
package com.ptit.a2.movie_theater_managent.facade.impl;

import com.ptit.a2.movie_theater_managent.dto.NotificationDto;
import com.ptit.a2.movie_theater_managent.entity.QuizSession;
import com.ptit.a2.movie_theater_managent.entity.QuizSessionParticipant;
import com.ptit.a2.movie_theater_managent.exception.quiz_session.QuizSessionEndedException;
import com.ptit.a2.movie_theater_managent.exception.quiz_session.QuizSessionNotFoundException;
import com.ptit.a2.movie_theater_managent.exception.quiz_session.QuizSessionTimeExpiredException;
import com.ptit.a2.movie_theater_managent.facade.QuizSessionFacadeService;
import com.ptit.a2.movie_theater_managent.service.QuizSessionParticipantService;
import com.ptit.a2.movie_theater_managent.service.QuizSessionService;
import com.ptit.a2.movie_theater_managent.service.websocket.WebSocketService;
import com.ptit.a2.movie_theater_managent.utils.AuthenticationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizSessionFacadeServiceImpl implements QuizSessionFacadeService {
  private final QuizSessionService quizSessionService;
  private final QuizSessionParticipantService quizSessionParticipantService;
  private final RedisTemplate<String, String> redisTemplate;
  private final WebSocketService webSocketService;

  @Override
  public void joinQuiz(Integer quizSessionId) {
    log.info("(joinQuiz) quizSessionId: {}", quizSessionId);

    Integer userId = AuthenticationUtils.getCurrentUserId();
    QuizSession quizSession = findQuizSession(quizSessionId);

    switch (quizSession.getStatus()) {
      case WAITING:
        addUserToWaitingList(quizSessionId, userId);
        break;

      case ENDED:
        throw new QuizSessionEndedException();

      case STARTED:
      case PAUSED:
        joinActiveQuizSession(quizSessionId, userId);
        break;

      default:
        throw new IllegalStateException("Unexpected quiz session status: " + quizSession.getStatus());
    }
  }

  @Override
  public void startQuiz(Integer quizSessionId) {
    log.info("(startQuiz) quizSessionId: {}", quizSessionId);

    QuizSession quizSession = findQuizSession(quizSessionId);
    validateStatus(quizSession, Set.of(QuizSession.Status.WAITING, QuizSession.Status.PAUSED),
          "Quiz session can only be started from WAITING or PAUSED status");

    long startTime = Instant.now().toEpochMilli();
    if (quizSession.getStatus() == QuizSession.Status.WAITING) {
      quizSession.setStartTime(startTime);
      handleParticipantsFromRedis(quizSessionId, startTime);
    } else {
      validateTimeNotExpired(quizSession);
      notifyParticipants(quizSessionId, NotificationDto.NotificationType.PAUSE_QUIZ,
            "Quiz session " + quizSessionId + " has resumed", new HashMap<>());
    }

    updateQuizSessionStatus(quizSession, QuizSession.Status.STARTED);
  }

  @Override
  public void pauseQuiz(Integer quizSessionId) {
    log.info("(pauseQuiz) quizSessionId: {}", quizSessionId);

    QuizSession quizSession = findQuizSession(quizSessionId);
    validateStatus(quizSession, Set.of(QuizSession.Status.STARTED),
          "Quiz session can only be paused from STARTED status");

    updateQuizSessionStatus(quizSession, QuizSession.Status.PAUSED);
    notifyParticipants(quizSessionId, NotificationDto.NotificationType.PAUSE_QUIZ,
          "Quiz session " + quizSessionId + " has been paused", new HashMap<>());
  }

  @Override
  public void endQuiz(Integer quizSessionId, String reason) {
    log.info("(endQuiz) quizSessionId: {}, reason: {}", quizSessionId, reason);

    QuizSession quizSession = findQuizSession(quizSessionId);
    validateStatus(quizSession, Set.of(QuizSession.Status.WAITING, QuizSession.Status.STARTED, QuizSession.Status.PAUSED),
          "Quiz session can only be ended from WAITING, STARTED, or PAUSED status");

    updateDurationIfEndedEarly(quizSession);
    updateQuizSessionStatus(quizSession, QuizSession.Status.ENDED);

    Map<String, Object> data = new HashMap<>();
    data.put("reason", reason != null ? reason : "Quiz ended");
    notifyParticipants(quizSessionId, NotificationDto.NotificationType.END_QUIZ,
          "Quiz session " + quizSessionId + " has ended", data);
  }

  // Tìm QuizSession và ném lỗi nếu không tồn tại
  private QuizSession findQuizSession(Integer quizSessionId) {
    QuizSession quizSession = quizSessionService.findById(quizSessionId);
    if (quizSession == null) {
      throw new QuizSessionNotFoundException();
    }
    return quizSession;
  }

  // Kiểm tra trạng thái hợp lệ
  private void validateStatus(QuizSession quizSession, Set<QuizSession.Status> allowedStatuses, String errorMessage) {
    if (!allowedStatuses.contains(quizSession.getStatus())) {
      throw new IllegalStateException(errorMessage);
    }
  }

  // Thêm người dùng vào danh sách chờ trong Redis
  private void addUserToWaitingList(Integer quizSessionId, Integer userId) {
    String redisKey = "QUIZ_USER_WAITING" + quizSessionId;
    redisTemplate.opsForSet().add(redisKey, userId.toString());
    log.info("User {} added to waiting list in Redis for quiz session {}", userId, quizSessionId);
  }

  // Tham gia quiz session đang hoạt động
  private void joinActiveQuizSession(Integer quizSessionId, Integer userId) {
    if (quizSessionParticipantService.existsBySessionIdAndUserId(quizSessionId, userId)) {
      log.info("User {} already joined quiz session {}", userId, quizSessionId);
      return;
    }

    QuizSessionParticipant participant = new QuizSessionParticipant();
    participant.setSessionId(quizSessionId);
    participant.setUserId(userId);
    participant.setJoinTime(Instant.now().toEpochMilli());
    participant.setScore(0);
    quizSessionParticipantService.save(participant);
    log.info("User {} joined quiz session {} in database", userId, quizSessionId);
  }

  // Xử lý người tham gia từ Redis khi bắt đầu quiz
  private void handleParticipantsFromRedis(Integer quizSessionId, long startTime) {
    String redisKey = "QUIZ_USER_WAITING" + quizSessionId;
    Set<String> userIdStrings = redisTemplate.opsForSet().members(redisKey);
    if (userIdStrings == null || userIdStrings.isEmpty()) {
      return;
    }

    List<Integer> userIds = userIdStrings.stream()
          .map(Integer::parseInt)
          .collect(Collectors.toList());

    for (Integer userId : userIds) {
      QuizSessionParticipant participant = new QuizSessionParticipant();
      participant.setSessionId(quizSessionId);
      participant.setUserId(userId);
      participant.setJoinTime(startTime);
      participant.setScore(0);
      quizSessionParticipantService.save(participant);
    }

    QuizSession quizSession = findQuizSession(quizSessionId);
    Map<String, Object> data = new HashMap<>();
    data.put("start_time", startTime);
    data.put("duration", quizSession.getDuration());
    sendNotification(userIds, quizSessionId, NotificationDto.NotificationType.START_QUIZ,
          "Quiz session " + quizSessionId + " has started", data);

    redisTemplate.delete(redisKey);
    log.info("Saved and notified {} participants from Redis for quiz session {}", userIds.size(), quizSessionId);
  }

  // Kiểm tra thời gian hết hạn khi tiếp tục từ PAUSED
  private void validateTimeNotExpired(QuizSession quizSession) {
    long currentTime = Instant.now().toEpochMilli();
    Long duration = quizSession.getDuration();
    Long startTime = quizSession.getStartTime();
    if (duration == null) {
      throw new IllegalStateException("Duration cannot be null for PAUSED quiz session");
    }
    if (startTime == null) {
      throw new IllegalStateException("Start time cannot be null for PAUSED quiz session");
    }
    long endTime = startTime + duration; // duration là mili giây
    if (endTime < currentTime) {
      throw new QuizSessionTimeExpiredException();
    }
  }

  // Cập nhật trạng thái QuizSession
  private void updateQuizSessionStatus(QuizSession quizSession, QuizSession.Status status) {
    quizSession.setStatus(status);
    quizSessionService.save(quizSession);
  }

  // Lấy danh sách userIds từ QuizSessionParticipant
  private List<Integer> getParticipantUserIds(Integer quizSessionId) {
    List<QuizSessionParticipant> participants = quizSessionParticipantService.findBySessionId(quizSessionId);
    return participants.stream()
          .map(QuizSessionParticipant::getUserId)
          .collect(Collectors.toList());
  }

  // Gửi thông báo WebSocket cho người tham gia
  private void notifyParticipants(Integer quizSessionId, NotificationDto.NotificationType type, String message, Map<String, Object> data) {
    List<Integer> userIds = getParticipantUserIds(quizSessionId);
    if (!userIds.isEmpty()) {
      sendNotification(userIds, quizSessionId, type, message, data);
      log.info("Notified {} participants for quiz session {} with type {}", userIds.size(), quizSessionId, type);
    }
  }

  // Gửi thông báo WebSocket tới danh sách userIds
  private void sendNotification(List<Integer> userIds, Integer quizSessionId, NotificationDto.NotificationType type,
                                String message, Map<String, Object> data) {
    NotificationDto notification = new NotificationDto(
          type,
          quizSessionId,
          Instant.now().toEpochMilli(),
          message,
          data
    );
    webSocketService.sendMessageToUsers(userIds, notification);
  }

  // Cập nhật duration nếu kết thúc sớm
  private void updateDurationIfEndedEarly(QuizSession quizSession) {
    if (quizSession.getStatus() != QuizSession.Status.STARTED && quizSession.getStatus() != QuizSession.Status.PAUSED) {
      return;
    }

    Long startTime = quizSession.getStartTime();
    Long duration = quizSession.getDuration();
    if (startTime == null || duration == null) {
      log.warn("Quiz session {} has null startTime or duration, skipping duration update", quizSession.getId());
      return;
    }

    long currentTime = Instant.now().toEpochMilli();
    long endTime = startTime + duration; // duration là mili giây
    if (currentTime < endTime) {
      long newDuration = currentTime - startTime; // Lưu dưới dạng mili giây
      quizSession.setDuration(newDuration);
      log.info("Quiz session {} ended early, updated duration to {} milliseconds", quizSession.getId(), newDuration);
    }
  }
}

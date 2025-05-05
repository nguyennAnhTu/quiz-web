package com.ptit.a2.movie_theater_managent.facade.impl;

import com.ptit.a2.movie_theater_managent.dto.NotificationDto;
import com.ptit.a2.movie_theater_managent.dto.request.quiz_session.QuizSessionAnswerRequest;
import com.ptit.a2.movie_theater_managent.dto.response.MediaResponse;
import com.ptit.a2.movie_theater_managent.dto.response.UserResponse;
import com.ptit.a2.movie_theater_managent.dto.response.quiz_session.QuizSessionAnswerResponse;
import com.ptit.a2.movie_theater_managent.dto.response.quiz_session.QuizSessionUserResponse;
import com.ptit.a2.movie_theater_managent.entity.QuizSession;
import com.ptit.a2.movie_theater_managent.entity.QuizSessionParticipant;
import com.ptit.a2.movie_theater_managent.exception.quiz_session.QuizSessionEndedException;
import com.ptit.a2.movie_theater_managent.exception.quiz_session.QuizSessionNotFoundException;
import com.ptit.a2.movie_theater_managent.exception.quiz_session.QuizSessionTimeExpiredException;
import com.ptit.a2.movie_theater_managent.facade.QuizSessionFacadeService;
import com.ptit.a2.movie_theater_managent.service.*;
import com.ptit.a2.movie_theater_managent.service.websocket.WebSocketService;
import com.ptit.a2.movie_theater_managent.utils.AuthenticationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
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
  private final UserService userService;
  private final QuizSessionAnswerService quizSessionAnswerService;
  private final MediaService mediaService;

  @Override
  public void joinQuiz(String sessionCode) {
    log.info("(joinQuiz) sessionCode: {}", sessionCode);

    Integer userId = AuthenticationUtils.getCurrentUserId();
    QuizSession quizSession = findQuizSessionByCode(sessionCode);

    switch (quizSession.getStatus()) {
      case WAITING:
        addUserToWaitingList(quizSession.getId(), userId);
        notifyWaitingUsers(quizSession.getId());
        break;

      case STARTED:
      case PAUSED:
        throw new IllegalStateException("Quiz session is already in progress");

      case ENDED:
        throw new QuizSessionEndedException();

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

    if (quizSession.getStatus() == QuizSession.Status.WAITING) {
      // Lấy danh sách user từ Redis và lưu vào database
      String redisKey = "QUIZ_USER_WAITING" + quizSessionId;
      Set<String> userIdStrings = redisTemplate.opsForSet().members(redisKey);
      if (userIdStrings != null && !userIdStrings.isEmpty()) {
        List<Integer> userIds = userIdStrings.stream()
              .map(Integer::parseInt)
              .collect(Collectors.toList());

        // Lưu vào database với score = 0
        for (Integer userId : userIds) {
          QuizSessionParticipant participant = new QuizSessionParticipant();
          participant.setSessionId(quizSessionId);
          participant.setUserId(userId);
          participant.setJoinTime(Instant.now().toEpochMilli());
          participant.setScore(0);
          quizSessionParticipantService.save(participant);
        }

        // Khởi tạo bảng xếp hạng trong Redis với score = 0
        String leaderboardKey = "QUIZ_LEADERBOARD:" + quizSessionId;
        for (Integer userId : userIds) {
          redisTemplate.opsForZSet().add(leaderboardKey, userId.toString(), 0);
        }

        // Gửi thông báo trạng thái
        Map<String, Object> statusData = new HashMap<>();
        statusData.put("status", "STARTED");
        NotificationDto statusNotification = new NotificationDto(
              NotificationDto.NotificationType.CHANGE_STATUS_ROOM,
              statusData
        );
        webSocketService.sendMessageToUsers(userIds, statusNotification);

        // Gửi thông báo bảng xếp hạng
        Map<String, Object> leaderboardData = new HashMap<>();
        leaderboardData.put("leaderboard", getLeaderboard(quizSessionId));
        NotificationDto leaderboardNotification = new NotificationDto(
              NotificationDto.NotificationType.LEADERBOARD,
              leaderboardData
        );
        webSocketService.sendMessageToUsers(userIds, leaderboardNotification);

        // Xóa dữ liệu từ Redis
        redisTemplate.delete(redisKey);
        log.info("Saved {} participants from Redis to database for quiz session {}", userIds.size(), quizSessionId);
      }
    } else if (quizSession.getStatus() == QuizSession.Status.PAUSED) {
      // Lấy danh sách user từ database
      List<QuizSessionParticipant> participants = quizSessionParticipantService.findBySessionId(quizSessionId);
      List<Integer> userIds = participants.stream()
            .map(QuizSessionParticipant::getUserId)
            .collect(Collectors.toList());

      // Lưu bảng xếp hạng vào Redis
      String leaderboardKey = "QUIZ_LEADERBOARD:" + quizSessionId;
      for (QuizSessionParticipant participant : participants) {
        redisTemplate.opsForZSet().add(
              leaderboardKey,
              participant.getUserId().toString(),
              participant.getScore()
        );
      }

      // Gửi thông báo trạng thái
      Map<String, Object> statusData = new HashMap<>();
      statusData.put("status", "STARTED");
      NotificationDto statusNotification = new NotificationDto(
            NotificationDto.NotificationType.CHANGE_STATUS_ROOM,
            statusData
      );
      webSocketService.sendMessageToUsers(userIds, statusNotification);

      // Gửi thông báo bảng xếp hạng
      Map<String, Object> leaderboardData = new HashMap<>();
      leaderboardData.put("leaderboard", getLeaderboard(quizSessionId));
      NotificationDto leaderboardNotification = new NotificationDto(
            NotificationDto.NotificationType.LEADERBOARD,
            leaderboardData
      );
      webSocketService.sendMessageToUsers(userIds, leaderboardNotification);

      log.info("Notified {} participants about quiz session {} resuming", userIds.size(), quizSessionId);
    }

    updateQuizSessionStatus(quizSession, QuizSession.Status.STARTED);
  }

  @Override
  public void pauseQuiz(Integer quizSessionId) {
    log.info("(pauseQuiz) quizSessionId: {}", quizSessionId);

    QuizSession quizSession = findQuizSession(quizSessionId);
    validateStatus(quizSession, Set.of(QuizSession.Status.STARTED),
          "Quiz session can only be paused from STARTED status");

    // Lấy danh sách user từ database
    List<QuizSessionParticipant> participants = quizSessionParticipantService.findBySessionId(quizSessionId);
    List<Integer> userIds = participants.stream()
          .map(QuizSessionParticipant::getUserId)
          .collect(Collectors.toList());

    // Gửi thông báo đến người dùng
    Map<String, Object> data = new HashMap<>();
    data.put("status", "PAUSED");
    NotificationDto notification = new NotificationDto(
          NotificationDto.NotificationType.CHANGE_STATUS_ROOM,
          data
    );
    webSocketService.sendMessageToUsers(userIds, notification);
    log.info("Notified {} participants about quiz session {} being paused", userIds.size(), quizSessionId);

    updateQuizSessionStatus(quizSession, QuizSession.Status.PAUSED);
  }

  @Override
  public void endQuiz(Integer quizSessionId) {
    log.info("(endQuiz) quizSessionId: {}", quizSessionId);

    QuizSession quizSession = findQuizSession(quizSessionId);
    validateStatus(quizSession, Set.of(QuizSession.Status.WAITING, QuizSession.Status.STARTED, QuizSession.Status.PAUSED),
          "Quiz session can only be ended from WAITING, STARTED, or PAUSED status");

    // Lấy danh sách user từ database
    List<QuizSessionParticipant> participants = quizSessionParticipantService.findBySessionId(quizSessionId);
    List<Integer> userIds = participants.stream()
          .map(QuizSessionParticipant::getUserId)
          .collect(Collectors.toList());

    // Gửi thông báo đến người dùng
    Map<String, Object> data = new HashMap<>();
    data.put("status", "ENDED");
    NotificationDto notification = new NotificationDto(
          NotificationDto.NotificationType.CHANGE_STATUS_ROOM,
          data
    );
    webSocketService.sendMessageToUsers(userIds, notification);
    log.info("Notified {} participants about quiz session {} ending", userIds.size(), quizSessionId);

    updateQuizSessionStatus(quizSession, QuizSession.Status.ENDED);
  }

  @Override
  public void nextQuestion(Integer quizSessionId, Integer currentQuestionId) {
    log.info("(nextQuestion) quizSessionId: {}, currentQuestionId: {}", quizSessionId, currentQuestionId);

    QuizSession quizSession = findQuizSession(quizSessionId);
    validateStatus(quizSession, Set.of(QuizSession.Status.STARTED),
          "Quiz session must be in STARTED status to move to next question");

    // Cập nhật current question id
    quizSession.setCurrentQuestionId(currentQuestionId);
    quizSessionService.save(quizSession);

    // Lấy danh sách user từ database
    List<QuizSessionParticipant> participants = quizSessionParticipantService.findBySessionId(quizSessionId);
    List<Integer> userIds = participants.stream()
          .map(QuizSessionParticipant::getUserId)
          .collect(Collectors.toList());

    // Gửi thông báo đến người dùng
    Map<String, Object> data = new HashMap<>();
    data.put("current_question_id", currentQuestionId);
    NotificationDto notification = new NotificationDto(
          NotificationDto.NotificationType.NEXT_QUESTION,
          data
    );
    webSocketService.sendMessageToUsers(userIds, notification);
    log.info("Notified {} participants about next question {} in quiz session {}", userIds.size(), currentQuestionId, quizSessionId);
  }

  @Override
  public void outQuiz(Integer quizSessionId) {
    log.info("(outQuiz) quizSessionId: {}", quizSessionId);

    Integer userId = AuthenticationUtils.getCurrentUserId();
    QuizSession quizSession = findQuizSession(quizSessionId);

    if (quizSession.getStatus() != QuizSession.Status.WAITING) {
      throw new IllegalStateException("Can only out from quiz session in WAITING status");
    }

    // Xóa user khỏi Redis
    String redisKey = "QUIZ_USER_WAITING" + quizSessionId;
    redisTemplate.opsForSet().remove(redisKey, userId.toString());
    log.info("User {} removed from waiting list in Redis for quiz session {}", userId, quizSessionId);

    // Thông báo danh sách người dùng đang chờ
    notifyWaitingUsers(quizSessionId);
  }

  @Override
  public QuizSessionAnswerResponse submitAnswer(Integer quizSessionId, QuizSessionAnswerRequest request) {
    log.info("(submitAnswer) quizSessionId: {}, request: {}", quizSessionId, request);

    QuizSession quizSession = findQuizSession(quizSessionId);
    validateStatus(quizSession, Set.of(QuizSession.Status.STARTED),
          "Can only submit answer when quiz session is in STARTED status");

    // Tạo câu trả lời
    QuizSessionAnswerResponse answerResponse = quizSessionAnswerService.create(request);

    // Cập nhật điểm số trong Redis
    String leaderboardKey = "QUIZ_LEADERBOARD:" + quizSessionId;
    Integer userId = request.userId();
    
    // Lấy điểm hiện tại
    Double currentScore = redisTemplate.opsForZSet().score(leaderboardKey, userId.toString());
    if (currentScore == null) {
      currentScore = 0.0;
    }

    // Cập nhật điểm mới
    Double newScore = currentScore + request.score();
    redisTemplate.opsForZSet().add(leaderboardKey, userId.toString(), newScore);

    // Gửi thông báo bảng xếp hạng
    Map<String, Object> leaderboardData = new HashMap<>();
    leaderboardData.put("leaderboard", getLeaderboard(quizSessionId));
    NotificationDto leaderboardNotification = new NotificationDto(
          NotificationDto.NotificationType.LEADERBOARD,
          leaderboardData
    );

    // Lấy danh sách người tham gia để gửi thông báo
    List<QuizSessionParticipant> participants = quizSessionParticipantService.findBySessionId(quizSessionId);
    List<Integer> userIds = participants.stream()
          .map(QuizSessionParticipant::getUserId)
          .collect(Collectors.toList());

    webSocketService.sendMessageToUsers(userIds, leaderboardNotification);
    log.info("Updated leaderboard and notified {} participants for quiz session {}", userIds.size(), quizSessionId);

    return answerResponse;
  }

  // Tìm QuizSession và ném lỗi nếu không tồn tại
  private QuizSession findQuizSession(Integer quizSessionId) {
    QuizSession quizSession = quizSessionService.findById(quizSessionId);
    if (quizSession == null) {
      throw new QuizSessionNotFoundException();
    }
    return quizSession;
  }

  // Tìm QuizSession bằng sessionCode và ném lỗi nếu không tồn tại
  private QuizSession findQuizSessionByCode(String sessionCode) {
    QuizSession quizSession = quizSessionService.findBySessionCode(sessionCode);
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

  // Thông báo danh sách người dùng đang chờ
  private void notifyWaitingUsers(Integer quizSessionId) {
    String redisKey = "QUIZ_USER_WAITING" + quizSessionId;
    Set<String> userIdStrings = redisTemplate.opsForSet().members(redisKey);
    if (userIdStrings == null || userIdStrings.isEmpty()) {
      return;
    }

    List<Integer> userIds = userIdStrings.stream()
          .map(Integer::parseInt)
          .collect(Collectors.toList());

    // Lấy thông tin quiz session để biết host
    QuizSession quizSession = findQuizSession(quizSessionId);
    Integer hostId = quizSession.getCreatedBy();

    // Lấy thông tin user và thêm trường isHost
    List<UserResponse> userResponses = userService.getUsersByIds(userIds);
    List<QuizSessionUserResponse> quizSessionUserResponses = userResponses.stream()
          .map(user -> QuizSessionUserResponse.of(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                mediaService.find(user.getImageId()),
                user.getIsAdmin(),
                user.getId().equals(hostId)
          ))
          .collect(Collectors.toList());

    Map<String, Object> data = new HashMap<>();
    data.put("users", quizSessionUserResponses);

    NotificationDto notification = new NotificationDto(
          NotificationDto.NotificationType.RELOAD_USER,
          data
    );

    webSocketService.sendMessageToUsers(userIds, notification);
    log.info("Notified {} waiting users for quiz session {}", userIds.size(), quizSessionId);
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

  // Lấy bảng xếp hạng
  private List<LeaderboardEntry> getLeaderboard(Integer quizSessionId) {
    String leaderboardKey = "QUIZ_LEADERBOARD:" + quizSessionId;
    Set<ZSetOperations.TypedTuple<String>> topScores = redisTemplate.opsForZSet()
          .reverseRangeWithScores(leaderboardKey, 0, 9); // Lấy top 10

    if (topScores == null || topScores.isEmpty()) {
      return new ArrayList<>();
    }

    return topScores.stream()
          .map(tuple -> {
            Integer userId = Integer.parseInt(tuple.getValue());
            UserResponse user = userService.detail(userId);
            return LeaderboardEntry.of(
                  user.getId(),
                  user.getUsername(),
                  user.getMedia(),
                  tuple.getScore().intValue()
            );
          })
          .collect(Collectors.toList());
  }

  // DTO cho bảng xếp hạng
  public record LeaderboardEntry(
        Integer userId,
        String username,
        MediaResponse mediaResponse,
        Integer score
  ) {
    public static LeaderboardEntry of(Integer userId, String username, MediaResponse mediaResponse, Integer score) {
      return new LeaderboardEntry(userId, username,mediaResponse, score);
    }
  }
}

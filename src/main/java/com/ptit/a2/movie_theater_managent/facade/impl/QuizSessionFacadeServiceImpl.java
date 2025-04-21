package com.ptit.a2.movie_theater_managent.facade.impl;

import com.ptit.a2.movie_theater_managent.entity.QuizSession;
import com.ptit.a2.movie_theater_managent.entity.QuizSessionParticipant;
import com.ptit.a2.movie_theater_managent.exception.quiz_session.QuizSessionEndedException;
import com.ptit.a2.movie_theater_managent.exception.quiz_session.QuizSessionNotFoundException;
import com.ptit.a2.movie_theater_managent.facade.QuizSessionFacadeService;
import com.ptit.a2.movie_theater_managent.service.QuizSessionParticipantService;
import com.ptit.a2.movie_theater_managent.service.QuizSessionService;
import com.ptit.a2.movie_theater_managent.utils.AuthenticationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizSessionFacadeServiceImpl implements QuizSessionFacadeService {
  private final QuizSessionService quizSessionService;
  private final QuizSessionParticipantService quizSessionParticipantService;
  private final RedisTemplate<String, String> redisTemplate;

  @Override
  public void joinQuiz(Integer quizSessionId) {
    log.info("(joinQuiz) quizSessionId: {}", quizSessionId);

    // Tìm QuizSession
    Integer userId = AuthenticationUtils.getCurrentUserId();
    QuizSession quizSession = quizSessionService.findById(quizSessionId);
    if (quizSession == null) {
      throw new QuizSessionNotFoundException();
    }

    // Kiểm tra trạng thái QuizSession
    switch (quizSession.getStatus()) {
      case WAITING:
        // Lưu userId vào Redis
        String redisKey = "QUIZ_USER_WAITING" + quizSessionId;
        redisTemplate.opsForSet().add(redisKey, userId.toString());
        log.info("User {} added to waiting list in Redis for quiz session {}", userId, quizSessionId);
        break;

      case ENDED:
        throw new QuizSessionEndedException();

      case STARTED:
      case PAUSED:
        // Kiểm tra xem userId đã tồn tại trong sessionId chưa
        if (quizSessionParticipantService.existsBySessionIdAndUserId(quizSessionId, userId)) {
          log.info("User {} already joined quiz session {}", userId, quizSessionId);
          break;
        }

        // Lưu vào QuizSessionParticipant nếu chưa tồn tại
        QuizSessionParticipant participant = new QuizSessionParticipant();
        participant.setSessionId(quizSessionId);
        participant.setUserId(userId);
        participant.setJoinTime(Instant.now().toEpochMilli()); // Lưu thời gian tham gia dưới dạng Long
        participant.setScore(0); // Khởi tạo điểm số bằng 0
        quizSessionParticipantService.save(participant);
        log.info("User {} joined quiz session {} in database", userId, quizSessionId);
        break;


      default:
        throw new IllegalStateException("Unexpected quiz session status: " + quizSession.getStatus());
    }
  }
}
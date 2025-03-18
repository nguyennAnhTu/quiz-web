package com.ptit.a2.movie_theater_managent.service.impl;

import com.ptit.a2.movie_theater_managent.dto.request.FilmRequest;
import com.ptit.a2.movie_theater_managent.dto.response.FilmResponse;
import com.ptit.a2.movie_theater_managent.entity.Film;
import com.ptit.a2.movie_theater_managent.repository.FilmRepository;
import com.ptit.a2.movie_theater_managent.service.FilmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

import static com.ptit.a2.movie_theater_managent.cloudinary.CloudinaryHelper.uploadAndGetFileUrl;

@Slf4j
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
  private final FilmRepository repository;

  @Override
  @Transactional
  public FilmResponse create(FilmRequest request, MultipartFile multipartFile) {
    log.info("(create) request: {}", request);

    final Film film = this.toEntity(request);
    if (Objects.nonNull(multipartFile) && !multipartFile.isEmpty()) {
      film.setThumbnailUrl(uploadAndGetFileUrl(multipartFile));
    }
    return this.toDTO(repository.save(film));
  }

  private Film toEntity(FilmRequest request) {
    return Film.of(
          request.getName(),
          request.getDescription(),
          request.getDuration(),
          request.getAgeLimit(),
          request.getReleaseDate(),
          null,
          request.getTrailerUrl()
    );
  }

  private FilmResponse toDTO(Film film) {
    return FilmResponse.of(
          film.getId(),
          film.getName(),
          film.getDescription(),
          film.getDuration(),
          film.getAgeLimit(),
          film.getReleaseDate(),
          film.getThumbnailUrl(),
          film.getTrailerUrl()
    );
  }
}

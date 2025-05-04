package com.ptit.a2.movie_theater_managent.service.impl;

import com.ptit.a2.movie_theater_managent.dto.request.MediaRequest;
import com.ptit.a2.movie_theater_managent.dto.response.MediaResponse;
import com.ptit.a2.movie_theater_managent.entity.Media;
import com.ptit.a2.movie_theater_managent.repository.MediaRepository;
import com.ptit.a2.movie_theater_managent.service.MediaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {
  private final MediaRepository repository;

  @Override
  @Transactional
  public MediaResponse create(MediaRequest request) {
    log.info("(create) media request: {}", request);

    return this.toDTO(repository.save(this.toEntity(request)));
  }

  @Override
  @Transactional
  public MediaResponse update(Integer id, MediaRequest request) {
    log.info("(update) media request: {}", request);

    Media media = repository.findById(id).orElseThrow(null);
    media.setMediaLink(request.getMediaLink());
    media.setZoom(request.getZoom());
    media.setOffsetX(request.getOffsetX());
    media.setOffsetY(request.getOffsetY());
    return this.toDTO(repository.save(media));
  }

  @Override
  public MediaResponse find(Integer id) {
    log.info("(find) media request: {}", id);

    Optional<Media> media = repository.findById(id);
    return media.map(this::toDTO).orElse(null);

  }

  @Override
  public List<MediaResponse> findAllByIds(List<Integer> ids) {
    if (ids == null || ids.isEmpty()) {
      return Collections.emptyList();
    }

    List<Media> medias = repository.findAllById(ids);
    return medias.stream()
          .map(this::toDTO)
          .collect(Collectors.toList());
  }

  @Override
  public void delete(Integer id) {
    log.info("(delete) media request: {}", id);

    repository.deleteById(id);
  }

  @Override
  public void update(Integer id, String url) {
    log.info("(update) media request: {}", id);

    Media media = repository.findById(id).orElseThrow(null);
    if (!Objects.equals(media.getMediaLink(), url)) {
      media.setMediaLink(url);
    }
    repository.save(media);
  }

  private Media toEntity(MediaRequest request) {
    return Media.of(
          request.getMediaLink(),
          request.getZoom(),
          request.getOffsetX(),
          request.getOffsetY()
    );
  }

  private MediaResponse toDTO(Media media) {
    return MediaResponse.of(
          media.getId(),
          media.getMediaLink(),
          media.getZoom(),
          media.getOffsetX(),
          media.getOffsetY()
    );
  }
}

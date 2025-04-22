package com.ptit.a2.movie_theater_managent.service;

import com.ptit.a2.movie_theater_managent.dto.request.MediaRequest;
import com.ptit.a2.movie_theater_managent.dto.response.MediaResponse;

import java.util.List;

public interface MediaService {
  MediaResponse create(MediaRequest request);

  MediaResponse update(Integer id, MediaRequest request);

  MediaResponse find(Integer id);

  List<MediaResponse> findAllByIds(List<Integer> ids);

  void delete(Integer id);
}

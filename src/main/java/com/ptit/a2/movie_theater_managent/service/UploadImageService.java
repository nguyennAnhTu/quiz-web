package com.ptit.a2.movie_theater_managent.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadImageService {
  String upload(MultipartFile file);
}

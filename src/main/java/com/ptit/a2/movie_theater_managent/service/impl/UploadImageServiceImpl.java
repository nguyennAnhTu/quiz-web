package com.ptit.a2.movie_theater_managent.service.impl;

import com.ptit.a2.movie_theater_managent.service.UploadImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static com.ptit.a2.movie_theater_managent.cloudinary.CloudinaryHelper.uploadAndGetFileUrl;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadImageServiceImpl implements UploadImageService {
  public String upload(MultipartFile file) {
    log.info("upload image");

    return uploadAndGetFileUrl(file);
  }
}

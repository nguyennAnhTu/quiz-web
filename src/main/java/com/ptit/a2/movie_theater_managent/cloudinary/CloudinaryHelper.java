package com.ptit.a2.movie_theater_managent.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.CloudinaryConstant.*;

@Slf4j
public class CloudinaryHelper {
  private static Cloudinary cloudinary;

  static {
    cloudinary = new Cloudinary(
          ObjectUtils.asMap(
                CLOUDINARY_NAME, CLOUDINARY_NAME_VALUE,
                CLOUDINARY_API_KEY, CLOUDINARY_API_KEY_VALUE,
                CLOUDINARY_API_SECRET, CLOUDINARY_API_SECRET_VALUE
          )
    );

    log.info("Cloudinary initialized");
  }

  public static String uploadAndGetFileUrl(MultipartFile multipartFile) {
    try {
      Map uploadResult = cloudinary.uploader().uploadLarge(multipartFile.getInputStream(), ObjectUtils.emptyMap());
      return  uploadResult.get("url").toString();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}

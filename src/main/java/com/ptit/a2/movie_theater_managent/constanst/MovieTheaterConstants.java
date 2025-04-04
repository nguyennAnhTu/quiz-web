package com.ptit.a2.movie_theater_managent.constanst;

public class MovieTheaterConstants {
  public static class CommonConstants {
    public static final String ENCODING_UTF_8 = "UTF-8";
    public static final String LANGUAGE = "Accept-Language";
    public static final String DEFAULT_LANGUAGE = "en";
    public static final String BLANK = "";
    public static final String PARAM_KEYWORD = "keyword";
    public static final String PARAM_SIZE = "size";
    public static final String PARAM_PAGE = "page";
    public static final String PARAM_ALL = "all";
    public static final String PERCENT = "%";
    public static final String MESSAGE_SOURCE = "classpath:i18n/messages";
    public static final String BASE_PACKAGE_REPO = "com.ptit.a2.movie_theater_managent";
    public static final String NOT_FOUND_MESSAGE = "Not found";
    public static final String BAD_REQUEST_MESSAGE = "Bad request";
    public static final String CONFLICT_MESSAGE = "Conflict occurred";
    public static final String BLANK_MESSAGE = "";
    public static final String SUCCESS = "success";
    public static final String INVALID_TIME = "Invalid time format";
    public static final String INVALID_PHONE_NUMBER = "Invalid phone number";
    public static final String REGEX_DATE = "^(?:19|20)\\d\\d-(?:0[1-9]|1[0-2])-(?:0[1-9]|[12]\\d|3[01])$";
    public static final String REGEX_PHONE_NUMBER = "(84|0[35789])(\\d{8})\\b";
    public static final String TOKEN_TYPE = "Bearer";
    public static final String BEARER_TOKEN_TYPE_START = "Bearer ";
    public static final String CMS_PREFIX = "CMS";
    public static final String UNKNOWN = "Unknown";
    public static final Integer UNKNOWN_BANK_ID = 15;
    public static final Integer UNKNOWN_PROVIDER_ID = 7;
    public static final String EMAIL_ALREADY_EXISTED= "email already existed";
    public static final String USER_NOT_FOUND = "user not found";
    public static final String INCORRECT_PASSWORD = "incorrect password";
    public static final String EXPIRED_TOKEN = "expired token";
    public static final String GENRE_NOT_FOUND = "genre not found";
    public static final String QUIZ_NOT_FOUND = "quiz not found";
  }


  public static class AuditorConstant {
    private AuditorConstant() {
    }

    public static final String ANONYMOUS = "anonymousUser";
    public static final String SYSTEM = "SYSTEM";
    public static final String UN_ASSIGNED = "Chưa có";
  }

  public static class StatusException {
    private StatusException() {
    }

    public static final Integer NOT_FOUND = 404;
    public static final Integer CONFLICT = 409;
    public static final Integer BAD_REQUEST = 400;
  }

  public static class MessageException {
    private MessageException() {
    }

    public static final String DEFAULT_CODE_BAD_REQUEST = "com.cyai.cms.exception.base.BadRequestException";
    public static final String DEFAULT_CODE_CONFLICT = "com.cyai.cms.exception.base.ConflictException";
    public static final String DEFAULT_CODE_NOTFOUND = "com.cyai.cms.exception.base.NotFoundException";
    public static final String DEFAULT_CODE_SERVER_ERROR = "com.cyai.cms.exception.base.InternalServerError";

  }
  public static class AuthConstant {
    private AuthConstant() {
    }

    public static String TYPE_TOKEN = "Bear ";
    public static String AUTHORIZATION = "Authorization";
    public static String[] MATCHER_USER_API = {"/api/v1/auth/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"
          , "/actuator/"};
    public static String[] MATCHER_ADMIN_API = {"/api/v1/genres/**, /api/v1/films/**"};
    public static String CLAIMS = "claims";
    public static final String CLAIM_ID_KEY = "id";
    public static final String CLAIM_EMAIL_KEY = "email";
    public static final String CLAIM_AUTHORITIES_KEY = "authorities";
  }


  public static class VariableConstant {
    private VariableConstant() {
    }

    public static final String SIZE_DEFAULT = "10";
    public static final String PAGE_DEFAULT = "0";
    public static final String IS_ALL_DEFAULT = "0";

  }
  public static final class Message {
    private Message() {
    }

    public static final String SUCCESS = "com.cyai.cms.success";
    public static final String INVALID_USERNAME = "Invalid Username";
    public static final String INVALID_FULL_NAME = "Invalid full name";
    public static final String INVALID_EMAIL = "Invalid Email";
    public static final String INVALID_PASSWORD = "Invalid Password";
    public static final String INVALID_PHONE_NUMBER = "Invalid PhoneNumber";
    public static final String INACTIVE_ACCOUNT = "Account is Inactived";
  }

  public static class RedisConstant {
    private RedisConstant() {}

    public static final String REFRESH_TOKEN_KEY = "refresh_token";
    public static final String ACCESS_TOKEN_KEY = "access_token";
  }

  public static class CloudinaryConstant {
    private CloudinaryConstant() {}

    public static final String CLOUDINARY_NAME = "cloud_name";
    public static final String CLOUDINARY_NAME_VALUE = "diorkbloc";
    public static final String CLOUDINARY_API_KEY = "api_key";
    public static final String CLOUDINARY_API_KEY_VALUE = "281948166373512";
    public static final String CLOUDINARY_API_SECRET = "api_secret";
    public static final String CLOUDINARY_API_SECRET_VALUE = "0ujSmYM_wBMIhKr0v6EMFjHwUG8";
  }
}

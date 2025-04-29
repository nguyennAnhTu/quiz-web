package com.ptit.a2.movie_theater_managent.utils;


import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

@Slf4j
public class DateUtils {

  private static final DateTimeFormatter YYYY_MM_DD_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  private static final DateTimeFormatter MM_DD_YYYY_HH_MM_SS_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

  private DateUtils() {
  }

  public static String getCurrentDateString() {
    return LocalDate.now().toString();
  }

  public static Long currentTimeMillis() {
    return System.currentTimeMillis();
  }

  public static Long convertToMillisSecond(String dateTime) {
    if (dateTime == null || dateTime.isEmpty()) return 0L;
    try {
      LocalDate localDate = LocalDate.parse(dateTime, YYYY_MM_DD_FORMAT);
      return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    } catch (Exception e) {
      throw new IllegalArgumentException(e);
    }
  }
  public static long convertDateToLong(Date date) {
    if (date == null) {
      return 0;
    }
    return date.getTime();
  }

  public static Long getStartOfDayInTimestamp(String dateFrom) {
    if (dateFrom == null || dateFrom.isEmpty()) return 0L;
    try {
      LocalDate date = LocalDate.parse(dateFrom, YYYY_MM_DD_FORMAT);
      LocalDateTime endOfDay = date.atTime(0, 0, 0);
      return endOfDay.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    } catch (DateTimeParseException e) {
      return null;
    }
  }

  public static Long getEndOfDayInTimestamp(String dateTo) {
    if (dateTo == null || dateTo.isEmpty()) return 0L;
    try {
      LocalDate date = LocalDate.parse(dateTo, YYYY_MM_DD_FORMAT);
      LocalDateTime endOfDay = date.atTime(23, 59, 59);
      return endOfDay.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    } catch (DateTimeParseException e) {
      return null;
    }
  }


  public static String convertToDateString(Long timestamp) {
    log.debug("(convertToTimestamp) timestamp: {}", timestamp);
    Date date = new Date(timestamp);
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    return dateFormat.format(date);
  }

  public static String convertTimestampToDateTime(long timestampMillis) {
    LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestampMillis), ZoneId.systemDefault());
    return MM_DD_YYYY_HH_MM_SS_FORMAT.format(dateTime);

  }
}

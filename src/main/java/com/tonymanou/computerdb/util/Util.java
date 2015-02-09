package com.tonymanou.computerdb.util;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.DateValidator;

/**
 * Utility class.
 * 
 * @author tonymanou
 */
public class Util {

  private static final String REGEX_POSITIVE_LONG = "\\d{1,19}";
  private static final Matcher POSITIVE_LONG_MATCHER;

  static {
    POSITIVE_LONG_MATCHER = Pattern.compile(REGEX_POSITIVE_LONG).matcher("");
  }

  /**
   * Parse the given date string to a {@link LocalDate}.
   * 
   * @param str
   *          The string to parse.
   * @return A LocalDate instance, or null if it was not a valid date string.
   */
  public static LocalDate parseLocalDate(String str) {
    LocalDate localDate;
    if (str != null && DateValidator.getInstance().validate(str, "yyyy-MM-dd") != null) {
      localDate = LocalDate.parse(str);
    } else {
      localDate = null;
    }
    return localDate;
  }

  /**
   * Parse the given string to a {@link Long}.
   * 
   * @param str
   *          The string to parse.
   * @return A Long instance, or null if it was not a valid number.
   */
  public static Long parsePositiveLong(String str) {
    Long result;
    if (str != null && POSITIVE_LONG_MATCHER.reset(str).matches()) {
      result = Long.parseLong(str);
    } else {
      result = null;
    }
    return result;
  }

  /**
   * Check if the given string is empty.
   * 
   * @param str
   *          The string to test.
   * @return true if the string is empty, false otherwise.
   */
  public static boolean isStringEmpty(String str) {
    return str == null || str.trim().isEmpty();
  }
}

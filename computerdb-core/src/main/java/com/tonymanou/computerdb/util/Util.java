package com.tonymanou.computerdb.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.GenericValidator;

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
   * @param pattern
   *          The date pattern to use.
   * @return A LocalDate instance, or null if it was not a valid date string.
   */
  public static LocalDate parseLocalDate(String str, String pattern) {
    LocalDate localDate;
    if (str != null && GenericValidator.isDate(str, pattern, true)) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
      localDate = formatter.parse(str, LocalDate::from);
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
   * @return true if the string is empty or contains only spaces, false otherwise.
   */
  public static boolean isStringEmpty(String str) {
    boolean error = true;
    if (str != null) {
      int strLen = str.length();
      for (int i = 0; i < strLen; i++) {
        if (!Character.isWhitespace(str.charAt(i))) {
          error = false;
          break;
        }
      }
    }
    return error;
  }
}

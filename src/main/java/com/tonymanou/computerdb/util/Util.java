package com.tonymanou.computerdb.util;

import java.time.LocalDate;

/**
 * Utility class.
 * 
 * @author tonymanou
 */
public class Util {

  // @formatter:off
  private static final String REGEX_DELIMITER = "(\\.|-|\\/)";
  private static final String REGEX_DATE_EN = "("
      + "((\\d{4})" + REGEX_DELIMITER + "(0[13578]|10|12)" + REGEX_DELIMITER + "(0[1-9]|[12][0-9]|3[01]))"
      + "|((\\d{4})" + REGEX_DELIMITER + "(0[469]|11)" + REGEX_DELIMITER + "([0][1-9]|[12][0-9]|30))"
      + "|((\\d{4})" + REGEX_DELIMITER + "(02)" + REGEX_DELIMITER  + "(0[1-9]|1[0-9]|2[0-8]))"
      + "|(([02468][048]00)" + REGEX_DELIMITER + "(02)" + REGEX_DELIMITER + "(29))"
      + "|(([13579][26]00)" + REGEX_DELIMITER + "(02)" + REGEX_DELIMITER + "(29))"
      + "|(([0-9][0-9][0][48])" + REGEX_DELIMITER + "(02)" + REGEX_DELIMITER + "(29))"
      + "|(([0-9][0-9][2468][048])" + REGEX_DELIMITER + "(02)" + REGEX_DELIMITER + "(29))"
      + "|(([0-9][0-9][13579][26])" + REGEX_DELIMITER + "(02)" + REGEX_DELIMITER + "(29))"
      + ")";
  private static final String REGEX_DATE_FR = "("
      + "((0[1-9]|[12][0-9]|3[01])" + REGEX_DELIMITER + "(0[13578]|10|12)" + REGEX_DELIMITER + "(\\d{4}))"
      + "|(([0][1-9]|[12][0-9]|30)" + REGEX_DELIMITER + "(0[469]|11)" + REGEX_DELIMITER + "(\\d{4}))"
      + "|((0[1-9]|1[0-9]|2[0-8])" + REGEX_DELIMITER + "(02)" + REGEX_DELIMITER + "(\\d{4}))"
      + "|((29)" + REGEX_DELIMITER + "(02)" + REGEX_DELIMITER + "([02468][048]00))"
      + "|((29)" + REGEX_DELIMITER + "(02)" + REGEX_DELIMITER + "([13579][26]00))"
      + "|((29)" + REGEX_DELIMITER + "(02)" + REGEX_DELIMITER + "([0-9][0-9][0][48]))"
      + "|((29)" + REGEX_DELIMITER + "(02)" + REGEX_DELIMITER + "([0-9][0-9][2468][048]))"
      + "|((29)" + REGEX_DELIMITER + "(02)" + REGEX_DELIMITER + "([0-9][0-9][13579][26]))"
      + ")";
  private static final String REGEX_NUMBER = "[+-]?[0-9]+";
  // @formatter:on

  /**
   * Parse the given date string to a {@link LocalDate}.
   * 
   * @param str
   *          The string to parse.
   * @return A LocalDate instance, or null if it was not a valid date string.
   */
  public static LocalDate parseLocalDate(String str) {
    LocalDate localDate;
    if (str != null && str.matches(REGEX_DATE_EN)) {
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
  public static Long parseLong(String str) {
    Long result;
    if (str != null && str.matches(REGEX_NUMBER)) {
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
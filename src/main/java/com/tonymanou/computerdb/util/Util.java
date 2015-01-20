package com.tonymanou.computerdb.util;

import java.time.LocalDate;

public class Util {

  public static LocalDate parseLocalDate(String str) {
    LocalDate localDate;
    if (str == null || str.trim().isEmpty()) {
      localDate = null;
    } else {
      localDate = LocalDate.parse(str);
    }
    return localDate;
  }
}

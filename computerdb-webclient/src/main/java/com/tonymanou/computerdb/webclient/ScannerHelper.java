package com.tonymanou.computerdb.webclient;

import java.time.LocalDate;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.tonymanou.computerdb.util.Util;

@Component
public class ScannerHelper {

  private static final String CANCELED = "Input canceled...";
  private static final String MSG_DATE_FORMAT = "date.format";

  @Autowired
  @Qualifier("formatMessageSource")
  private MessageSource messageSource;

  private Scanner scanner;

  /**
   * Enumeration describing the default action to do when the user enters an empty value.
   */
  public enum EmptyType {
    /**
     * Leave the field empty.
     */
    EMPTY,
    /**
     * Cancel the current input.
     */
    CANCEL,
    /**
     * Keep the original value.
     */
    KEEP
  }

  public ScannerHelper() {
    scanner = new Scanner(System.in);
  }

  /**
   * Ask the user to enter a string, or nothing to cancel.
   *
   * @param message
   *          The message to display at the beginning of the input.
   * @param emptyType
   *          What to do when the user leaves an empty value.
   * @param original
   *          Optional original value, only used when using emptyType KEEP.
   * @return a string, or null if the user canceled the input.
   */
  public String getStringInput(String message, EmptyType emptyType, String... original) {
    String string = null;

    System.out.print(message + " (Enter nothing to ");
    switch (emptyType) {
    case EMPTY:
      System.out.print("leave empty");
      break;
    case CANCEL:
      System.out.print("cancel");
      break;
    case KEEP:
      System.out.print("keep current value, enter 0 to set no value");
      break;
    }
    System.out.print(")\n> ");

    String input = scanner.nextLine();
    String[] words = splitToWords(input);

    if ("".equals(words[0])) {
      switch (emptyType) {
      case EMPTY:
        System.out.println("Value set to null.");
        break;
      case CANCEL:
        System.out.println(CANCELED);
        break;
      case KEEP:
        System.out.println("Original value kept.");
        if (original != null && original.length >= 1) {
          string = original[0];
        }
        break;
      }
      System.out.println();
    } else if (emptyType == EmptyType.KEEP && "0".equals(words[0])) {
      System.out.println("Value set to null.");
    } else {
      string = input;
      System.out.println();
    }

    return string;
  }

  /**
   * Ask the user if he wants to do something.
   *
   * @param message
   *          The message to display to the user.
   * @return true if the user answered by yes, false otherwise.
   */
  public boolean isYesAnswer(String message) {
    System.out.print(message + " [y|n]\n> ");

    String input = scanner.nextLine();
    System.out.println();

    if (input == null) {
      input = "";
    } else {
      input.trim().toLowerCase();
    }

    return input.startsWith("y");
  }

  /**
   * Ask the user to enter a date, or nothing to leave empty.
   *
   * @param message
   *          The message to display at the beginning of the input.
   * @param emptyType
   *          What to do when the user leaves an empty value.
   * @param original
   *          Optional original value, only used when using emptyType KEEP.
   * @return the date, or null if the the user left the input empty.
   */
  public String getDateInput(String message, EmptyType emptyType, String... original) {
    boolean running = true;
    String date = null;
    String string;

    String datePattern = messageSource.getMessage(MSG_DATE_FORMAT, null,
        LocaleContextHolder.getLocale());

    System.out.print(message + " (" + datePattern + ", enter nothing to ");
    switch (emptyType) {
    case EMPTY:
      System.out.print("leave empty");
      break;
    case CANCEL:
      System.out.print("cancel");
      break;
    case KEEP:
      System.out.print("keep current value, enter 0 to set no value");
      break;
    }
    System.out.println(")");

    // Loop until a valid date is entered or the input is canceled
    do {
      System.out.print("> ");

      string = scanner.nextLine();
      String[] words = splitToWords(string);

      if ("".equals(words[0])) {
        switch (emptyType) {
        case EMPTY:
          System.out.println("Value set to null.");
          break;
        case CANCEL:
          System.out.println(CANCELED);
          break;
        case KEEP:
          System.out.println("Original value kept.");
          if (original != null && original.length >= 1) {
            date = original[0];
          }
          break;
        }
        System.out.println();
        running = false;
      } else if (emptyType == EmptyType.KEEP && "0".equals(words[0])) {
        System.out.println("Value set to null.");
        System.out.println();
        running = false;
      } else {
        LocalDate dateIn = Util.parseLocalDate(string, datePattern);
        if (dateIn == null) {
          date = null;
          System.out.println("Please enter a valid date."); 
        } else {
          date = string;
          System.out.println();
        }
      }
    } while (date == null && running);

    return date;
  }

  /**
   * Ask the user to input a (long) number.
   *
   * @param message
   *          The message to display at the beginning of the input.
   * @param emptyType
   *          What to do when the user leaves an empty value.
   * @param original
   *          Optional original value, only used when using emptyType KEEP.
   * @return the input number, or null if the input was canceled.
   */
  public Long getLongInput(String message, EmptyType emptyType, Long... original) {
    boolean running = true;
    Long number = null;
    String string;

    System.out.print(message + " (Positive number, enter nothing to ");
    switch (emptyType) {
    case EMPTY:
      System.out.print("leave empty");
      break;
    case CANCEL:
      System.out.print("cancel");
      break;
    case KEEP:
      System.out.print("keep current value, enter 0 to set no value");
      break;
    }
    System.out.println(")");

    // Loop until a valid number is entered or the input is canceled
    do {
      System.out.print("> ");

      string = scanner.nextLine();
      String[] words = splitToWords(string);

      if ("".equals(words[0])) {
        switch (emptyType) {
        case EMPTY:
          System.out.println("Value set to null.");
          break;
        case CANCEL:
          System.out.println(CANCELED);
          break;
        case KEEP:
          System.out.println("Original value kept.");
          if (original != null && original.length >= 1) {
            number = original[0];
          }
          break;
        }
        System.out.println();
        running = false;
      } else if (emptyType == EmptyType.KEEP && "0".equals(words[0])) {
        System.out.println("Value set to null.");
        System.out.println();
        running = false;
      } else {
        boolean bad = true;

        // Try to parse the number
        if (words[0].matches("[+]?[0-9]+")) {
          number = Long.parseLong(words[0]);
          bad = false;
          System.out.println();
        }
        if (bad) {
          System.out.println("Please enter a positive number.");
        }
      }
    } while (number == null && running);

    return number;
  }

  /**
   * Split a string into words.
   *
   * @param string
   *          The string to split.
   * @return a non-empty array with all the substrings.
   */
  public static String[] splitToWords(String string) {
    String[] words;

    if (string != null) {
      words = string.trim().toLowerCase().split(" ");
    } else {
      words = new String[1];
      words[0] = "";
    }

    return words;
  }

  public Scanner getScanner() {
    return scanner;
  }

  public void close() {
    scanner.close();
    scanner = null;
  }

  public String nextLine() {
    if (scanner == null) {
      return null;
    } else {
      return scanner.nextLine();
    }
  }
}

package com.tonymanou.computerdb;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.tonymanou.computerdb.dao.CompanyDAO;
import com.tonymanou.computerdb.dao.ComputerDAO;
import com.tonymanou.computerdb.entity.Company;
import com.tonymanou.computerdb.entity.Computer;

/**
 * Command-line interface to manipulate the database.
 *
 * @author tonymanou
 */
public class CLIRoutine {

  private static final String UNRECOGNIZED = "Unrecognized action.";
  private static final String CANCELED = "Input canceled...";
  private Scanner scanner;

  private static enum EmptyType {
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

  public CLIRoutine() {
    scanner = new Scanner(System.in);
  }

  /**
   * Routine for the main menu in command line interface.
   */
  public void doMainMenu() {
    String action = null;
    boolean running = true;
    boolean skip = false;

    System.out.println("=== Computer database ===\n");

    // Main loop
    while (running) {
      System.out.println("[Home] Type 'help' if you don't know what to do.");
      System.out.print("> ");

      // Get user's input
      action = scanner.nextLine();

      System.out.println();

      // Split action string into words
      String[] words = splitToWords(action);

      switch (words[0]) {
      case "computer":
        if (words.length > 1 && !"list".equals(words[1])) {
          switch (words[1]) {
          case "add":
            doAddComputer();
            break;
          case "remove":
            doRemoveComputer();
            break;
          case "update":
            doUpdateComputer();
            break;
          default:
            System.out.println(UNRECOGNIZED);
          }
        } else {
          doListComputers();
        }
        break;
      case "company":
        if (words.length > 1 && !"list".equals(words[1])) {
          switch (words[1]) {
          default:
            System.out.println(UNRECOGNIZED);
          }
        } else {
          doListCompanies();
        }
        break;
      case "help":
        System.out.println("Usage:");
        System.out.println("\tcomputer [list|add|remove|update]");
        System.out.println("\tcompany [list]");
        System.out.println("\thelp");
        System.out.println("\texit");
        break;
      case "exit":
        System.out.println("Exiting...");
        running = false;
        break;
      case "":
        skip = true;
        break;
      default:
        System.out.println(UNRECOGNIZED);
      }

      if (skip == false) {
        System.out.println();
      } else {
        skip = false;
      }
    }

    scanner.close();
    scanner = null;

    System.out.println("========== End ==========");
  }

  /* ========== Computer actions ========== */

  /**
   * List all the computers from the database.
   */
  private void doListComputers() {
    try {
      ComputerDAO computerDAO = new ComputerDAO();
      List<Computer> listComputer = computerDAO.findAll();

      if (listComputer.size() == 0) {
        System.out.println("There is no computer is the database.");
      } else {
        for (Computer c : listComputer) {
          System.out.println(c.toCleverString());
        }
      }
    } catch (SQLException e) {
      die(e);
    }
  }

  /**
   * Let the CLI user add a new computer.
   */
  private void doAddComputer() {
    String name = getStringInput("[Add computer] Enter the name of the computer.");
    if (name == null) {
      return;
    }

    Date introduced = getDateInput("[Add computer] Enter its introduced date.");
    Date discontinued = getDateInput("[Add computer] Enter its discontinued date.");

    if (isYesAnswer("[Add computer] Do you want to list all the companies first?")) {
      doListCompanies();
      System.out.println();
    }

    Long companyId = getLongInput("[Add computer] Enter its company ID.", EmptyType.EMPTY);

    try {
      Company company = null;

      if (companyId != null) {
        CompanyDAO companyDAO = new CompanyDAO();
        company = companyDAO.getFromId(companyId);
      }

      ComputerDAO computerDAO = new ComputerDAO();
      Computer computer = new Computer();
      computer.setName(name);
      computer.setIntroduced(introduced);
      computer.setDiscontinued(discontinued);
      computer.setCompany(company);
      computerDAO.create(computer);
    } catch (SQLException e) {
      die(e);
    }
  }

  /**
   * Let the CLI user remove a computer.
   */
  private void doRemoveComputer() {
    if (isYesAnswer("[Remove computer] Do you want to list all the computers first?")) {
      doListComputers();
      System.out.println();
    }

    Long id = getLongInput("[Remove computer] Enter the id of the computer you want to delete.",
        EmptyType.CANCEL);

    if (id != null) {
      try {
        ComputerDAO computerDAO = new ComputerDAO();
        computerDAO.delete(id);
        System.out.println("Deleting computer [id=" + id + "].");
      } catch (SQLException e) {
        die(e);
      }
    }
  }

  /**
   * Let the CLI user update a computer.
   */
  private void doUpdateComputer() {
    if (isYesAnswer("[Update computer] Do you want to list all the computers first?")) {
      doListComputers();
      System.out.println();
    }

    Long id = getLongInput("[Update computer] Enter the id of the computer you want to update.",
        EmptyType.CANCEL);

    if (id != null) {
      try {
        ComputerDAO computerDAO = new ComputerDAO();
        Computer computer = computerDAO.getFromId(id);

        if (computer == null) {
          System.out.println("Computer [id=" + id + "] not found!");
        } else {
          System.out.println("[Update computer] Current name is '" + computer.getName() + "'.");
          String name = getStringInput("[Update computer] Enter the new name.");
          if (name != null) {
            computer.setName(name);
          }

          System.out.println("[Update computer] Current introduced date is "
              + computer.getIntroduced() + ".");
          Date introduced = getDateInput("[Update computer] Enter the new date.");
          if (introduced != null) {
            computer.setIntroduced(introduced);
          }

          System.out.println("[Update computer] Current introduced date is "
              + computer.getDiscontinued() + ".");
          Date discontinued = getDateInput("[Update computer] Enter the new date.");
          if (discontinued != null) {
            computer.setDiscontinued(discontinued);
          }

          System.out.println("[Update computer] Current company is " + computer.getCompany() + ".");
          if (isYesAnswer("[Update computer] Do you want to list all the companies first?")) {
            doListCompanies();
            System.out.println();
          }

          Long companyId = getLongInput("[Update computer] Enter the new company company ID.",
              EmptyType.KEEP);
          if (companyId != null) {
            CompanyDAO companyDAO = new CompanyDAO();
            Company company = companyDAO.getFromId(companyId);
            computer.setCompany(company);
          }

          System.out.println(computer);
          computerDAO.update(computer);

          System.out.println("Computer [id=" + id + "] updated!");
        }
      } catch (SQLException e) {
        die(e);
      }
    }
  }

  /* ========== Company actions ========== */

  /**
   * List all the companies in the database.
   */
  private void doListCompanies() {
    try {
      CompanyDAO companyDAO = new CompanyDAO();
      List<Company> listCompany = companyDAO.findAll();

      if (listCompany.size() == 0) {
        System.out.println("There is no company is the database.");
      } else {
        for (Company c : listCompany) {
          System.out.println(c);
        }
      }
    } catch (SQLException e) {
      die(e);
    }
  }

  /* ========== Scanner helpers ========== */

  /**
   * Ask the user to enter a string, or nothing to cancel.
   *
   * @param message
   *          The message to display at the beginning of the input.
   * @return a string, or null if the user canceled the input.
   */
  private String getStringInput(String message) {
    String string = null;

    System.out.print(message + " (Enter nothing to cancel)\n> ");

    String input = scanner.nextLine();

    if (!"".equals(splitToWords(input)[0])) {
      string = input;
      System.out.println();
    } else {
      System.out.println(CANCELED);
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
  private boolean isYesAnswer(String message) {
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
   * @return the date, or null if the the user left the input empty.
   */
  private Date getDateInput(String message) {
    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
    boolean running = true;
    Date date = null;
    String string;

    System.out.println(message + " (yyyy-MM-dd, enter nothing to leave empty)");

    // Loop until a valid date is entered or the input is canceled
    do {
      System.out.print("> ");

      string = scanner.nextLine();
      String[] words = splitToWords(string);

      if ("".equals(words[0])) {
        System.out.println();
        running = false;
      } else {
        boolean bad = true;

        try {
          LocalDate localDate = LocalDate.parse(string, formatter);
          date = java.sql.Date.valueOf(localDate);
          bad = false;
          System.out.println();
        } catch (DateTimeParseException e) {
          // Ignored
        }
        if (bad) {
          System.out.println("Please enter a valid date.");
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
  private Long getLongInput(String message, EmptyType emptyType, Long... original) {
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
      } else {
        boolean bad = true;

        // Try to parse the number
        if (words[0].matches("[+]?[0-9]+")) {
          try {
            number = Long.parseLong(words[0]);
            bad = false;
            System.out.println();
          } catch (NumberFormatException ex) {
            // Ignored
          }
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
  private String[] splitToWords(String string) {
    String[] words;

    if (string != null) {
      words = string.trim().toLowerCase().split(" ");
    } else {
      words = new String[1];
      words[0] = "";
    }

    return words;
  }

  /**
   * Kill the program and display the exception that caused the death.
   *
   * @param e
   *          Exception thrown.
   */
  private void die(Exception e) {
    e.printStackTrace(System.err);
    System.exit(-1);
  }
}

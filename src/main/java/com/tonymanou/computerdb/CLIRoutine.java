package com.tonymanou.computerdb;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import com.tonymanou.computerdb.entity.Company;
import com.tonymanou.computerdb.entity.Computer;
import com.tonymanou.computerdb.service.ICompanyService;
import com.tonymanou.computerdb.service.IComputerService;
import com.tonymanou.computerdb.service.ServiceManager;

/**
 * Command-line interface to manipulate the database.
 *
 * @author tonymanou
 */
public class CLIRoutine {

  private static final String UNRECOGNIZED = "Unrecognized action.";
  private static final String CANCELED = "Input canceled...";
  private Scanner scanner;
  private IComputerService computerService;
  private ICompanyService companyService;

  /**
   * Enumeration describing the default action to do when the user enters an empty value.
   */
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
    computerService = ServiceManager.INSTANCE.getComputerService();
    companyService = ServiceManager.INSTANCE.getCompanyService();
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
    List<Computer> listComputer = computerService.findAll();

    if (listComputer.size() == 0) {
      System.out.println("There is no computer is the database.");
    } else {
      for (Computer c : listComputer) {
        System.out.println(c);
      }
    }
  }

  /**
   * Let the CLI user add a new computer.
   */
  private void doAddComputer() {
    String name = getStringInput("[Add computer] Enter the name of the computer.", EmptyType.CANCEL);
    if (name == null) {
      return;
    }
    Computer.Builder builder = Computer.getBuilder(name);

    builder
        .setIntroduced(getDateInput("[Add computer] Enter its introduced date.", EmptyType.EMPTY));
    builder.setDiscontinued(getDateInput("[Add computer] Enter its discontinued date.",
        EmptyType.EMPTY));

    if (isYesAnswer("[Add computer] Do you want to list all the companies first?")) {
      doListCompanies();
      System.out.println();
    }

    Long companyId = getLongInput("[Add computer] Enter its company ID.", EmptyType.EMPTY);
    if (companyId != null) {
      builder.setCompany(companyService.getFromId(companyId));
    }

    computerService.create(builder.build());
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
      computerService.delete(id);
      System.out.println("Deleting computer [id=" + id + "].");
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
      Computer computer = computerService.getFromId(id);

      if (computer == null) {
        System.out.println("Computer [id=" + id + "] not found!");
      } else {
        String oldName = computer.getName();
        System.out.println("[Update computer] Current name is '" + oldName + "'.");
        String name = getStringInput("[Update computer] Enter the new name.", EmptyType.KEEP,
            oldName);
        computer.setName(name);

        LocalDateTime oldIntroduced = computer.getIntroduced();
        System.out.println("[Update computer] Current introduced date is " + oldIntroduced + ".");
        LocalDateTime introduced = getDateInput("[Update computer] Enter the new date.",
            EmptyType.KEEP, oldIntroduced);
        computer.setIntroduced(introduced);

        LocalDateTime oldDiscontinued = computer.getDiscontinued();
        System.out.println("[Update computer] Current introduced date is " + oldDiscontinued + ".");
        LocalDateTime discontinued = getDateInput("[Update computer] Enter the new date.",
            EmptyType.KEEP, oldDiscontinued);
        computer.setDiscontinued(discontinued);

        System.out.println("[Update computer] Current company is " + computer.getCompany() + ".");
        if (isYesAnswer("[Update computer] Do you want to list all the companies first?")) {
          doListCompanies();
          System.out.println();
        }

        Company oldCompany = computer.getCompany();
        Long oldCompanyId = oldCompany == null ? null : oldCompany.getId();
        Long companyId = getLongInput("[Update computer] Enter the new company company ID.",
            EmptyType.KEEP, oldCompanyId);
        Company company = null;
        if (companyId != null) {
          company = companyService.getFromId(companyId);
          // TODO check if this company id exists
        }
        computer.setCompany(company);

        System.out.println(computer);
        computerService.update(computer);

        System.out.println("Computer [id=" + id + "] updated!");
      }
    }
  }

  /* ========== Company actions ========== */

  /**
   * List all the companies in the database.
   */
  private void doListCompanies() {
    List<Company> listCompany = companyService.findAll();

    if (listCompany.size() == 0) {
      System.out.println("There is no company is the database.");
    } else {
      for (Company c : listCompany) {
        System.out.println(c);
      }
    }
  }

  /* ========== Scanner helpers ========== */

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
  private String getStringInput(String message, EmptyType emptyType, String... original) {
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
   * @param emptyType
   *          What to do when the user leaves an empty value.
   * @param original
   *          Optional original value, only used when using emptyType KEEP.
   * @return the date, or null if the the user left the input empty.
   */
  private LocalDateTime getDateInput(String message, EmptyType emptyType, LocalDateTime... original) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    boolean running = true;
    LocalDateTime date = null;
    String string;

    System.out.print(message + " (yyyy-MM-dd, enter nothing to ");
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
        boolean bad = true;

        try {
          date = LocalDate.parse(string, formatter).atStartOfDay();
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
      } else if (emptyType == EmptyType.KEEP && "0".equals(words[0])) {
        System.out.println("Value set to null.");
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
}

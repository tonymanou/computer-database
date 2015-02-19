package com.tonymanou.computerdb.webclient;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tonymanou.computerdb.dto.CompanyDTO;
import com.tonymanou.computerdb.dto.ComputerDTO;
import com.tonymanou.computerdb.webclient.ScannerHelper.EmptyType;
import com.tonymanou.computerdb.webservice.ICompanyWS;
import com.tonymanou.computerdb.webservice.IComputerWS;

/**
 * Command-line interface to manipulate the database.
 *
 * @author tonymanou
 */
@Component
public class CLIRoutine {

  private static final String UNRECOGNIZED = "Unrecognized action.";

  private IComputerWS computerService;
  private ICompanyWS companyService;
  @Autowired
  private ScannerHelper sc;

  public void init(IComputerWS computerWS, ICompanyWS companyWS) {
    computerService = computerWS;
    companyService = companyWS;
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
      action = sc.nextLine();

      System.out.println();

      // Split action string into words
      String[] words = ScannerHelper.splitToWords(action);

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
          case "remove":
            doRemoveCompany();
            break;
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
        System.out.println("\tcompany [list|remove]");
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

    sc.close();

    System.out.println("========== End ==========");
  }

  /* ========== Computer actions ========== */

  /**
   * List all the computers from the database.
   */
  private void doListComputers() {
    List<ComputerDTO> listComputer = computerService.findAll().getList();

    if (listComputer.size() == 0) {
      System.out.println("There is no computer is the database.");
    } else {
      for (ComputerDTO c : listComputer) {
        System.out.println(c);
      }
    }
  }

  /**
   * Let the CLI user add a new computer.
   */
  private void doAddComputer() {
    String here = "[Add computer] ";
    String name = sc.getStringInput(here + "Enter the name of the computer.", EmptyType.CANCEL);
    if (name == null) {
      return;
    }
    ComputerDTO.Builder builder = ComputerDTO.getBuilder(name);

    builder.setIntroduced(sc.getDateInput(here + "Enter its introduced date.", EmptyType.EMPTY));
    builder.setDiscontinued(sc.getDateInput(here + "Enter its discontinued date.",
        EmptyType.EMPTY));

    if (sc.isYesAnswer(here + "Do you want to list all the companies first?")) {
      doListCompanies();
      System.out.println();
    }

    Long companyId = sc.getLongInput(here + "Enter its company ID.", EmptyType.EMPTY);
    if (companyId != null) {
      CompanyDTO company = companyService.getFromId(companyId);
      if (company == null || company.getName() == null) {
        System.out.println("Company [id=" + companyId + "] not found, using none.\n");
      } else {
        builder.setCompany(company.getName());
        builder.setCompanyId(company.getId());
      }
    }

    ComputerDTO computer = builder.build();
    System.out.println(computer);
    computerService.create(computer);

    System.out.println("Computer created!");
  }

  /**
   * Let the CLI user remove a computer.
   */
  private void doRemoveComputer() {
    String here = "[Remove computer] ";
    if (sc.isYesAnswer(here + "Do you want to list all the computers first?")) {
      doListComputers();
      System.out.println();
    }

    Long id = sc.getLongInput(here + "Enter the id of the computer you want to delete.",
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
    String here = "[Update computer] ";
    if (sc.isYesAnswer(here + "Do you want to list all the computers first?")) {
      doListComputers();
      System.out.println();
    }

    Long id = sc.getLongInput(here + "Enter the id of the computer you want to update.",
        EmptyType.CANCEL);

    if (id != null) {
      ComputerDTO computer = computerService.getFromId(id);

      if (computer == null || computer.getName() == null) {
        System.out.println("Computer [id=" + id + "] not found!");
      } else {
        String oldName = computer.getName();
        System.out.println(here + "Current name is '" + oldName + "'.");
        String name = sc.getStringInput(here + "Enter the new name.", EmptyType.KEEP, oldName);
        computer.setName(name);

        String oldIntroduced = computer.getIntroducedDate();
        System.out.println(here + "Current introduced date is " + oldIntroduced + ".");
        String introduced = sc.getDateInput(here + "Enter the new date.", EmptyType.KEEP,
            oldIntroduced);
        computer.setIntroducedDate(introduced);

        String oldDiscontinued = computer.getDiscontinuedDate();
        System.out.println(here + "Current discontinued date is " + oldDiscontinued + ".");
        String discontinued = sc.getDateInput(here + "Enter the new date.", EmptyType.KEEP,
            oldDiscontinued);
        computer.setDiscontinuedDate(discontinued);

        System.out.println(here + "Current company is " + computer.getCompanyName() + ".");
        if (sc.isYesAnswer(here + "Do you want to list all the companies first?")) {
          doListCompanies();
          System.out.println();
        }

        Long oldCompanyId = computer.getCompanyId();
        Long companyId = sc.getLongInput(here + "Enter the new company company ID.",
            EmptyType.KEEP, oldCompanyId);
        if (companyId != null) {
          CompanyDTO company = companyService.getFromId(companyId);
          if (company == null || company.getName() == null) {
            System.out.println("Company [id=" + companyId + "] not found, using none.\n");
          } else {
            computer.setCompanyId(company.getId());
            computer.setCompanyName(company.getName());
          }
        }

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
    List<CompanyDTO> listCompany = companyService.findAll().getList();

    if (listCompany.size() == 0) {
      System.out.println("There is no company is the database.");
    } else {
      for (CompanyDTO c : listCompany) {
        System.out.println(c);
      }
    }
  }

  private void doRemoveCompany() {
    String here = "[Remove company] ";
    if (sc.isYesAnswer(here + "Do you want to list all the company first?")) {
      doListCompanies();
      System.out.println();
    }

    Long id = sc.getLongInput(here + "Enter the id of the company you want to delete.",
        EmptyType.CANCEL);

    if (id != null) {
      companyService.delete(id);
      System.out.println("Deleting company [id=" + id + "] and its computers.");
    }
  }
}

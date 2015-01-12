package com.tonymanou.computerdb;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.tonymanou.computerdb.dao.CompanyDAO;
import com.tonymanou.computerdb.dao.ComputerDAO;
import com.tonymanou.computerdb.entity.Company;
import com.tonymanou.computerdb.entity.Computer;

/**
 * Program's entry point.
 *
 * @author tonymanou
 */
public class Main {

	private static final String UNRECOGNIZED = "Unrecognized action.";
	private Scanner scanner;

	public static void main(String[] args) {
		Main main = new Main();
		main.doMainMenu();
	}

	private Main() {
		scanner = new Scanner(System.in);
	}

	private void doMainMenu() {
		String action = null;
		boolean running = true;

		System.out.println("=== Computer database ===\n");

		// Main loop
		while (running) {
			System.out
					.println("[Home] Type 'help' if you don't know what to do.");
			System.out.print("> ");

			// Get user's input
			action = scanner.nextLine();

			System.out.println();

			// Split action string into words
			String[] words;
			if (action != null) {
				words = action.trim().toLowerCase().split(" ");
			} else {
				words = new String[1];
				words[0] = "";
			}

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
			default:
				System.out.println(UNRECOGNIZED);
			}

			System.out.println();
		}

		scanner.close();
		scanner = null;

		System.out.println("========== End ==========");
	}

	/* ========== Computer actions ========== */

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
			e.printStackTrace(System.err);
		}
	}

	private void doAddComputer() {
		try {
			ComputerDAO computerDAO = new ComputerDAO();
			Computer comp = new Computer();
			// TODO let the user enter the data
			computerDAO.create(comp);
		} catch (SQLException e) {
			e.printStackTrace(System.err);
		}
	}

	private void doRemoveComputer() {
		try {
			ComputerDAO computerDAO = new ComputerDAO();
			computerDAO.delete(0L);
			// TODO let the user choose the id
		} catch (SQLException e) {
			e.printStackTrace(System.err);
		}
	}

	private void doUpdateComputer() {
		try {
			ComputerDAO computerDAO = new ComputerDAO();
			Computer comp = null;
			// TODO let the user choose what to edit
			computerDAO.update(comp);
		} catch (SQLException e) {
			e.printStackTrace(System.err);
		}
	}

	/* ========== Company actions ========== */

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
			e.printStackTrace(System.err);
		}
	}
}

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

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String action = null;
		boolean running = true;

		System.out.println("=== Computer database ===\n");

		// Main loop
		while (running) {
			printHelp();
			System.out.print("> ");

			// Get user's input
			action = sc.next();

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
				if (words.length > 1 && !words[1].equals("list")) {
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
					}
				} else {
					doListComputers();
				}
				break;
			case "company":
				doListCompanies();
				break;
			case "help":
				printHelp();
				break;
			case "exit":
				System.out.println("Exiting...");
				running = false;
				break;
			default:
				System.out.println("Unrecognized action");
			}

			System.out.println();
		}

		sc.close();

		System.out.println("========== End ==========");
	}

	private static void printHelp() {
		System.out
				.println("[Home] Actions: {computer [list|add|remove|update]} | {company [list]} | exit");
	}

	private static void doListComputers() {
		try {
			ComputerDAO computerDAO = new ComputerDAO();
			List<Computer> listComputer = computerDAO.findAll();

			for (Object c : listComputer) {
				System.out.println(c);
			}
		} catch (SQLException e) {
			e.printStackTrace(System.err);
		}
	}

	private static void doAddComputer() {
		try {
			ComputerDAO computerDAO = new ComputerDAO();
			Computer comp = new Computer();
			// TODO let the user enter the data
			computerDAO.create(comp);
		} catch (SQLException e) {
			e.printStackTrace(System.err);
		}
	}

	private static void doRemoveComputer() {
		try {
			ComputerDAO computerDAO = new ComputerDAO();
			computerDAO.delete(0L);
			// TODO let the user choose the id
		} catch (SQLException e) {
			e.printStackTrace(System.err);
		}
	}

	private static void doUpdateComputer() {
		try {
			ComputerDAO computerDAO = new ComputerDAO();
			Computer comp = null;
			// TODO let the user choose what to edit
			computerDAO.update(comp);
		} catch (SQLException e) {
			e.printStackTrace(System.err);
		}
	}

	private static void doListCompanies() {
		try {
			List<Company> listCompany = new CompanyDAO().findAll();

			for (Object c : listCompany) {
				System.out.println(c);
			}
		} catch (SQLException e) {
			e.printStackTrace(System.err);
		}
	}
}

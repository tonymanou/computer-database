package com.tonymanou.computerdb.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLUtil {

	private static final String DB_URL = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	private static final String DB_USR = "admincdb";
	private static final String DB_PW = "qwerty1234";

	static {
		try {
			// Load the driver for mysql database
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace(System.err);
		}
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DB_URL, DB_USR, DB_PW);
	}
}

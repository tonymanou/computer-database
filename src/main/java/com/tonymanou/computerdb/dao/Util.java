package com.tonymanou.computerdb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Helper class to create database connections.
 *
 * @author tonymanou
 */
public class Util {

  /**
   * URL to the database server.
   */
  private static final String DB_URL = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
  /**
   * User name for the database.
   */
  private static final String DB_USR = "admincdb";
  /**
   * User password for the database.
   */
  private static final String DB_PW = "qwerty1234";

  static {
    try {
      // Load the driver for mysql database
      Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace(System.err);
    }
  }

  /**
   * Retrieve a connection to the database.
   *
   * @return The {@link Connection} instance.
   * @throws SQLException
   *           if a database access error occurs
   */
  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(DB_URL, DB_USR, DB_PW);
  }
}

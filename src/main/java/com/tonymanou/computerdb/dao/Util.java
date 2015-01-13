package com.tonymanou.computerdb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

import com.tonymanou.computerdb.dao.impl.SQLCompanyDAO;
import com.tonymanou.computerdb.dao.impl.SQLComputerDAO;

/**
 * Helper class to create database connections.
 *
 * @author tonymanou
 */
public enum Util {

  INSTANCE;

  // ========== Database ==========

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

  private IComputerDAO computerDAO;
  private ICompanyDAO companyDAO;

  private Util() {
    try {
      // Load the driver for mysql database
      Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }

    computerDAO = new SQLComputerDAO();
    companyDAO = new SQLCompanyDAO();
  }

  public IComputerDAO getComputerDAO() {
    return computerDAO;
  }

  public ICompanyDAO getCompanyDAO() {
    return companyDAO;
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

  // ========== Utility ==========

  /**
   * Close elements if they are not null.
   *
   * @param resultat
   * @param statement
   * @param connection
   */
  public static void close(ResultSet resultat, Statement statement, Connection connection) {
    if (resultat != null) {
      try {
        resultat.close();
      } catch (SQLException e) {
        // Ignored
      }
    }
    close(statement, connection);
  }

  /**
   * Close elements if they are not null.
   *
   * @param statement
   * @param connection
   */
  public static void close(Statement statement, Connection connection) {
    if (statement != null) {
      try {
        statement.close();
      } catch (SQLException e) {
        // Ignored
      }
    }
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        // Ignored
      }
    }
  }

  /**
   * Convert a {@link Date} to a {@link Timestamp}
   *
   * @param date
   *          The date.
   * @return a timestamp.
   */
  public static Timestamp getTimestamp(Date date) {
    return new Timestamp(date.getTime());
  }
}

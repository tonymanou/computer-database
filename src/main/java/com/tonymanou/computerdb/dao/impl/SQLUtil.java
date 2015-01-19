package com.tonymanou.computerdb.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Helper class to create database connections.
 *
 * @author tonymanou
 */
public class SQLUtil {

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

  static {
    try {
      // Load the driver for mysql database
      Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
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
   * Convert a {@link LocalDateTime} to a {@link Timestamp}
   *
   * @param date
   *          The locale date time.
   * @return a timestamp, or null if the date wass null.
   */
  public static Timestamp getTimestamp(LocalDate date) {
    return date == null ? null : Timestamp.valueOf(date.atStartOfDay());
  }

  /**
   * Convert a {@link Timestamp} to a {@link LocalDateTime}
   *
   * @param timestamp
   *          The timestamp.
   * @return a local date time, or null if the timestamp was null.
   */
  public static LocalDate getLocalDate(Timestamp timestamp) {
    return timestamp == null ? null : timestamp.toLocalDateTime().toLocalDate();
  }
}

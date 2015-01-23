package com.tonymanou.computerdb.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tonymanou.computerdb.exception.PersistenceException;

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

  private static final Logger LOGGER = LoggerFactory.getLogger(SQLUtil.class);

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
   * @throws PersistenceException
   *           if a database access error occurs
   */
  public static Connection getConnection() {
    try {
      Connection connection = DriverManager.getConnection(DB_URL, DB_USR, DB_PW);
      // Isolate connections
      connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
      return connection;
    } catch (SQLException e) {
      LOGGER.error("Unable to get connection", e);
      throw new PersistenceException(e);
    }
  }

  /**
   * Close a connection.
   * 
   * @param connection
   *          The connection to close.
   */
  public static void closeConnection(Connection connection) {
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        LOGGER.warn("Unable to close connection", e);
      }
    }
  }

  // ========== Utility ==========

  /**
   * Close elements if they are not null.
   *
   * @param resultat
   * @param statement
   */
  public static void close(ResultSet resultat, Statement statement) {
    if (resultat != null) {
      try {
        resultat.close();
      } catch (SQLException e) {
        LOGGER.warn("Unable to close resultat", e);
      }
    }
    close(statement);
  }

  /**
   * Close elements if they are not null.
   *
   * @param statement
   */
  public static void close(Statement statement) {
    if (statement != null) {
      try {
        statement.close();
      } catch (SQLException e) {
        LOGGER.warn("Unable to close statement", e);
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

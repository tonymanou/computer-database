package com.tonymanou.computerdb.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper class to create database connections.
 *
 * @author tonymanou
 */
public class SQLUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(SQLUtil.class);

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
   * @return a timestamp, or null if the date was null.
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

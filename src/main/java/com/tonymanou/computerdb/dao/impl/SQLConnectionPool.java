package com.tonymanou.computerdb.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import com.tonymanou.computerdb.dao.IConnectionPool;
import com.tonymanou.computerdb.exception.PersistenceException;

@Component
public class SQLConnectionPool implements IConnectionPool {

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

  private static final Logger LOGGER = LoggerFactory.getLogger(SQLConnectionPool.class);
  private BoneCP connectionPool;

  static {
    try {
      // Load the driver for mysql database
      Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public SQLConnectionPool() {
    BoneCPConfig config = new BoneCPConfig();
    config.setJdbcUrl(DB_URL);
    config.setUsername(DB_USR);
    config.setPassword(DB_PW);
    try {
      connectionPool = new BoneCP(config);
    } catch (SQLException e) {
      LOGGER.error("Unable to initialize connection pool", e);
      throw new PersistenceException(e);
    }
  }

  @Override
  public void shutdown() {
    connectionPool.shutdown();
  }

  @Override
  public Connection getConnection() {
    try {
      Connection c = connectionPool.getConnection();
      // Isolate connections
      c.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
      return c;
    } catch (SQLException e) {
      LOGGER.error("Unable to get connection", e);
      throw new PersistenceException(e);
    }
  }

  @Override
  public void closeConnection(Connection connection) {
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        LOGGER.warn("Unable to close connection", e);
      }
    }
  }
}

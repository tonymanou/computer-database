package com.tonymanou.computerdb.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import com.tonymanou.computerdb.dao.IConnectionPool;
import com.tonymanou.computerdb.exception.PersistenceException;

@Component
@PropertySource("classpath:/database.properties")
public class SQLConnectionPool implements IConnectionPool {

  private static final Logger LOGGER = LoggerFactory.getLogger(SQLConnectionPool.class);
  private BoneCP connectionPool;
  @Autowired
  private Environment env;

  static {
    try {
      // Load the driver for mysql database
      Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  @PostConstruct
  private void init() {
    BoneCPConfig config = new BoneCPConfig();
    config.setJdbcUrl(env.getProperty("db.url"));
    config.setUsername(env.getProperty("db.user"));
    config.setPassword(env.getProperty("db.pass"));
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

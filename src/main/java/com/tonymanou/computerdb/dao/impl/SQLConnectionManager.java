package com.tonymanou.computerdb.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tonymanou.computerdb.dao.IConnectionManager;
import com.tonymanou.computerdb.exception.PersistenceException;

@Component
public class SQLConnectionManager implements IConnectionManager {

  private static final Logger LOGGER = LoggerFactory.getLogger(SQLConnectionManager.class);

  private ThreadLocal<Connection> threadLocal;
  @Autowired
  private DataSource dataSource;

  public SQLConnectionManager() {
    threadLocal = new ThreadLocal<Connection>();
  }

  @Override
  public Connection getConnection() {
    Connection c = threadLocal.get();
    if (c == null) {
      try {
        c = dataSource.getConnection();
        // Isolate connections
        c.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
      } catch (SQLException e) {
        LOGGER.error("Unable to get connection", e);
        throw new PersistenceException(e);
      }
      threadLocal.set(c);
    }
    return c;
  }

  @Override
  public void closeConnection() {
    Connection c = threadLocal.get();
    if (c != null) {
      threadLocal.set(null);
      try {
        c.close();
      } catch (SQLException e) {
        LOGGER.warn("Unable to close connection", e);
      }
    }
  }
}

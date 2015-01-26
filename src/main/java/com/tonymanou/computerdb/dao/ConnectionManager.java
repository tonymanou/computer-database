package com.tonymanou.computerdb.dao;

import java.sql.Connection;

import com.tonymanou.computerdb.dao.impl.SQLUtil;

public enum ConnectionManager {

  INSTANCE;

  private ThreadLocal<Connection> threadLocal;

  private ConnectionManager() {
    threadLocal = new ThreadLocal<Connection>();
  }

  /**
   * Retrieve a new connection.
   *
   * @return The current active connection to the database or a new one.
   */
  public Connection getConnection() {
    Connection c = threadLocal.get();
    if (c == null) {
      c = SQLUtil.getConnection();
      threadLocal.set(c);
    }
    return c;
  }

  /**
   * Close the current connection to the database.
   */
  public void closeConnection() {
    Connection c = threadLocal.get();
    if (c != null) {
      SQLUtil.closeConnection(c);
      threadLocal.set(null);
    }
  }
}

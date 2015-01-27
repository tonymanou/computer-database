package com.tonymanou.computerdb.dao;

import java.sql.Connection;

import com.tonymanou.computerdb.dao.impl.SQLConnectionPool;

public enum ConnectionManager {

  INSTANCE;

  private ThreadLocal<Connection> threadLocal;
  private IConnectionPool connectionPool;

  private ConnectionManager() {
    threadLocal = new ThreadLocal<Connection>();
    connectionPool = new SQLConnectionPool();
  }

  /**
   * Retrieve a new connection.
   *
   * @return The current active connection to the database or a new one.
   */
  public Connection getConnection() {
    Connection c = threadLocal.get();
    if (c == null) {
      c = connectionPool.getConnection();
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
      threadLocal.set(null);
      connectionPool.closeConnection(c);
    }
  }
}

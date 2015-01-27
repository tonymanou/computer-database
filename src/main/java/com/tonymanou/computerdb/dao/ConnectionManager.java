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
   * Retrieve the current active database connection of this thread or create a new one if none was
   * active.
   *
   * @return A connection to the database.
   * @throws PersistenceException
   *           if a database access error occurs
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
   * 
   * @throws PersistenceException
   *           if a database access error occurs
   */
  public void closeConnection() {
    Connection c = threadLocal.get();
    if (c != null) {
      threadLocal.set(null);
      connectionPool.closeConnection(c);
    }
  }
}

package com.tonymanou.computerdb.dao.impl;

import java.sql.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tonymanou.computerdb.dao.IConnectionManager;
import com.tonymanou.computerdb.dao.IConnectionPool;

@Component
public class SQLConnectionManager implements IConnectionManager {

  private ThreadLocal<Connection> threadLocal;
  @Autowired
  private IConnectionPool connectionPool;

  public SQLConnectionManager() {
    threadLocal = new ThreadLocal<Connection>();
  }

  @Override
  public Connection getConnection() {
    Connection c = threadLocal.get();
    if (c == null) {
      c = connectionPool.getConnection();
      threadLocal.set(c);
    }
    return c;
  }

  @Override
  public void closeConnection() {
    Connection c = threadLocal.get();
    if (c != null) {
      threadLocal.set(null);
      connectionPool.closeConnection(c);
    }
  }
}

package com.tonymanou.computerdb.dao;

import java.sql.Connection;

import com.tonymanou.computerdb.exception.PersistenceException;

public interface IConnectionPool {

  /**
   * Shutdown the connection pool.
   * 
   * @throws PersistenceException
   *           if a database access error occurs
   */
  void shutdown();

  /**
   * Retrieve a connection to the database.
   *
   * @return The {@link Connection} instance.
   * @throws PersistenceException
   *           if a database access error occurs
   */
  Connection getConnection();

  /**
   * Close a connection.
   * 
   * @param connection
   *          The connection to close.
   * @throws PersistenceException
   *           if a database access error occurs
   */
  void closeConnection(Connection connection);
}

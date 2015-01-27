package com.tonymanou.computerdb.dao;

import java.sql.Connection;

import com.tonymanou.computerdb.exception.PersistenceException;

public interface IConnectionManager {
  /**
   * Retrieve the current active database connection of this thread or create a new one if none was
   * active.
   *
   * @return A connection to the database.
   * @throws PersistenceException
   *           if a database access error occurs
   */
  Connection getConnection();

  /**
   * Close the current connection to the database.
   * 
   * @throws PersistenceException
   *           if a database access error occurs
   */
  void closeConnection();

}

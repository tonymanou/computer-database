package com.tonymanou.computerdb.dao;

import java.sql.Connection;
import java.util.List;

import com.tonymanou.computerdb.domain.Computer;
import com.tonymanou.computerdb.exception.PersistenceException;
import com.tonymanou.computerdb.pagination.ComputerPage;

/**
 * Interface implemented by DAOs to manage computers.
 *
 * @author tonymanou
 */
public interface IComputerDAO {

  /**
   * Retrieve a list of computers from the database.
   *
   * @param connection
   *          Active database connection.
   * @param page
   *          Description of the pagination to use.
   * @return A list containing the computers.
   * @throws PersistenceException
   *           if an error occurred while processing the query.
   */
  List<Computer> findAll(Connection connection, ComputerPage page);

  /**
   * Create a new computer in the database.
   *
   * @param connection
   *          Active database connection.
   * @param computer
   *          The computer to create.
   * @throws PersistenceException
   *           if an error occurred while processing the query.
   */
  void create(Connection connection, Computer computer);

  /**
   * Update a computer in the database.
   *
   * @param connection
   *          Active database connection.
   * @param computer
   *          The computer to update.
   * @throws PersistenceException
   *           if an error occurred while processing the query.
   */
  void update(Connection connection, Computer computer);

  /**
   * Delete a computer from the database.
   *
   * @param connection
   *          Active database connection.
   * @param id
   *          Id of the computer to delete.
   * @throws PersistenceException
   *           if an error occurred while processing the query.
   */
  void delete(Connection connection, Long id);

  /**
   * Retrieve a computer from the database thanks to the given id.
   *
   * @param connection
   *          Active database connection.
   * @param id
   *          The id of the computer to retrieve.
   * @return The {@link Computer}, or null if no matching company was found.
   * @throws PersistenceException
   *           if an error occurred while processing the query.
   */
  Computer getFromId(Connection connection, Long id);

  /**
   * Count the number of computers.
   * 
   * @param connection
   *          Active database connection.
   * @return The number of computers.
   */
  int count(Connection connection);
}

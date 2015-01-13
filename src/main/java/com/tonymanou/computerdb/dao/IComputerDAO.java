package com.tonymanou.computerdb.dao;

import java.sql.SQLException;
import java.util.List;

import com.tonymanou.computerdb.entity.Computer;

/**
 * Helper class to make actions on computers in the database.
 *
 * @author tonymanou
 */
public interface IComputerDAO {

  /**
   * Retrieve all the computers from the database.
   *
   * @return A list containing all the computers.
   * @throws SQLException
   *           if a database access error occurs
   */
  List<Computer> findAll() throws SQLException;

  /**
   * Create a new computer in the database.
   *
   * @param computer
   *          The computer to create.
   * @throws SQLException
   *           if a database access error occurs
   */
  void create(Computer computer) throws SQLException;

  /**
   * Update a computer in the database.
   *
   * @param computer
   *          The computer to update.
   * @throws SQLException
   *           if a database access error occurs
   */
  void update(Computer computer) throws SQLException;

  /**
   * Delete a computer from the database.
   *
   * @param id
   *          Id of the computer to delete.
   * @throws SQLException
   *           if a database access error occurs
   */
  void delete(Long id) throws SQLException;

  /**
   * Retrieve a computer from the database thanks to the given id.
   *
   * @param id
   *          The id of the computer to retrieve.
   * @return The {@link Computer}, or null if no matching company was found.
   * @throws SQLException
   *           if a database access error occurs
   */
  Computer getFromId(Long id) throws SQLException;
}

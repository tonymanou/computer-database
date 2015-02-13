package com.tonymanou.computerdb.dao;

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
   * @param page
   *          Description of the pagination to use.
   * @return A list containing the computers.
   * @throws PersistenceException
   *           if an error occurred while processing the query.
   */
  List<Computer> findAll(ComputerPage.Builder page);

  /**
   * Create a new computer in the database.
   *
   * @param computer
   *          The computer to create.
   * @throws PersistenceException
   *           if an error occurred while processing the query.
   */
  void create(Computer computer);

  /**
   * Update a computer in the database.
   *
   * @param computer
   *          The computer to update.
   * @throws PersistenceException
   *           if an error occurred while processing the query.
   */
  void update(Computer computer);

  /**
   * Delete a computer from the database.
   *
   * @param id
   *          Id of the computer to delete.
   * @throws PersistenceException
   *           if an error occurred while processing the query.
   */
  void delete(Long id);

  /**
   * Delete all computers from the database that have the specified company id.
   *
   * @param id
   *          Id of the company.
   * @throws PersistenceException
   *           if an error occurred while processing the query.
   */
  void deleteAllWithCompanyId(Long companyId);

  /**
   * Retrieve a computer from the database thanks to the given id.
   *
   * @param id
   *          The id of the computer to retrieve.
   * @return The {@link Computer}, or null if no matching company was found.
   * @throws PersistenceException
   *           if an error occurred while processing the query.
   */
  Computer getFromId(Long id);
}
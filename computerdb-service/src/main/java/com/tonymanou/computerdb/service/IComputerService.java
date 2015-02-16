package com.tonymanou.computerdb.service;

import java.util.List;

import com.tonymanou.computerdb.model.Computer;
import com.tonymanou.computerdb.pagination.ComputerPage;

/**
 * Interface implemented by services to manage computers.
 *
 * @author tonymanou
 */
public interface IComputerService {

  /**
   * Retrieve all the computers from the database.
   *
   * @return A list containing all the computers.
   */
  List<Computer> findAll();

  /**
   * Retrieve a page of computers from the database.
   *
   * @param page
   *          Description of the pagination to use.
   * @return A list containing the computers.
   */
  List<Computer> findPage(ComputerPage.Builder page);

  /**
   * Create a new computer in the database.
   *
   * @param computer
   *          The computer to create.
   */
  void create(Computer computer);

  /**
   * Update a computer in the database.
   *
   * @param computer
   *          The computer to update.
   */
  void update(Computer computer);

  /**
   * Delete a computer from the database.
   *
   * @param id
   *          Id of the computer to delete.
   */
  void delete(Long id);

  /**
   * Retrieve a computer from the database thanks to the given id.
   *
   * @param id
   *          The id of the computer to retrieve.
   * @return The {@link Computer}, or null if no matching company was found.
   */
  Computer getFromId(Long id);
}

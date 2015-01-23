package com.tonymanou.computerdb.dao;

import java.sql.Connection;
import java.util.List;

import com.tonymanou.computerdb.domain.Company;
import com.tonymanou.computerdb.exception.PersistenceException;

/**
 * Interface implemented by DAOs to manage companies.
 *
 * @author tonymanou
 */
public interface ICompanyDAO {

  /**
   * Retrieve all the companies from the database.
   *
   * @param connection
   *          Active database connection.
   * @return A list containing all the companies.
   * @throws PersistenceException
   *           if an error occurred while processing the query.
   */
  List<Company> findAll(Connection connection);

  /**
   * Retrieve a company from the database thanks to the given id.
   *
   * @param connection
   *          Active database connection.
   * @param id
   *          The id of the company to retrieve.
   * @return The {@link Company}, or null if no matching company was found.
   * @throws PersistenceException
   *           if an error occurred while processing the query.
   */
  Company getFromId(Connection connection, Long id);

  /**
   * Remove a company, and its computers, from the database.
   * 
   * @param connection
   *          Active database connection.
   * @param id
   *          The id of the company.
   * @throws PersistenceException
   *           if an error occurred while processing the query.
   */
  void delete(Connection connection, Long id);
}

package com.tonymanou.computerdb.dao;

import java.sql.SQLException;
import java.util.List;

import com.tonymanou.computerdb.entity.Company;

/**
 * Helper class to make actions on companies in the database.
 *
 * @author tonymanou
 */
public interface ICompanyDAO {

  /**
   * Retrieve all the companies from the database.
   *
   * @return A list containing all the companies.
   * @throws SQLException
   *           if a database access error occurs
   */
  List<Company> findAll() throws SQLException;

  /**
   * Retrieve a company from the database thanks to the given id.
   *
   * @param id
   *          The id of the company to retrieve.
   * @return The {@link Company}, or null if no matching company was found.
   * @throws SQLException
   *           if a database access error occurs
   */
  Company getFromId(Long id) throws SQLException;
}

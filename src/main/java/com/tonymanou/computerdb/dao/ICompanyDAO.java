package com.tonymanou.computerdb.dao;

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
   */
  List<Company> findAll();

  /**
   * Retrieve a company from the database thanks to the given id.
   *
   * @param id
   *          The id of the company to retrieve.
   * @return The {@link Company}, or null if no matching company was found.
   */
  Company getFromId(Long id);
}

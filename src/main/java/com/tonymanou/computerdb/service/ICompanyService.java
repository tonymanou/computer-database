package com.tonymanou.computerdb.service;

import java.util.List;

import com.tonymanou.computerdb.entity.Company;

/**
 * Interface implemented by services to manage companies.
 *
 * @author tonymanou
 */
public interface ICompanyService {

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

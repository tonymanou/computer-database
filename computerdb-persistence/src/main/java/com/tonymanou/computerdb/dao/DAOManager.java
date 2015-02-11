package com.tonymanou.computerdb.dao;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Singleton managing DAOs instances.
 *
 * @author tonymanou
 */
public class DAOManager {

  @Autowired
  private IComputerDAO computerDAO;
  @Autowired
  private ICompanyDAO companyDAO;

  public IComputerDAO getComputerDAO() {
    return computerDAO;
  }

  public ICompanyDAO getCompanyDAO() {
    return companyDAO;
  }
}

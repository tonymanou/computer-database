package com.tonymanou.computerdb.dao;

import com.tonymanou.computerdb.dao.impl.SQLCompanyDAO;
import com.tonymanou.computerdb.dao.impl.SQLComputerDAO;

/**
 * Singleton managing DAOs instances.
 *
 * @author tonymanou
 */
public enum DAOManager {

  INSTANCE;

  private IComputerDAO computerDAO;
  private ICompanyDAO companyDAO;

  private DAOManager() {
    computerDAO = new SQLComputerDAO();
    companyDAO = new SQLCompanyDAO();
  }

  public IComputerDAO getComputerDAO() {
    return computerDAO;
  }

  public ICompanyDAO getCompanyDAO() {
    return companyDAO;
  }
}

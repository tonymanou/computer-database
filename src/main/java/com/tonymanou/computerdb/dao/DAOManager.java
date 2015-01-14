package com.tonymanou.computerdb.dao;

import com.tonymanou.computerdb.dao.impl.MockCompanyDAO;
import com.tonymanou.computerdb.dao.impl.MockComputerDAO;
import com.tonymanou.computerdb.dao.impl.SQLCompanyDAO;
import com.tonymanou.computerdb.dao.impl.SQLComputerDAO;

/**
 * Singleton managing DAOs instances.
 *
 * @author tonymanou
 */
public enum DAOManager {

  INSTANCE;

  private static final boolean MOCK = false;
  private IComputerDAO computerDAO;
  private ICompanyDAO companyDAO;

  private DAOManager() {
    if (MOCK) {
      computerDAO = new MockComputerDAO();
      companyDAO = new MockCompanyDAO();
    } else {
      computerDAO = new SQLComputerDAO();
      companyDAO = new SQLCompanyDAO();
    }
  }

  public IComputerDAO getComputerDAO() {
    return computerDAO;
  }

  public ICompanyDAO getCompanyDAO() {
    return companyDAO;
  }
}

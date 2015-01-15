package com.tonymanou.computerdb.dao;

import com.tonymanou.computerdb.dao.impl.MemCompanyDAO;
import com.tonymanou.computerdb.dao.impl.MemComputerDAO;
import com.tonymanou.computerdb.dao.impl.SQLCompanyDAO;
import com.tonymanou.computerdb.dao.impl.SQLComputerDAO;

/**
 * Singleton managing DAOs instances.
 *
 * @author tonymanou
 */
public enum DAOManager {

  INSTANCE;

  private static final boolean IN_MEMORY = true;
  private IComputerDAO computerDAO;
  private ICompanyDAO companyDAO;

  private DAOManager() {
    if (IN_MEMORY) {
      computerDAO = new MemComputerDAO();
      companyDAO = new MemCompanyDAO();
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

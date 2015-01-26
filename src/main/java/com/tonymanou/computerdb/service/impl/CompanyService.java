package com.tonymanou.computerdb.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;

import com.tonymanou.computerdb.dao.ConnectionManager;
import com.tonymanou.computerdb.dao.DAOManager;
import com.tonymanou.computerdb.dao.ICompanyDAO;
import com.tonymanou.computerdb.dao.IComputerDAO;
import com.tonymanou.computerdb.domain.Company;
import com.tonymanou.computerdb.exception.PersistenceException;
import com.tonymanou.computerdb.service.ICompanyService;

/**
 * Standard service implementation to manage companies.
 *
 * @author tonymanou
 */
public class CompanyService implements ICompanyService {

  private ICompanyDAO companyDAO;
  private IComputerDAO computerDAO;

  public CompanyService() {
    companyDAO = DAOManager.INSTANCE.getCompanyDAO();
    computerDAO = DAOManager.INSTANCE.getComputerDAO();
  }

  public CompanyService(ICompanyDAO dao, IComputerDAO computer) {
    companyDAO = dao;
    computerDAO = computer;
  }

  @Override
  public List<Company> findAll() {
    ConnectionManager.INSTANCE.getConnection();
    List<Company> result = companyDAO.findAll();
    ConnectionManager.INSTANCE.closeConnection();
    return result;
  }

  @Override
  public Company getFromId(Long id) {
    ConnectionManager.INSTANCE.getConnection();
    Company result = companyDAO.getFromId(id);
    ConnectionManager.INSTANCE.closeConnection();
    return result;
  }

  @Override
  public void delete(Long id) {
    Connection connection = ConnectionManager.INSTANCE.getConnection();
    Savepoint savepoint = null;
    try {
      connection.setAutoCommit(false);
      savepoint = connection.setSavepoint();
      computerDAO.deleteAllWithCompanyId(id);
      companyDAO.delete(id);
      connection.commit();
    } catch (SQLException | PersistenceException e) {
      if (savepoint != null) {
        try {
          connection.rollback(savepoint);
        } catch (SQLException e1) {
          throw new PersistenceException(e1);
        }
      }
      throw new PersistenceException(e);
    } finally {
      ConnectionManager.INSTANCE.closeConnection();
    }
  }
}

package com.tonymanou.computerdb.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tonymanou.computerdb.dao.ICompanyDAO;
import com.tonymanou.computerdb.dao.IComputerDAO;
import com.tonymanou.computerdb.dao.IConnectionManager;
import com.tonymanou.computerdb.domain.Company;
import com.tonymanou.computerdb.exception.PersistenceException;
import com.tonymanou.computerdb.service.ICompanyService;

/**
 * Standard service implementation to manage companies.
 *
 * @author tonymanou
 */
@Component
public class CompanyService implements ICompanyService {

  @Autowired
  private IConnectionManager connectionManager;
  @Autowired
  private ICompanyDAO companyDAO;
  @Autowired
  private IComputerDAO computerDAO;

  public CompanyService() {
  }

  public CompanyService(ICompanyDAO dao, IComputerDAO computer) {
    companyDAO = dao;
    computerDAO = computer;
  }

  @Override
  public List<Company> findAll() {
    connectionManager.getConnection();
    List<Company> result = companyDAO.findAll();
    connectionManager.closeConnection();
    return result;
  }

  @Override
  public Company getFromId(Long id) {
    connectionManager.getConnection();
    Company result = companyDAO.getFromId(id);
    connectionManager.closeConnection();
    return result;
  }

  @Override
  public void delete(Long id) {
    Connection connection = connectionManager.getConnection();
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
      connectionManager.closeConnection();
    }
  }
}

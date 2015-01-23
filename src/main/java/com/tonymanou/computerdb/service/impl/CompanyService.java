package com.tonymanou.computerdb.service.impl;

import java.sql.Connection;
import java.util.List;

import com.tonymanou.computerdb.dao.DAOManager;
import com.tonymanou.computerdb.dao.ICompanyDAO;
import com.tonymanou.computerdb.dao.impl.SQLUtil;
import com.tonymanou.computerdb.domain.Company;
import com.tonymanou.computerdb.service.ICompanyService;

/**
 * Standard service implementation to manage companies.
 *
 * @author tonymanou
 */
public class CompanyService implements ICompanyService {

  private ICompanyDAO companyDAO;

  public CompanyService() {
    companyDAO = DAOManager.INSTANCE.getCompanyDAO();
  }

  public CompanyService(ICompanyDAO dao) {
    companyDAO = dao;
  }

  @Override
  public List<Company> findAll() {
    Connection connection = SQLUtil.getConnection();
    List<Company> result = companyDAO.findAll(connection);
    SQLUtil.closeConnection(connection);
    return result;
  }

  @Override
  public Company getFromId(Long id) {
    Connection connection = SQLUtil.getConnection();
    Company result = companyDAO.getFromId(connection, id);
    SQLUtil.closeConnection(connection);
    return result;
  }
}

package com.tonymanou.computerdb.service.impl;

import java.util.List;

import com.tonymanou.computerdb.dao.DAOManager;
import com.tonymanou.computerdb.dao.ICompanyDAO;
import com.tonymanou.computerdb.entity.Company;
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
    return companyDAO.findAll();
  }

  @Override
  public Company getFromId(Long id) {
    return companyDAO.getFromId(id);
  }
}

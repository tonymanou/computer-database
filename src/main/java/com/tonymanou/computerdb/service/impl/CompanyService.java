package com.tonymanou.computerdb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
@Component
public class CompanyService implements ICompanyService {

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

  @Transactional(readOnly = true)
  @Override
  public List<Company> findAll() {
    List<Company> result = companyDAO.findAll();
    return result;
  }

  @Transactional(readOnly = true)
  @Override
  public Company getFromId(Long id) {
    Company result = companyDAO.getFromId(id);
    return result;
  }

  @Transactional(rollbackFor = { PersistenceException.class })
  @Override
  public void delete(Long id) {
    computerDAO.deleteAllWithCompanyId(id);
    companyDAO.delete(id);
  }
}

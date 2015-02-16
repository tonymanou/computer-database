package com.tonymanou.computerdb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tonymanou.computerdb.dao.ICompanyDAO;
import com.tonymanou.computerdb.dao.IComputerDAO;
import com.tonymanou.computerdb.model.Company;
import com.tonymanou.computerdb.service.ICompanyService;

/**
 * Standard service implementation to manage companies.
 *
 * @author tonymanou
 */
@Service
public class CompanyService implements ICompanyService {

  @Autowired
  private ICompanyDAO companyDAO;
  @Autowired
  private IComputerDAO computerDAO;

  @Transactional(readOnly = true)
  @Override
  public List<Company> findAll() {
    return companyDAO.findAll();
  }

  @Transactional(readOnly = true)
  @Override
  public Company getFromId(Long id) {
    return companyDAO.getFromId(id);
  }

  @Transactional(rollbackFor = { Exception.class })
  @Override
  public void delete(Long id) {
    computerDAO.deleteAllWithCompanyId(id);
    companyDAO.delete(id);
  }
}

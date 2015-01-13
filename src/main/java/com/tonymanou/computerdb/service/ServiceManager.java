package com.tonymanou.computerdb.service;

import com.tonymanou.computerdb.service.impl.CompanyService;
import com.tonymanou.computerdb.service.impl.ComputerService;

/**
 * Singleton managing services instances.
 *
 * @author tonymanou
 */
public enum ServiceManager {

  INSTANCE;

  private IComputerService computerService;
  private ICompanyService companyService;

  private ServiceManager() {
    computerService = new ComputerService();
    companyService = new CompanyService();
  }

  public IComputerService getComputerService() {
    return computerService;
  }

  public ICompanyService getCompanyService() {
    return companyService;
  }
}

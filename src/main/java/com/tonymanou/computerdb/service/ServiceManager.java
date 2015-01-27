package com.tonymanou.computerdb.service;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Singleton managing services instances.
 *
 * @author tonymanou
 */
public class ServiceManager {

  @Autowired
  private IComputerService computerService;
  @Autowired
  private ICompanyService companyService;

  public IComputerService getComputerService() {
    return computerService;
  }

  public ICompanyService getCompanyService() {
    return companyService;
  }
}

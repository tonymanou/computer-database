package com.tonymanou.computerdb.mapper;

import com.tonymanou.computerdb.mapper.impl.CompanyMapper;
import com.tonymanou.computerdb.mapper.impl.ComputerMapper;

public enum MapperManager {

  INSTANCE;

  private ComputerMapper computerMapper;
  private CompanyMapper companyMapper;

  private MapperManager() {
    computerMapper = new ComputerMapper();
    companyMapper = new CompanyMapper();
  }

  public ComputerMapper getComputerMapper() {
    return computerMapper;
  }

  public CompanyMapper getCompanyMapper() {
    return companyMapper;
  }
}

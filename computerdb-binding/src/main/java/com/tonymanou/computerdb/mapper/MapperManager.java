package com.tonymanou.computerdb.mapper;

import org.springframework.beans.factory.annotation.Autowired;

import com.tonymanou.computerdb.mapper.impl.CompanyMapper;
import com.tonymanou.computerdb.mapper.impl.ComputerMapper;

public class MapperManager {

  @Autowired
  private ComputerMapper computerMapper;
  @Autowired
  private CompanyMapper companyMapper;

  public ComputerMapper getComputerMapper() {
    return computerMapper;
  }

  public CompanyMapper getCompanyMapper() {
    return companyMapper;
  }
}

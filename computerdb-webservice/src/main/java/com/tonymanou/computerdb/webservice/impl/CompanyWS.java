package com.tonymanou.computerdb.webservice.impl;

import javax.jws.WebService;

import com.tonymanou.computerdb.model.Company;
import com.tonymanou.computerdb.webservice.ICompanyWS;
import com.tonymanou.computerdb.webservice.wrapper.ListWrapper;

@WebService(endpointInterface = "com.tonymanou.computerdb.webservice.ICompanyWS")
public class CompanyWS implements ICompanyWS {

  @Override
  public ListWrapper<Company> findAll() {
    return null;
  }

  @Override
  public Company getFromId(Long id) {
    return null;
  }

  @Override
  public void delete(Long id) {
  }
}

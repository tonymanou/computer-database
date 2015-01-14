package com.tonymanou.computerdb.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.tonymanou.computerdb.dao.ICompanyDAO;
import com.tonymanou.computerdb.entity.Company;

public class MockCompanyDAO implements ICompanyDAO {

  @Override
  public List<Company> findAll() {
    List<Company> list = new ArrayList<>(1);
    list.add(getFromId(0L));
    return list;
  }

  @Override
  public Company getFromId(Long id) {
    // @formatter:off
    return Company.getBuilder("Mock company")
        .setId(id)
        .build();
    // @formatter:on
  }
}

package com.tonymanou.computerdb.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tonymanou.computerdb.dao.ICompanyDAO;
import com.tonymanou.computerdb.entity.Company;

public class MemCompanyDAO implements ICompanyDAO {

  private Map<Long, Company> map;
  private Long index;

  public MemCompanyDAO() {
    map = new HashMap<>(1);
    // @formatter:off
    map.put(1L, Company.getBuilder("Apple")
        .setId(1L)
        .build());
    // @formatter:on
    index = 2L;
  }

  @Override
  public List<Company> findAll() {
    return new ArrayList<>(map.values());
  }

  @Override
  public Company getFromId(Long id) {
    return map.get(id);
  }
}

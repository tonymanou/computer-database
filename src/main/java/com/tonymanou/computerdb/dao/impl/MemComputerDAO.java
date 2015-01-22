package com.tonymanou.computerdb.dao.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tonymanou.computerdb.dao.IComputerDAO;
import com.tonymanou.computerdb.domain.Company;
import com.tonymanou.computerdb.domain.Computer;
import com.tonymanou.computerdb.exception.PersistenceException;
import com.tonymanou.computerdb.pagination.ComputerPage;

public class MemComputerDAO implements IComputerDAO {

  private Map<Long, Computer> map;
  private Long index;

  public MemComputerDAO() {
    map = new HashMap<>(2);
    // @formatter:off
    map.put(1L, Computer.getBuilder("Vaio SCE1230-Z")
        .setId(1L)
        .setIntroduced(LocalDate.of(2011, 5, 12))
        .build());
    map.put(2L, Computer.getBuilder("Macbook Pro 13 late 2010")
        .setId(2L)
        .setIntroduced(LocalDate.of(2010, 10, 01))
        .setDiscontinued(LocalDate.of(2012, 02, 25))
        .setCompany(Company.getBuilder("Apple")
            .setId(1L)
            .build())
        .build());
    // @formatter:on
    index = 3L;
  }

  @Override
  public List<Computer> findAll(ComputerPage page) {
    return new ArrayList<>(map.values());
  }

  @Override
  public void create(Computer computer) {
    computer.setId((long) index);
    map.put(index, computer);
    index++;
  }

  @Override
  public void update(Computer computer) {
    Long key = computer.getId();
    if (key != null && map.containsKey(key)) {
      map.put(key, computer);
    } else {
      throw new PersistenceException("Cannot update " + computer + " as it does not exist.");
    }
  }

  @Override
  public void delete(Long id) {
    map.remove(id);
  }

  @Override
  public Computer getFromId(Long id) {
    return map.get(id);
  }

  @Override
  public int count() {
    return map.size();
  }
}

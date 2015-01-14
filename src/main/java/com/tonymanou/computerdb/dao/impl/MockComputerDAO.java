package com.tonymanou.computerdb.dao.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.tonymanou.computerdb.dao.IComputerDAO;
import com.tonymanou.computerdb.entity.Company;
import com.tonymanou.computerdb.entity.Computer;

public class MockComputerDAO implements IComputerDAO {

  @Override
  public List<Computer> findAll() {
    List<Computer> list = new ArrayList<>(1);
    list.add(getFromId(0L));
    return list;
  }

  @Override
  public void create(Computer computer) {
    System.out.println("DAO: [create] " + computer);
  }

  @Override
  public void update(Computer computer) {
    System.out.println("DAO: [update] " + computer);
  }

  @Override
  public void delete(Long id) {
    System.out.println("DAO: [delete] Computer[id=" + id + "]");
  }

  @Override
  public Computer getFromId(Long id) {
    // @formatter:off
    return Computer.getBuilder("Mock computer")
        .setId(id)
        .setIntroduced(LocalDateTime.MIN)
        .setDiscontinued(LocalDateTime.MAX)
        .setCompany(Company.getBuilder("Mock company")
            .setId(0L).build())
        .build();
    // @formatter:on
  }
}

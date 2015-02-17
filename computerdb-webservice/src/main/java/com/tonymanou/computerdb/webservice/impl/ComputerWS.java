package com.tonymanou.computerdb.webservice.impl;

import javax.jws.WebService;

import com.tonymanou.computerdb.model.Computer;
import com.tonymanou.computerdb.pagination.ComputerPage.Builder;
import com.tonymanou.computerdb.webservice.IComputerWS;
import com.tonymanou.computerdb.webservice.wrapper.ListWrapper;

@WebService(endpointInterface = "com.tonymanou.computerdb.webservice.IComputerWS")
public class ComputerWS implements IComputerWS {

  @Override
  public ListWrapper<Computer> findAll() {
    return null;
  }

  @Override
  public ListWrapper<Computer> findPage(Builder page) {
    return null;
  }

  @Override
  public void create(Computer computer) {
  }

  @Override
  public void update(Computer computer) {
  }

  @Override
  public void delete(Long id) {
  }

  @Override
  public Computer getFromId(Long id) {
    return null;
  }
}

package com.tonymanou.computerdb.service.impl;

import java.util.List;

import com.tonymanou.computerdb.dao.IComputerDAO;
import com.tonymanou.computerdb.dao.DAOManager;
import com.tonymanou.computerdb.entity.Computer;
import com.tonymanou.computerdb.service.IComputerService;

/**
 * Standard service implementation to manage computers.
 *
 * @author tonymanou
 */
public class ComputerService implements IComputerService {

  private IComputerDAO computerDAO;

  public ComputerService() {
    computerDAO = DAOManager.INSTANCE.getComputerDAO();
  }

  @Override
  public List<Computer> findAll() {
    return computerDAO.findAll();
  }

  @Override
  public void create(Computer computer) {
    computerDAO.create(computer);
  }

  @Override
  public void update(Computer computer) {
    computerDAO.update(computer);
  }

  @Override
  public void delete(Long id) {
    computerDAO.delete(id);
  }

  @Override
  public Computer getFromId(Long id) {
    return computerDAO.getFromId(id);
  }
}

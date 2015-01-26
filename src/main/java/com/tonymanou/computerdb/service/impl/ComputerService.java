package com.tonymanou.computerdb.service.impl;

import java.util.List;

import com.tonymanou.computerdb.dao.ConnectionManager;
import com.tonymanou.computerdb.dao.DAOManager;
import com.tonymanou.computerdb.dao.IComputerDAO;
import com.tonymanou.computerdb.domain.Computer;
import com.tonymanou.computerdb.pagination.ComputerPage;
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

  public ComputerService(IComputerDAO dao) {
    computerDAO = dao;
  }

  @Override
  public List<Computer> findAll() {
    return findAll(ComputerPage.getBuilder());
  }

  @Override
  public List<Computer> findAll(ComputerPage.Builder page) {
    ConnectionManager.INSTANCE.getConnection();
    List<Computer> result = computerDAO.findAll(page);
    ConnectionManager.INSTANCE.closeConnection();
    return result;
  }

  @Override
  public void create(Computer computer) {
    ConnectionManager.INSTANCE.getConnection();
    computerDAO.create(computer);
    ConnectionManager.INSTANCE.closeConnection();
  }

  @Override
  public void update(Computer computer) {
    ConnectionManager.INSTANCE.getConnection();
    computerDAO.update(computer);
    ConnectionManager.INSTANCE.closeConnection();
  }

  @Override
  public void delete(Long id) {
    ConnectionManager.INSTANCE.getConnection();
    computerDAO.delete(id);
    ConnectionManager.INSTANCE.closeConnection();
  }

  @Override
  public Computer getFromId(Long id) {
    ConnectionManager.INSTANCE.getConnection();
    Computer result = computerDAO.getFromId(id);
    ConnectionManager.INSTANCE.closeConnection();
    return result;
  }

  @Override
  public int count() {
    ConnectionManager.INSTANCE.getConnection();
    int result = computerDAO.count();
    ConnectionManager.INSTANCE.closeConnection();
    return result;
  }
}

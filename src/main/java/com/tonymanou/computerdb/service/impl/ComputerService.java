package com.tonymanou.computerdb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tonymanou.computerdb.dao.IComputerDAO;
import com.tonymanou.computerdb.dao.IConnectionManager;
import com.tonymanou.computerdb.domain.Computer;
import com.tonymanou.computerdb.pagination.ComputerPage;
import com.tonymanou.computerdb.service.IComputerService;

/**
 * Standard service implementation to manage computers.
 *
 * @author tonymanou
 */
@Component
public class ComputerService implements IComputerService {

  @Autowired
  private IConnectionManager connectionManager;
  @Autowired
  private IComputerDAO computerDAO;

  public ComputerService() {
  }

  public ComputerService(IComputerDAO dao) {
    computerDAO = dao;
  }

  @Transactional(readOnly = true)
  @Override
  public List<Computer> findAll() {
    return findAll(ComputerPage.getBuilder());
  }

  @Transactional(readOnly = true)
  @Override
  public List<Computer> findAll(ComputerPage.Builder page) {
    connectionManager.getConnection();
    List<Computer> result = computerDAO.findAll(page);
    connectionManager.closeConnection();
    return result;
  }

  @Transactional
  @Override
  public void create(Computer computer) {
    connectionManager.getConnection();
    computerDAO.create(computer);
    connectionManager.closeConnection();
  }

  @Transactional
  @Override
  public void update(Computer computer) {
    connectionManager.getConnection();
    computerDAO.update(computer);
    connectionManager.closeConnection();
  }

  @Transactional
  @Override
  public void delete(Long id) {
    connectionManager.getConnection();
    computerDAO.delete(id);
    connectionManager.closeConnection();
  }

  @Transactional(readOnly = true)
  @Override
  public Computer getFromId(Long id) {
    connectionManager.getConnection();
    Computer result = computerDAO.getFromId(id);
    connectionManager.closeConnection();
    return result;
  }

  @Transactional(readOnly = true)
  @Override
  public int count() {
    connectionManager.getConnection();
    int result = computerDAO.count();
    connectionManager.closeConnection();
    return result;
  }
}

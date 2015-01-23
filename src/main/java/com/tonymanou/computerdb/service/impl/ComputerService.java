package com.tonymanou.computerdb.service.impl;

import java.sql.Connection;
import java.util.List;

import com.tonymanou.computerdb.dao.DAOManager;
import com.tonymanou.computerdb.dao.IComputerDAO;
import com.tonymanou.computerdb.dao.impl.SQLUtil;
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
    return findAll(new ComputerPage());
  }

  @Override
  public List<Computer> findAll(ComputerPage page) {
    Connection connection = SQLUtil.getConnection();
    List<Computer> result = computerDAO.findAll(connection, page);
    SQLUtil.closeConnection(connection);
    return result;
  }

  @Override
  public void create(Computer computer) {
    Connection connection = SQLUtil.getConnection();
    computerDAO.create(connection, computer);
    SQLUtil.closeConnection(connection);
  }

  @Override
  public void update(Computer computer) {
    Connection connection = SQLUtil.getConnection();
    computerDAO.update(connection, computer);
    SQLUtil.closeConnection(connection);
  }

  @Override
  public void delete(Long id) {
    Connection connection = SQLUtil.getConnection();
    computerDAO.delete(connection, id);
    SQLUtil.closeConnection(connection);
  }

  @Override
  public Computer getFromId(Long id) {
    Connection connection = SQLUtil.getConnection();
    Computer result = computerDAO.getFromId(connection, id);
    SQLUtil.closeConnection(connection);
    return result;
  }

  @Override
  public int count() {
    Connection connection = SQLUtil.getConnection();
    int result = computerDAO.count(connection);
    SQLUtil.closeConnection(connection);
    return result;
  }
}

package com.tonymanou.computerdb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tonymanou.computerdb.dao.IComputerDAO;
import com.tonymanou.computerdb.domain.Computer;
import com.tonymanou.computerdb.pagination.ComputerPage;
import com.tonymanou.computerdb.service.IComputerService;

/**
 * Standard service implementation to manage computers.
 *
 * @author tonymanou
 */
@Service
public class ComputerService implements IComputerService {

  @Autowired
  private IComputerDAO computerDAO;

  @Transactional(readOnly = true)
  @Override
  public List<Computer> findAll() {
    return computerDAO.findAll();
  }

  @Transactional(readOnly = true)
  @Override
  public List<Computer> findPage(ComputerPage.Builder page) {
    return computerDAO.findPage(page);
  }

  @Transactional
  @Override
  public void create(Computer computer) {
    computerDAO.create(computer);
  }

  @Transactional
  @Override
  public void update(Computer computer) {
    computerDAO.update(computer);
  }

  @Transactional
  @Override
  public void delete(Long id) {
    computerDAO.delete(id);
  }

  @Transactional(readOnly = true)
  @Override
  public Computer getFromId(Long id) {
    return computerDAO.getFromId(id);
  }
}

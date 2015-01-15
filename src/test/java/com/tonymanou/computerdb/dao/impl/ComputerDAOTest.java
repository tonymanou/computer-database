package com.tonymanou.computerdb.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.tonymanou.computerdb.entity.Computer;

@RunWith(MockitoJUnitRunner.class)
public class ComputerDAOTest {

  private static SQLComputerDAO computerDAO;
  private static Computer computer1;
  private static Computer computer2;

  @BeforeClass
  public static void setUp() {
    computerDAO = new SQLComputerDAO();
  }

  @Test
  public void findAllTest() {
    // TODO implement test
  }

  @Test
  public void create() {
    // TODO implement test
  }

  @Test
  public void update() {
    // TODO implement test
  }

  @Test
  public void delete() {
    // TODO implement test
  }

  @Test
  public void getFromIdTest() {
    // TODO implement test
  }
}

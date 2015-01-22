package com.tonymanou.computerdb.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.tonymanou.computerdb.dao.IComputerDAO;
import com.tonymanou.computerdb.domain.Computer;

@RunWith(MockitoJUnitRunner.class)
public class ComputerServiceTest {

  private static final Long ID = 5136L;
  private static final Long ID2 = 26734L;

  private static ComputerService service;
  private static IComputerDAO computerDAO;
  private static Computer computer1;
  private static Computer computer2;

  @BeforeClass
  public static void setUp() {
    computerDAO = mock(IComputerDAO.class);
    service = new ComputerService(computerDAO);

    computer1 = Computer.getBuilder("Computer 1").setId(ID).build();
    computer2 = Computer.getBuilder("Computer 2").setId(ID2).build();

    when(computerDAO.findAll()).thenReturn(Arrays.asList(computer1, computer2));
    doAnswer(new Answer<Object>() {
      public Object answer(InvocationOnMock invocation) {
        Object[] args = invocation.getArguments();
        return args;
      }
    }).when(computerDAO).create(computer1);
    doAnswer(new Answer<Object>() {
      public Object answer(InvocationOnMock invocation) {
        Object[] args = invocation.getArguments();
        return args;
      }
    }).when(computerDAO).update(computer1);
    doAnswer(new Answer<Object>() {
      public Object answer(InvocationOnMock invocation) {
        Object[] args = invocation.getArguments();
        return args;
      }
    }).when(computerDAO).delete(ID);
    when(computerDAO.getFromId(ID)).thenReturn(computer1);
  }

  @Test
  public void findAllTest() {
    List<Computer> list = service.findAll();

    assertNotNull(list);
    assertEquals(2, list.size());

    Computer computer = Computer.getBuilder("Computer 2").setId(ID2).build();
    assertEquals(computer, list.get(1));

    verify(computerDAO).findAll();
  }

  @Test
  public void create() {
    service.create(computer1);

    verify(computerDAO).create(computer1);
  }

  @Test
  public void update() {
    service.update(computer1);

    verify(computerDAO).update(computer1);
  }

  @Test
  public void delete() {
    service.delete(ID);

    verify(computerDAO).delete(ID);
  }

  @Test
  public void getFromIdTest() {
    Computer computer0 = service.getFromId(ID);
    assertNotNull(computer0);

    Computer computer = Computer.getBuilder("Computer 1").setId(ID).build();
    assertEquals(computer, computer0);

    verify(computerDAO).getFromId(ID);
  }
}

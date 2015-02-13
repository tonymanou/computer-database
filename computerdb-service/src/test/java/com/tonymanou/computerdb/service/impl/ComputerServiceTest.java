package com.tonymanou.computerdb.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tonymanou.computerdb.dao.IComputerDAO;
import com.tonymanou.computerdb.domain.Computer;
import com.tonymanou.computerdb.pagination.ComputerPage;
import com.tonymanou.computerdb.service.IComputerService;

@RunWith(MockitoJUnitRunner.class)
public class ComputerServiceTest {

  private static final Long ID1 = 5196L;
  private static final Long ID2 = 26734L;
  private static final Long ID3 = 902786354L;

  private static AnnotationConfigApplicationContext context;
  private static IComputerService service;
  private static IComputerDAO computerDAO;
  private static Computer computer1;
  private static Computer computer2;
  private static Computer computer3;
  private static List<Computer> computerList;
  private static ComputerPage.Builder page;

  private static boolean createCalled;
  private static boolean deleteCalled;

  /**
   * Spring context configuration class.
   */
  @Configuration
  public static class ComputerServiceTestConfig {

    @Bean
    public IComputerDAO computerDAO() {
      return mock(IComputerDAO.class);
    }

    @Bean
    public IComputerService computerService() {
      return new ComputerService();
    }
  }

  @BeforeClass
  public static void setUp() {
    context = new AnnotationConfigApplicationContext(ComputerServiceTestConfig.class);

    service = context.getBean(IComputerService.class);

    computer1 = Computer.getBuilder("Computer 1").setId(ID1).build();
    computer2 = Computer.getBuilder("Computer 2").setId(ID2).build();
    computer3 = Computer.getBuilder("Computer 3").setId(ID3).build();

    page = ComputerPage.getBuilder();

    computerList = new ArrayList<>();
    computerList.add(computer1);
    computerList.add(computer2);

    // Mock computer DAO
    computerDAO = context.getBean(IComputerDAO.class);
    when(computerDAO.findAll(page)).thenReturn(computerList);
    doAnswer(new Answer<Object>() {
      public Object answer(InvocationOnMock invocation) {
        computerList.add(computer3);
        return invocation.getArguments();
      }
    }).when(computerDAO).create(computer3);
    doAnswer(new Answer<Object>() {
      public Object answer(InvocationOnMock invocation) {
        return invocation.getArguments();
      }
    }).when(computerDAO).update(computer1);
    doAnswer(new Answer<Object>() {
      public Object answer(InvocationOnMock invocation) {
        computerList.remove(computer2);
        return invocation.getArguments();
      }
    }).when(computerDAO).delete(ID2);
    when(computerDAO.getFromId(ID2)).thenReturn(computer2);
  }

  private static int getListSize() {
    int size = 2;
    if (createCalled) {
      size++;
    }
    if (deleteCalled) {
      size--;
    }
    return size;
  }

  @Test
  public void findAll() {
    List<Computer> list = service.findAll(page);

    assertNotNull(list);
    assertEquals(getListSize(), list.size());

    Computer computer = Computer.getBuilder("Computer 1").setId(ID1).build();
    assertEquals(computer, list.get(0));

    verify(computerDAO).findAll(page);
  }

  @Test
  public void create() {
    service.create(computer3);
    createCalled = true;

    assertEquals(getListSize(), computerList.size());

    verify(computerDAO).create(computer3);
  }

  @Test
  public void update() {
    service.update(computer1);

    verify(computerDAO).update(computer1);
  }

  @Test
  public void delete() {
    service.delete(ID2);
    deleteCalled = true;

    assertFalse(computerList.contains(computer2));
    assertEquals(getListSize(), computerList.size());

    verify(computerDAO).delete(ID2);
  }

  @Test
  public void getFromId() {
    Computer computer0 = service.getFromId(ID2);
    assertNotNull(computer0);

    Computer computer = Computer.getBuilder("Computer 2").setId(ID2).build();
    assertEquals(computer, computer0);

    verify(computerDAO).getFromId(ID2);
  }
}

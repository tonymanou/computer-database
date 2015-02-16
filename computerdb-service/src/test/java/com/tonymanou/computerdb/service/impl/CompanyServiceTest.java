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

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tonymanou.computerdb.dao.ICompanyDAO;
import com.tonymanou.computerdb.dao.IComputerDAO;
import com.tonymanou.computerdb.model.Company;
import com.tonymanou.computerdb.service.ICompanyService;

@RunWith(MockitoJUnitRunner.class)
public class CompanyServiceTest {

  private static final Long ID1 = 845L;
  private static final Long ID2 = 3517L;

  private static AnnotationConfigApplicationContext context;
  private static ICompanyService service;
  private static ICompanyDAO companyDAO;
  private static IComputerDAO computerDAO;
  private static Company company1;
  private static Company company2;
  private static List<Company> companyList;

  /**
   * Spring context configuration class.
   */
  @Configuration
  public static class CompanyServiceTestConfig {
    @Bean
    public ICompanyDAO companyDAO() {
      return mock(ICompanyDAO.class);
    }

    @Bean
    public IComputerDAO computerDAO() {
      return mock(IComputerDAO.class);
    }

    @Bean
    public ICompanyService companyService() {
      return new CompanyService();
    }
  }

  @BeforeClass
  public static void setUp() {
    context = new AnnotationConfigApplicationContext(CompanyServiceTestConfig.class);
    service = context.getBean(ICompanyService.class);

    company1 = Company.getBuilder("Company 1").setId(ID1).build();
    company2 = Company.getBuilder("Company 2").setId(ID2).build();
    companyList = new ArrayList<>(2);

    // Mock company DAO
    companyDAO = context.getBean(ICompanyDAO.class);

    when(companyDAO.findAll()).thenReturn(companyList);

    when(companyDAO.getFromId(ID1)).thenReturn(company1);

    doAnswer(new Answer<Object>() {
      public Object answer(InvocationOnMock invocation) {
        companyList.remove(company2);
        return invocation.getArguments();
      }
    }).when(companyDAO).delete(ID2);

    // Mock computer DAO
    computerDAO = context.getBean(IComputerDAO.class);

    doAnswer(new Answer<Object>() {
      public Object answer(InvocationOnMock invocation) {
        return invocation.getArguments();
      }
    }).when(computerDAO).deleteAllWithCompanyId(ID2);
  }

  @Before
  public void resetCompanyList() {
    companyList.clear();
    companyList.add(company1);
    companyList.add(company2);
  }

  @Test
  public void findAllTest() {
    List<Company> list = service.findAll();

    assertNotNull(list);
    assertEquals(2, list.size());

    Company company = Company.getBuilder("Company 2").setId(ID2).build();
    assertEquals(company, list.get(1));

    verify(companyDAO).findAll();
  }

  @Test
  public void getFromIdTest() {
    Company company0 = service.getFromId(ID1);
    assertNotNull(company0);

    Company company = Company.getBuilder("Company 1").setId(ID1).build();
    assertEquals(company, company0);

    verify(companyDAO).getFromId(ID1);
  }

  @Test
  public void deleteById() {
    service.delete(ID2);

    List<Company> list = service.findAll();

    assertNotNull(list);
    assertEquals(1, list.size());
    assertFalse(list.contains(company2));

    verify(companyDAO).delete(ID2);
    verify(computerDAO).deleteAllWithCompanyId(ID2);
  }
}

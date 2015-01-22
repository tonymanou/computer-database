package com.tonymanou.computerdb.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.tonymanou.computerdb.dao.ICompanyDAO;
import com.tonymanou.computerdb.domain.Company;

@RunWith(MockitoJUnitRunner.class)
public class CompanyServiceTest {

  private static final Long ID = 2134L;
  private static final Long ID2 = 213434L;

  private static CompanyService service;
  private static ICompanyDAO companyDAO;
  private static Company company1;
  private static Company company2;

  @BeforeClass
  public static void setUp() {
    companyDAO = mock(ICompanyDAO.class);
    service = new CompanyService(companyDAO);

    company1 = Company.getBuilder("Company 1").setId(ID).build();
    company2 = Company.getBuilder("Company 2").setId(ID2).build();

    when(companyDAO.findAll()).thenReturn(Arrays.asList(company1, company2));
    when(companyDAO.getFromId(ID)).thenReturn(company1);
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
    Company company0 = service.getFromId(ID);
    assertNotNull(company0);

    Company company = Company.getBuilder("Company 1").setId(ID).build();
    assertEquals(company, company0);

    verify(companyDAO).getFromId(ID);
  }
}

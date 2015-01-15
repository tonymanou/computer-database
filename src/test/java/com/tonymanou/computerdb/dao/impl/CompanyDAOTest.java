package com.tonymanou.computerdb.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.tonymanou.computerdb.entity.Company;

@RunWith(MockitoJUnitRunner.class)
public class CompanyDAOTest {

  private static SQLCompanyDAO companyDAO;
  private static Company company1;
  private static Company company2;

  @BeforeClass
  public static void setUp() {
    companyDAO = new SQLCompanyDAO();
  }

  @Test
  public void findAllTest() {
    // TODO implement test
  }

  @Test
  public void getFromIdTest() {
    // TODO implement test
  }
}

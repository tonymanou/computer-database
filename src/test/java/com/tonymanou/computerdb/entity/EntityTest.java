package com.tonymanou.computerdb.entity;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

import com.tonymanou.computerdb.domain.Company;
import com.tonymanou.computerdb.domain.Computer;

public class EntityTest {

  @Test
  public void equalityBetweenComputers() {
    Computer c1 = Computer.getBuilder("Computer 1").setId(10L)
        .setIntroduced(LocalDate.of(2012, 12, 31))
        .setDiscontinued(LocalDate.of(2020, 02, 29))
        .setCompany(Company.getBuilder("Company 1").setId(2L).build()).build();
    Computer c2 = Computer.getBuilder("Computer 1").setId(10L)
        .setIntroduced(LocalDate.of(2012, 12, 31))
        .setDiscontinued(LocalDate.of(2020, 02, 29))
        .setCompany(Company.getBuilder("Company 1").setId(2L).build()).build();

    assertEquals(c1, c2);
  }

  @Test
  public void equalityBetweenCompanies() {
    Company c1 = Company.getBuilder("Company 1").setId(2L).build();
    Company c2 = Company.getBuilder("Company 1").setId(2L).build();

    assertEquals(c1, c2);
  }
}

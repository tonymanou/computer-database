package com.tonymanou.computerdb.entity;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Test;

import com.tonymanou.computerdb.entity.Company;
import com.tonymanou.computerdb.entity.Computer;

public class EntityTest {

  @Test
  public void equalityBetweenComputers() {
    Computer c1 = Computer.getBuilder("Computer 1").setId(10L)
        .setIntroduced(LocalDateTime.of(2012, 12, 31, 0, 0))
        .setDiscontinued(LocalDateTime.of(2020, 02, 29, 0, 0))
        .setCompany(Company.getBuilder("Company 1").setId(2L).build()).build();
    Computer c2 = Computer.getBuilder("Computer 1").setId(10L)
        .setIntroduced(LocalDateTime.of(2012, 12, 31, 0, 0))
        .setDiscontinued(LocalDateTime.of(2020, 02, 29, 0, 0))
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

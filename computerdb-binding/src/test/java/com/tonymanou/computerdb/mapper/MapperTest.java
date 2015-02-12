package com.tonymanou.computerdb.mapper;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.tonymanou.computerdb.domain.Company;
import com.tonymanou.computerdb.domain.Computer;
import com.tonymanou.computerdb.dto.CompanyDTO;
import com.tonymanou.computerdb.dto.ComputerDTO;
import com.tonymanou.computerdb.mapper.impl.CompanyMapper;
import com.tonymanou.computerdb.mapper.impl.ComputerMapper;

public class MapperTest {

  private IEntityMapper<Computer, ComputerDTO> computerMapper;
  private IEntityMapper<Company, CompanyDTO> companyMapper;

  @Before
  public void initTest() {
    computerMapper = new ComputerMapper();
    companyMapper = new CompanyMapper();
  }

  @Test
  public void equalityBetweenComputersFromDTO() {
    // @formatter:off
    ComputerDTO cd1 = ComputerDTO.getBuilder("Computer 1")
        .setId(10L)
        .setIntroduced("2012-12-31")
        .setDiscontinued("2020-02-29")
        .setCompany("Company 1")
        .setCompanyId(2L)
        .build();
    ComputerDTO cd2 = ComputerDTO.getBuilder("Computer 1")
        .setId(10L)
        .setIntroduced("2012-12-31")
        .setDiscontinued("2020-02-29")
        .setCompany("Company 1")
        .setCompanyId(2L)
        .build();
    // @formatter:on

    assertEquals(cd1, cd2);

    Computer c1 = computerMapper.fromDTO(cd1);
    Computer c2 = computerMapper.fromDTO(cd2);

    assertEquals(c1, c2);
  }

  @Test
  public void equalityBetweenCompaniesFromDTO() {
    // @formatter:off
    CompanyDTO cd1 = CompanyDTO.getBuilder("Company 1")
        .setId(2L)
        .build();
    CompanyDTO cd2 = CompanyDTO.getBuilder("Company 1")
        .setId(2L)
        .build();
    // @formatter:on

    assertEquals(cd1, cd2);

    Company c1 = companyMapper.fromDTO(cd1);
    Company c2 = companyMapper.fromDTO(cd2);

    assertEquals(c1, c2);
  }
}

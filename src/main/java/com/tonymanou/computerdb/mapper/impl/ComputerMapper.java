package com.tonymanou.computerdb.mapper.impl;

import java.time.LocalDate;

import com.tonymanou.computerdb.domain.Company;
import com.tonymanou.computerdb.domain.Computer;
import com.tonymanou.computerdb.dto.ComputerDTO;
import com.tonymanou.computerdb.mapper.IEntityMapper;
import com.tonymanou.computerdb.util.Util;

public class ComputerMapper implements IEntityMapper<Computer, ComputerDTO> {

  @Override
  public ComputerDTO mapToDTO(Computer computer) {
    if (computer == null) {
      return null;
    }

    LocalDate introduced = computer.getIntroduced();
    LocalDate discontinued = computer.getDiscontinued();
    Company company = computer.getCompany();
    // @formatter:off
    return ComputerDTO.getBuilder(computer.getName())
        .setId(computer.getId())
        .setIntroduced(introduced == null ? null : introduced.toString())
        .setDiscontinued(discontinued == null ? null : discontinued.toString())
        .setCompany(company == null ? null : company.getName())
        .build();
    // @formatter:on
  }

  @Override
  public Computer mapFromDTO(ComputerDTO computerDTO) {
    if (computerDTO == null) {
      return null;
    }

    String companyName = computerDTO.getCompanyName();
    Company company = companyName == null ? null : Company.getBuilder(companyName).build();

    // @formatter:off
    return Computer.getBuilder(computerDTO.getName())
        .setId(computerDTO.getId())
        .setIntroduced(Util.parseLocalDate(computerDTO.getIntroducedDate()))
        .setDiscontinued(Util.parseLocalDate(computerDTO.getIntroducedDate()))
        .setCompany(company)
        .build();
    // @formatter:on
  }
}

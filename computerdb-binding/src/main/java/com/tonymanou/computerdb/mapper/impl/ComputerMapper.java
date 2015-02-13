package com.tonymanou.computerdb.mapper.impl;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.tonymanou.computerdb.domain.Company;
import com.tonymanou.computerdb.domain.Computer;
import com.tonymanou.computerdb.dto.ComputerDTO;
import com.tonymanou.computerdb.mapper.IEntityMapper;
import com.tonymanou.computerdb.util.Util;

@Component
public class ComputerMapper implements IEntityMapper<Computer, ComputerDTO> {

  @Override
  public ComputerDTO toDTO(Computer computer) {
    if (computer == null) {
      return null;
    }

    LocalDate introduced = computer.getIntroduced();
    LocalDate discontinued = computer.getDiscontinued();
    Company company = computer.getCompany();

    return ComputerDTO
        .getBuilder(computer.getName())
        .setId(computer.getId())
        .setIntroduced(introduced == null ? null : introduced.toString())
        .setDiscontinued(discontinued == null ? null : discontinued.toString())
        .setCompany(company == null ? null : company.getName())
        .setCompanyId(company == null ? null : company.getId())
        .build();
  }

  @Override
  public Computer fromDTO(ComputerDTO computerDTO) {
    if (computerDTO == null) {
      return null;
    }

    Long companyId = computerDTO.getCompanyId();
    String companyName = computerDTO.getCompanyName();
    Company company;
    if (companyId == null && companyName == null) {
      company = null;
    } else {
      company = Company
          .getBuilder(companyName)
          .setId(companyId)
          .build();
    }

    return Computer
        .getBuilder(computerDTO.getName())
        .setId(computerDTO.getId())
        .setIntroduced(Util.parseLocalDate(computerDTO.getIntroducedDate()))
        .setDiscontinued(Util.parseLocalDate(computerDTO.getDiscontinuedDate()))
        .setCompany(company)
        .build();
  }
}

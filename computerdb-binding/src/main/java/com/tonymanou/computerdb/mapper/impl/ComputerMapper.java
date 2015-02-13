package com.tonymanou.computerdb.mapper.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.tonymanou.computerdb.domain.Company;
import com.tonymanou.computerdb.domain.Computer;
import com.tonymanou.computerdb.dto.ComputerDTO;
import com.tonymanou.computerdb.mapper.IEntityMapper;
import com.tonymanou.computerdb.util.Util;

@Component
public class ComputerMapper implements IEntityMapper<Computer, ComputerDTO> {

  private static final String MSG_DATE_FORMAT = "date.format";

  @Resource(name = "formatMessageSource")
  private MessageSource messageSource;

  @Override
  public ComputerDTO toDTO(Computer computer) {
    if (computer == null) {
      return null;
    }

    LocalDate introduced = computer.getIntroduced();
    LocalDate discontinued = computer.getDiscontinued();
    Company company = computer.getCompany();

    String datePattern = messageSource.getMessage(MSG_DATE_FORMAT, null,
        LocaleContextHolder.getLocale());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);

    return ComputerDTO
        .getBuilder(computer.getName())
        .setId(computer.getId())
        .setIntroduced(introduced == null ? null : formatter.format(introduced))
        .setDiscontinued(discontinued == null ? null : formatter.format(discontinued))
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

    String datePattern = messageSource.getMessage(MSG_DATE_FORMAT, null,
        LocaleContextHolder.getLocale());

    return Computer
        .getBuilder(computerDTO.getName())
        .setId(computerDTO.getId())
        .setIntroduced(Util.parseLocalDate(computerDTO.getIntroducedDate(), datePattern))
        .setDiscontinued(Util.parseLocalDate(computerDTO.getDiscontinuedDate(), datePattern))
        .setCompany(company)
        .build();
  }
}

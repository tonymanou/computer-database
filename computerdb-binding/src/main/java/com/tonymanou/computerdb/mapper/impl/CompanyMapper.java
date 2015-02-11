package com.tonymanou.computerdb.mapper.impl;

import org.springframework.stereotype.Component;

import com.tonymanou.computerdb.domain.Company;
import com.tonymanou.computerdb.dto.CompanyDTO;
import com.tonymanou.computerdb.mapper.IEntityMapper;

@Component
public class CompanyMapper implements IEntityMapper<Company, CompanyDTO> {

  @Override
  public CompanyDTO toDTO(Company company) {
    if (company == null) {
      return null;
    }

    // @formatter:off
    return CompanyDTO.getBuilder(company.getName())
        .setId(company.getId())
        .build();
    // @formatter:on
  }

  @Override
  public Company fromDTO(CompanyDTO companyDTO) {
    if (companyDTO == null) {
      return null;
    }

    // @formatter:off
    return Company.getBuilder(companyDTO.getName())
        .setId(companyDTO.getId())
        .build();
    // @formatter:on
  }
}

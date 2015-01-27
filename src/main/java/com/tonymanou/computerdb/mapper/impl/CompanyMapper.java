package com.tonymanou.computerdb.mapper.impl;

import com.tonymanou.computerdb.domain.Company;
import com.tonymanou.computerdb.dto.CompanyDTO;
import com.tonymanou.computerdb.mapper.IEntityMapper;

public class CompanyMapper implements IEntityMapper<Company, CompanyDTO> {

  @Override
  public CompanyDTO mapToDTO(Company company) {
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
  public Company mapFromDTO(CompanyDTO companyDTO) {
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

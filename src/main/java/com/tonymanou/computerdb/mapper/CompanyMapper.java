package com.tonymanou.computerdb.mapper;

import com.tonymanou.computerdb.dto.CompanyDTO;
import com.tonymanou.computerdb.entity.Company;

public class CompanyMapper {

  public static CompanyDTO mapToDTO(Company company) {
    // @formatter:off
    return CompanyDTO.getBuilder(company.getName())
        .setId(company.getId())
        .build();
    // @formatter:on
  }

  public static Company mapToCompany(CompanyDTO companyDTO) {
    // @formatter:off
    return Company.getBuilder(companyDTO.getName())
        .setId(companyDTO.getId())
        .build();
    // @formatter:on
  }
}

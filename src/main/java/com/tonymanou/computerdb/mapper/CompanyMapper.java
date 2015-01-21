package com.tonymanou.computerdb.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.tonymanou.computerdb.dto.CompanyDTO;
import com.tonymanou.computerdb.entity.Company;

public class CompanyMapper {

  public static List<CompanyDTO> mapToDTOList(List<Company> list) {
    if (list == null) {
      return null;
    }
    // @formatter:off
    return list.stream()
        .map(e -> mapToDTO(e))
        .collect(Collectors.toList());
    // @formatter:on
  }

  public static List<Company> mapToList(List<CompanyDTO> list) {
    if (list == null) {
      return null;
    }
    // @formatter:off
    return list.stream()
        .map(e -> mapToCompany(e))
        .collect(Collectors.toList());
    // @formatter:on
  }

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

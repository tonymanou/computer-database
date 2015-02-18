package com.tonymanou.computerdb.restservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tonymanou.computerdb.dto.CompanyDTO;
import com.tonymanou.computerdb.mapper.IEntityMapper;
import com.tonymanou.computerdb.model.Company;
import com.tonymanou.computerdb.service.ICompanyService;

@RestController
@RequestMapping("/company")
public class CompanyWebservice {

  @Autowired
  private ICompanyService companyService;
  @Autowired
  private IEntityMapper<Company, CompanyDTO> companyMapper;

  @RequestMapping(method = RequestMethod.GET)
  public List<CompanyDTO> getAll() {
    List<Company> list = companyService.findAll();
    return companyMapper.toDTOList(list);
  }
}

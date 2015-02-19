package com.tonymanou.computerdb.webservice.impl;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tonymanou.computerdb.dto.CompanyDTO;
import com.tonymanou.computerdb.mapper.IEntityMapper;
import com.tonymanou.computerdb.model.Company;
import com.tonymanou.computerdb.service.ICompanyService;
import com.tonymanou.computerdb.webservice.ICompanyWS;
import com.tonymanou.computerdb.webservice.wrapper.ListWrapper;

@Service
@WebService(endpointInterface = "com.tonymanou.computerdb.webservice.ICompanyWS")
public class CompanyWS implements ICompanyWS {

  @Autowired
  private ICompanyService companyService;
  @Autowired
  private IEntityMapper<Company, CompanyDTO> companyMapper;

  @Override
  public ListWrapper<CompanyDTO> findAll() {
    ListWrapper<CompanyDTO> wrapper = new ListWrapper<>();
    List<Company> list = companyService.findAll();
    wrapper.setList(companyMapper.toDTOList(list));
    return wrapper;
  }

  @Override
  public CompanyDTO getFromId(Long id) {
    Company company = companyService.getFromId(id);
    if (company == null) {
      return CompanyDTO.getBuilder(null).build();
    } else {
      return companyMapper.toDTO(company);
    }
  }

  @Override
  public void delete(Long id) {
    companyService.delete(id);
  }
}

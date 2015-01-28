package com.tonymanou.computerdb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.tonymanou.computerdb.dao.ICompanyDAO;
import com.tonymanou.computerdb.dao.IComputerDAO;
import com.tonymanou.computerdb.dao.IConnectionManager;
import com.tonymanou.computerdb.dao.impl.SQLCompanyDAO;
import com.tonymanou.computerdb.dao.impl.SQLComputerDAO;
import com.tonymanou.computerdb.dao.impl.SQLConnectionManager;
import com.tonymanou.computerdb.domain.Company;
import com.tonymanou.computerdb.domain.Computer;
import com.tonymanou.computerdb.dto.CompanyDTO;
import com.tonymanou.computerdb.dto.ComputerDTO;
import com.tonymanou.computerdb.mapper.IEntityMapper;
import com.tonymanou.computerdb.mapper.impl.CompanyMapper;
import com.tonymanou.computerdb.mapper.impl.ComputerMapper;
import com.tonymanou.computerdb.service.ICompanyService;
import com.tonymanou.computerdb.service.IComputerService;
import com.tonymanou.computerdb.service.impl.CompanyService;
import com.tonymanou.computerdb.service.impl.ComputerService;
import com.tonymanou.computerdb.validator.IEntityValidator;
import com.tonymanou.computerdb.validator.impl.ComputerDTOValidator;

@Configuration
@ComponentScan(basePackages = { "com.tonymanou.computerdb.dao",
    "com.tonymanou.computerdb.mapper", "com.tonymanou.computerdb.service",
    "com.tonymanou.computerdb.validator" })
public class Application {

  @Bean
  public IComputerDAO computerDAO() {
    return new SQLComputerDAO();
  }

  @Bean
  public ICompanyDAO companyDAO() {
    return new SQLCompanyDAO();
  }

  @Bean
  public IConnectionManager connectionManager() {
    return new SQLConnectionManager();
  }

  @Bean
  public IEntityMapper<Company, CompanyDTO> companyMapper() {
    return new CompanyMapper();
  }

  @Bean
  public IEntityMapper<Computer, ComputerDTO> computerMapper() {
    return new ComputerMapper();
  }

  @Bean
  public ICompanyService companyService() {
    return new CompanyService();
  }

  @Bean
  public IComputerService computerService() {
    return new ComputerService();
  }

  @Bean
  public IEntityValidator<ComputerDTO> computerDTOValidator() {
    return new ComputerDTOValidator();
  }
}

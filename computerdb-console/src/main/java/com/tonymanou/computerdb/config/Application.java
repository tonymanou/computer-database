package com.tonymanou.computerdb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.tonymanou.computerdb.dao.ICompanyDAO;
import com.tonymanou.computerdb.dao.IComputerDAO;
import com.tonymanou.computerdb.dao.impl.SQLCompanyDAO;
import com.tonymanou.computerdb.dao.impl.SQLComputerDAO;
import com.tonymanou.computerdb.service.ICompanyService;
import com.tonymanou.computerdb.service.IComputerService;
import com.tonymanou.computerdb.service.impl.CompanyService;
import com.tonymanou.computerdb.service.impl.ComputerService;

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
  public ICompanyService companyService() {
    return new CompanyService();
  }

  @Bean
  public IComputerService computerService() {
    return new ComputerService();
  }
}

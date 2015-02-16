package com.tonymanou.computerdb.mapper;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.tonymanou.computerdb.dto.CompanyDTO;
import com.tonymanou.computerdb.dto.ComputerDTO;
import com.tonymanou.computerdb.mapper.impl.CompanyMapper;
import com.tonymanou.computerdb.mapper.impl.ComputerMapper;
import com.tonymanou.computerdb.model.Company;
import com.tonymanou.computerdb.model.Computer;

public class MapperTest {

  private static AnnotationConfigApplicationContext context;
  private static IEntityMapper<Computer, ComputerDTO> computerMapper;
  private static IEntityMapper<Company, CompanyDTO> companyMapper;

  /**
   * Spring context configuration class.
   */
  @Configuration
  public static class MapperTestConfig {
    @Bean
    public IEntityMapper<Computer, ComputerDTO> computerMapper() {
      return new ComputerMapper();
    }

    @Bean
    public IEntityMapper<Company, CompanyDTO> companyMapper() {
      return new CompanyMapper();
    }

    @Bean
    public MessageSource formatMessageSource() {
      ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
      messageSource.setBasename("formatmessages");
      messageSource.setDefaultEncoding("UTF-8");
      LocaleContextHolder.setLocale(Locale.ENGLISH);
      return messageSource;
    }
  }

  @BeforeClass
  public static void setUp() {
    context = new AnnotationConfigApplicationContext(MapperTestConfig.class);
    computerMapper = context.getBean(ComputerMapper.class);
    companyMapper = context.getBean(CompanyMapper.class);
  }

  @Test
  public void equalityBetweenComputersFromDTO() {
    ComputerDTO cd1 = ComputerDTO.getBuilder("Computer 1")
        .setId(10L)
        .setIntroduced("2012-12-31")
        .setDiscontinued("2020-02-29")
        .setCompany("Company 1")
        .setCompanyId(2L)
        .build();
    ComputerDTO cd2 = ComputerDTO.getBuilder("Computer 1")
        .setId(10L)
        .setIntroduced("2012-12-31")
        .setDiscontinued("2020-02-29")
        .setCompany("Company 1")
        .setCompanyId(2L)
        .build();

    assertEquals(cd1, cd2);

    Computer c1 = computerMapper.fromDTO(cd1);
    Computer c2 = computerMapper.fromDTO(cd2);

    assertEquals(c1, c2);
  }

  @Test
  public void equalityBetweenCompaniesFromDTO() {
    CompanyDTO cd1 = CompanyDTO.getBuilder("Company 1")
        .setId(2L)
        .build();
    CompanyDTO cd2 = CompanyDTO.getBuilder("Company 1")
        .setId(2L)
        .build();

    assertEquals(cd1, cd2);

    Company c1 = companyMapper.fromDTO(cd1);
    Company c2 = companyMapper.fromDTO(cd2);

    assertEquals(c1, c2);
  }
}

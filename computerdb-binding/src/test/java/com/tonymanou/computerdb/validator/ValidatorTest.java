package com.tonymanou.computerdb.validator;

import java.util.Locale;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.AbstractBindingResult;
import org.springframework.validation.Errors;

import com.tonymanou.computerdb.dto.ComputerDTO;

public class ValidatorTest {

  private static AnnotationConfigApplicationContext context;
  private static ComputerDTOValidator computerValidator;

  /**
   * Spring context configuration class.
   */
  @Configuration
  public static class ValidatorTestConfig {
    @Bean
    public ComputerDTOValidator computerDTOValidator() {
      return new ComputerDTOValidator();
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
    context = new AnnotationConfigApplicationContext(ValidatorTestConfig.class);
    computerValidator = context.getBean(ComputerDTOValidator.class);
  }

  @Test
  public void checkValidComputerDTO1() {
    ComputerDTO target = ComputerDTO.getBuilder("Computer 1")
        .setId(10L)
        .setIntroduced("2012-12-31")
        .setDiscontinued("2020-02-29")
        .setCompany("Company 1")
        .setCompanyId(2L)
        .build();

    Errors errorMap = new EmptyBindingResult("testObject");
    computerValidator.validate(target, errorMap);

    Assert.assertFalse(errorMap.toString(), errorMap.hasErrors());
  }

  @Test
  public void checkValidComputerDTO1Fr() {
    ComputerDTO target = ComputerDTO.getBuilder("Computer 1")
        .setId(10L)
        .setIntroduced("31/12/2012")
        .setDiscontinued("29/02/2020")
        .setCompany("Company 1")
        .setCompanyId(2L)
        .build();

    LocaleContextHolder.setLocale(Locale.FRENCH);

    Errors errorMap = new EmptyBindingResult("testObject");
    computerValidator.validate(target, errorMap);

    LocaleContextHolder.setLocale(Locale.ENGLISH);

    Assert.assertFalse(errorMap.toString(), errorMap.hasErrors());
  }

  @Test
  public void checkValidComputerDTO2() {
    ComputerDTO target = ComputerDTO.getBuilder("Computer 1")
        .setId(10L)
        .setIntroduced(null)
        .setDiscontinued(null)
        .setCompany(null)
        .setCompanyId(null)
        .build();

    Errors errorMap = new EmptyBindingResult("testObject");
    computerValidator.validate(target, errorMap);

    Assert.assertFalse(errorMap.toString(), errorMap.hasErrors());
  }

  @Test
  public void checkInvalidComputerDTOName() {
    ComputerDTO target = ComputerDTO.getBuilder(null)
        .setId(10L)
        .setIntroduced("2012-12-31")
        .setDiscontinued("2020-02-29")
        .setCompany("Company 1")
        .setCompanyId(2L)
        .build();

    Errors errorMap = new EmptyBindingResult("testObject");
    computerValidator.validate(target, errorMap);

    Assert.assertTrue(errorMap.hasErrors());
  }

  @Test
  public void checkInvalidComputerDTOCompanyId() {
    ComputerDTO target = ComputerDTO.getBuilder("Computer 1")
        .setId(10L)
        .setIntroduced("2012-12-31")
        .setDiscontinued("2020-02-29")
        .setCompany("Company 1")
        .setCompanyId(-2L)
        .build();

    Errors errorMap = new EmptyBindingResult("testObject");
    computerValidator.validate(target, errorMap);

    Assert.assertTrue(errorMap.hasErrors());
  }

  @Test
  public void checkInvalidNotComputerDTO() {
    Errors errorMap = new EmptyBindingResult("testObject");
    computerValidator.validate(new Integer(1), errorMap);

    Assert.assertTrue(errorMap.hasErrors());
  }

  @Test
  public void checkInvalidNullComputerDTO() {
    Errors errorMap = new EmptyBindingResult("testObject");
    computerValidator.validate(null, errorMap);

    Assert.assertTrue(errorMap.hasErrors());
  }

  private class EmptyBindingResult extends AbstractBindingResult {

    private static final long serialVersionUID = 2323940359120177081L;

    protected EmptyBindingResult(String objectName) {
      super(objectName);
    }

    @Override
    public Object getTarget() {
      return null;
    }

    @Override
    protected Object getActualFieldValue(String field) {
      return null;
    }
  }
}

package com.tonymanou.computerdb.validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.AbstractBindingResult;
import org.springframework.validation.Errors;

import com.tonymanou.computerdb.dto.ComputerDTO;

public class ValidatorTest {

  private ComputerDTOValidator computerValidator;

  @Before
  public void initTest() {
    computerValidator = new ComputerDTOValidator();
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

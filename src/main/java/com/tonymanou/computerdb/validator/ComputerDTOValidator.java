package com.tonymanou.computerdb.validator;

import java.time.LocalDate;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.tonymanou.computerdb.dto.ComputerDTO;
import com.tonymanou.computerdb.util.Util;

@Component
public class ComputerDTOValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return ComputerDTO.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    if (target == null) {
      errors.reject("null-object");
      return;
    }
    if (!(target instanceof ComputerDTO)) {
      errors.reject("wrong-type");
      return;
    }

    ComputerDTO entity = (ComputerDTO) target;

    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required.computer-name");

    if (entity.getId() == null) {
      errors.rejectValue("id", "required.computer-id");
    }

    String introducedDate = entity.getIntroducedDate();
    if (!Util.isStringEmpty(introducedDate)) {
      LocalDate introduced = Util.parseLocalDate(introducedDate);
      if (introduced == null) {
        errors.rejectValue("introducedDate", "format.date-introduced");
      }
    }

    String discontinuedDate = entity.getDiscontinuedDate();
    if (!Util.isStringEmpty(discontinuedDate)) {
      LocalDate discontinued = Util.parseLocalDate(discontinuedDate);
      if (discontinued == null) {
        errors.rejectValue("discontinuedDate", "format.date-discontinued");
      }
    }

    Long companyId = entity.getCompanyId();
    if (companyId != null && companyId <= 0) {
      errors.rejectValue("companyId", "format.company-id");
    }
  }
}

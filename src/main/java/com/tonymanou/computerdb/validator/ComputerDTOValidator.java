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

  private static final String ERROR_MSG_NULL = "error.null-object";
  private static final String ERROR_MSG_WRONG = "error.wrong-type";

  private static final String ERROR_ID_NAME = "name";
  private static final String ERROR_MSG_NAME = "required.computer-name";

  private static final String ERROR_ID_ID = "id";
  private static final String ERROR_MSG_ID = "required.computer-id";

  private static final String ERROR_ID_IDATE = "introducedDate";
  private static final String ERROR_MSG_IDATE = "format.date-introduced";

  private static final String ERROR_ID_DDATE = "discontinuedDate";
  private static final String ERROR_MSG_DDATE = "format.date-discontinued";

  private static final String ERROR_ID_COMPANY = "companyId";
  private static final String ERROR_MSG_COMPANY = "format.company-id";

  @Override
  public boolean supports(Class<?> clazz) {
    return ComputerDTO.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    if (target == null) {
      errors.reject(ERROR_MSG_NULL);
      return;
    }
    if (!(target instanceof ComputerDTO)) {
      errors.reject(ERROR_MSG_WRONG);
      return;
    }

    ComputerDTO entity = (ComputerDTO) target;

    ValidationUtils.rejectIfEmptyOrWhitespace(errors, ERROR_ID_NAME, ERROR_MSG_NAME);

    if (entity.getId() == null) {
      errors.rejectValue(ERROR_ID_ID, ERROR_MSG_ID);
    }

    String introducedDate = entity.getIntroducedDate();
    if (!Util.isStringEmpty(introducedDate)) {
      LocalDate introduced = Util.parseLocalDate(introducedDate);
      if (introduced == null) {
        errors.rejectValue(ERROR_ID_IDATE, ERROR_MSG_IDATE);
      }
    }

    String discontinuedDate = entity.getDiscontinuedDate();
    if (!Util.isStringEmpty(discontinuedDate)) {
      LocalDate discontinued = Util.parseLocalDate(discontinuedDate);
      if (discontinued == null) {
        errors.rejectValue(ERROR_ID_DDATE, ERROR_MSG_DDATE);
      }
    }

    Long companyId = entity.getCompanyId();
    if (companyId != null && companyId <= 0) {
      errors.rejectValue(ERROR_ID_COMPANY, ERROR_MSG_COMPANY);
    }
  }
}

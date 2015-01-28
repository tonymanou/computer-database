package com.tonymanou.computerdb.validator.impl;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.tonymanou.computerdb.dto.ComputerDTO;
import com.tonymanou.computerdb.util.Util;
import com.tonymanou.computerdb.validator.IEntityValidator;

@Component
public class ComputerDTOValidator implements IEntityValidator<ComputerDTO> {

  @Override
  public boolean validate(ComputerDTO entity, Map<String, String> errors) {
    boolean valid = true;

    String name = entity.getName();
    if (Util.isStringEmpty(name)) {
      valid = false;
      errors.put("computerName", "You must enter a computer name.");
    }

    String introducedDate = entity.getIntroducedDate();
    if (!Util.isStringEmpty(introducedDate)) {
      LocalDate introduced = Util.parseLocalDate(introducedDate);
      if (introduced == null) {
        valid = false;
        errors.put("introduced", "You must enter a valid introduced date (yyyy-MM-dd).");
      }
    }

    String discontinuedDate = entity.getDiscontinuedDate();
    if (!Util.isStringEmpty(discontinuedDate)) {
      LocalDate discontinued = Util.parseLocalDate(discontinuedDate);
      if (discontinued == null) {
        valid = false;
        errors.put("discontinued", "You must enter a valid discontinued date (yyyy-MM-dd).");
      }
    }

    Long companyId = entity.getCompanyId();
    if (companyId == null || companyId < 0) {
      valid = false;
      errors.put("companyId", "You must choose a valid company.");
    }

    return valid;
  }
}

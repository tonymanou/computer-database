package com.tonymanou.computerdb.validator.impl;

import java.time.LocalDate;
import java.util.Map;

import com.tonymanou.computerdb.dto.ComputerDTO;
import com.tonymanou.computerdb.util.Util;
import com.tonymanou.computerdb.validator.IEntityValidator;

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
    LocalDate introduced;
    if (Util.isStringEmpty(introducedDate)) {
      introduced = null;
    } else {
      introduced = Util.parseLocalDate(introducedDate);
      if (introduced == null) {
        errors.put("introduced", "You must enter a valid introduced date (yyyy-MM-dd).");
      }
    }

    String discontinuedDate = entity.getDiscontinuedDate();
    LocalDate discontinued;
    if (Util.isStringEmpty(discontinuedDate)) {
      discontinued = null;
    } else {
      discontinued = Util.parseLocalDate(discontinuedDate);
      if (discontinued == null) {
        errors.put("discontinued", "You must enter a valid discontinued date (yyyy-MM-dd).");
      }
    }

    Long companyId = entity.getCompanyId();
    if (companyId == null || companyId < 0) {
      errors.put("companyId", "You must choose a valid company.");
    }

    return valid;
  }
}

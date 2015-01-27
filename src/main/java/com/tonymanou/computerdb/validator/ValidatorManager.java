package com.tonymanou.computerdb.validator;

import com.tonymanou.computerdb.dto.ComputerDTO;
import com.tonymanou.computerdb.validator.impl.ComputerDTOValidator;

public enum ValidatorManager {

  INSTANCE;

  private IEntityValidator<ComputerDTO> computerDTOValidator;

  private ValidatorManager() {
    computerDTOValidator = new ComputerDTOValidator();
  }

  public IEntityValidator<ComputerDTO> getComputerDTOValidator() {
    return computerDTOValidator;
  }
}

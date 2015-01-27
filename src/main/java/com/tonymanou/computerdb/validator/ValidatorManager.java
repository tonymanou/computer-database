package com.tonymanou.computerdb.validator;

import org.springframework.beans.factory.annotation.Autowired;

import com.tonymanou.computerdb.dto.ComputerDTO;

public class ValidatorManager {

  @Autowired
  private IEntityValidator<ComputerDTO> computerDTOValidator;

  public IEntityValidator<ComputerDTO> getComputerDTOValidator() {
    return computerDTOValidator;
  }
}

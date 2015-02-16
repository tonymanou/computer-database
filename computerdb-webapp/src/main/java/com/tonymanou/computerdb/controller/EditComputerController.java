package com.tonymanou.computerdb.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tonymanou.computerdb.dto.CompanyDTO;
import com.tonymanou.computerdb.dto.ComputerDTO;
import com.tonymanou.computerdb.mapper.IEntityMapper;
import com.tonymanou.computerdb.model.Company;
import com.tonymanou.computerdb.model.Computer;
import com.tonymanou.computerdb.service.ICompanyService;
import com.tonymanou.computerdb.service.IComputerService;

@Controller
public class EditComputerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(EditComputerController.class);

  @Autowired
  private ICompanyService companyService;
  @Autowired
  private IComputerService computerService;
  @Autowired
  private IEntityMapper<Company, CompanyDTO> companyMapper;
  @Autowired
  private IEntityMapper<Computer, ComputerDTO> computerMapper;
  @Autowired
  @Qualifier("computerDTOValidator")
  private Validator computerValidator;

  @InitBinder
  private void initBinder(WebDataBinder binder) {
    binder.setValidator(computerValidator);
  }

  @RequestMapping(value = "/computer/edit/{id}", method = RequestMethod.GET)
  protected String editComputerGet(ComputerDTO computerDTO, Model model) {
    Computer computer = computerService.getFromId(computerDTO.getId());
    ComputerDTO dto = computerMapper.toDTO(computer);
    computerDTO.copy(dto);

    List<CompanyDTO> companies = companyMapper.toDTOList(companyService.findAll());
    model.addAttribute("companies", companies);
    return "editComputer";
  }

  @RequestMapping(value = "/computer/edit/{id}", method = RequestMethod.POST)
  protected String editComputerPost(Model model, @Validated ComputerDTO computerDTO,
      BindingResult result) {
    if (!result.hasErrors()) {
      Computer computer = computerMapper.fromDTO(computerDTO);
      try {
        computerService.update(computer);
      } catch (Exception e) {
        LOGGER.error("Unable to save the computer", e);
        result.reject("error.save-computer");
      }

      if (!result.hasErrors()) {
        return "redirect:/dashboard";
      }
    }

    List<CompanyDTO> companies = companyMapper.toDTOList(companyService.findAll());
    model.addAttribute("companies", companies);
    return "editComputer";
  }
}

package com.tonymanou.computerdb.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tonymanou.computerdb.domain.Company;
import com.tonymanou.computerdb.domain.Computer;
import com.tonymanou.computerdb.dto.CompanyDTO;
import com.tonymanou.computerdb.dto.ComputerDTO;
import com.tonymanou.computerdb.mapper.IEntityMapper;
import com.tonymanou.computerdb.service.ICompanyService;
import com.tonymanou.computerdb.service.IComputerService;
import com.tonymanou.computerdb.util.Util;

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

  @RequestMapping(value = "/computer/edit/{id}", method = RequestMethod.GET)
  protected String editComputerGet(ComputerDTO computerDTO) {
    // Long computerId = Util.parsePositiveLong(id);
    // ComputerDTO computerDTO;
    System.out.println(computerDTO);

    Computer computer = computerService.getFromId(computerDTO.getId());
    computerMapper.updateDTO(computerDTO, computer);

    // return showEditComputerForm(model, computerDTO, null);
    return "editComputer";
  }

  @RequestMapping(value = "/computer/edit/{id}", method = RequestMethod.POST)
  protected String editComputerPost(@Valid ComputerDTO computerDTO,
      BindingResult result) {
    System.out.println(result);
    if (result.hasErrors()) {
      System.out.println("Invalid, bitch... " + computerDTO);
//      return showEditComputerForm(model, computerDTO, null);
      return "editComputer";
    } else {
      System.out.println("apporved: " + computerDTO);

//      Computer computer = computerMapper.fromDTO(computerDTO);
//      try {
//        computerService.update(computer);
//      } catch (Exception e) {
//        LOGGER.error("Unable to save the computer", e);
//        result.reject("error.save-computer");
//      }

      // if (errors.isEmpty()) {
      // return "redirect:/dashboard";
      // } else {
//      return showEditComputerForm(model, computerDTO, null);
      return "editComputer";
      // }
    }
  }

  private String showEditComputerForm(Model model, ComputerDTO computer, Map<String, String> errors) {
    List<CompanyDTO> companies = companyMapper.toDTOList(companyService.findAll());
    model.addAttribute("computer", computer);
    model.addAttribute("companies", companies);
    return "editComputer";
  }
}

package com.tonymanou.computerdb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.tonymanou.computerdb.validator.IEntityValidator;

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
  private IEntityValidator<ComputerDTO> computerDTOValidator;

  @RequestMapping(value = "/computer/edit/{id}", method = RequestMethod.GET)
  protected String editComputerGet(@PathVariable String id, Model model) {
    Long computerId = Util.parsePositiveLong(id);
    ComputerDTO computerDTO;
    if (computerId == null) {
      computerDTO = null;
    } else {
      Computer computer = computerService.getFromId(computerId);
      computerDTO = computerMapper.toDTO(computer);
    }

    return showEditComputerForm(model, computerDTO, null);
  }

  @RequestMapping(value = "/computer/edit/{id}", method = RequestMethod.POST)
  protected String editComputerPost(@PathVariable String id, HttpServletRequest req, Model model) {
    Map<String, String> errors = new HashMap<>();

    Long computerId = Util.parsePositiveLong(id);
    if (computerId == null || computerService.getFromId(computerId) == null) {
      return showEditComputerForm(model, null, errors);
    }

    // @formatter:off
    ComputerDTO computerDTO = ComputerDTO
        .getBuilder(req.getParameter("computerName"))
        .setId(computerId)
        .setIntroduced(req.getParameter("introduced"))
        .setDiscontinued(req.getParameter("discontinued"))
        .setCompanyId(Util.parsePositiveLong(req.getParameter("companyId")))
        .build();
    // @formatter:on

    if (computerDTOValidator.validate(computerDTO, errors)) {
      Computer computer = computerMapper.fromDTO(computerDTO);
      try {
        computerService.update(computer);
      } catch (Exception e) {
        LOGGER.error("Unable to save the computer", e);
        errors.put("bug", "Internal error: unable to save the computer.");
      }
    }

    if (errors.isEmpty()) {
      return "redirect:/dashboard";
    } else {
      return showEditComputerForm(model, computerDTO, errors);
    }
  }

  private String showEditComputerForm(Model model, ComputerDTO computer, Map<String, String> errors) {
    List<CompanyDTO> companies = companyMapper.toDTOList(companyService.findAll());
    model.addAttribute("computer", computer);
    model.addAttribute("companies", companies);
    model.addAttribute("errors", errors);
    return "editComputer";
  }
}

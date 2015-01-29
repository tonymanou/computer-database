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
public class AddComputerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(AddComputerController.class);

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

  @RequestMapping(value = "/computer/add", method = RequestMethod.GET)
  protected String addComputerGet(Model model) {
    List<CompanyDTO> companies = companyMapper.toDTOList(companyService.findAll());
    model.addAttribute("companies", companies);
    return "addComputer";
  }

  @RequestMapping(value = "/computer/add", method = RequestMethod.POST)
  protected String addComputerPost(HttpServletRequest req, Model model) {
    Map<String, String> errors = new HashMap<>();

    Long companyId = Util.parsePositiveLong(req.getParameter("companyId"));
    if (companyId == 0) {
      companyId = null;
    }

    // @formatter:off
    ComputerDTO computerDTO = ComputerDTO
        .getBuilder(req.getParameter("computerName"))
        .setIntroduced(req.getParameter("introduced"))
        .setDiscontinued(req.getParameter("discontinued"))
        .setCompanyId(companyId)
        .build();
    // @formatter:on

    if (computerDTOValidator.validate(computerDTO, errors)) {
      try {
        Computer computer = computerMapper.fromDTO(computerDTO);
        computerService.create(computer);
      } catch (Exception e) {
        LOGGER.error("Unable to save the computer", e);
        errors.put("bug", "Unable to save the computer.");
      }
    }

    if (errors.isEmpty()) {
      return "redirect:/dashboard";
    } else {
      List<CompanyDTO> companies = companyMapper.toDTOList(companyService.findAll());
      model.addAttribute("computer", computerDTO);
      model.addAttribute("companies", companies);
      model.addAttribute("errors", errors);
      return "addComputer";
    }
  }
}

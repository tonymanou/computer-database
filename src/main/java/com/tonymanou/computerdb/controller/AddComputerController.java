package com.tonymanou.computerdb.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tonymanou.computerdb.domain.Company;
import com.tonymanou.computerdb.domain.Computer;
import com.tonymanou.computerdb.dto.CompanyDTO;
import com.tonymanou.computerdb.dto.ComputerDTO;
import com.tonymanou.computerdb.exception.PersistenceException;
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

  @RequestMapping(value="/computer/add", method=RequestMethod.GET)
  protected void addComputerGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {
    showAddComputerForm(req, resp, null, null);
  }

  @RequestMapping(value="/computer/add", method=RequestMethod.POST)
  protected void addComputerPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {
    Map<String, String> errors = new HashMap<>();

    // @formatter:off
    ComputerDTO computerDTO = ComputerDTO
        .getBuilder(req.getParameter("computerName"))
        .setIntroduced(req.getParameter("introduced"))
        .setDiscontinued(req.getParameter("discontinued"))
        .setCompanyId(Util.parsePositiveLong(req.getParameter("companyId")))
        .build();
    // @formatter:on

    if (computerDTOValidator.validate(computerDTO, errors)) {
      try {
        Computer computer = computerMapper.fromDTO(computerDTO);
        computerService.create(computer);
      } catch (PersistenceException e) {
        LOGGER.error("Unable to save the computer", e);
        errors.put("bug", "Unable to save the computer.");
      }
    }

    if (errors.isEmpty()) {
      resp.sendRedirect("../dashboard");
    } else {
      showAddComputerForm(req, resp, computerDTO, errors);
    }
  }

  private void showAddComputerForm(HttpServletRequest req, HttpServletResponse resp,
      ComputerDTO computer, Map<String, String> errors) throws ServletException, IOException {
    List<CompanyDTO> companies = companyMapper.toDTOList(companyService.findAll());
    req.setAttribute("computer", computer);
    req.setAttribute("companies", companies);
    req.setAttribute("errors", errors);
    req.getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(req, resp);
  }
}

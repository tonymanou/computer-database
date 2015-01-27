package com.tonymanou.computerdb.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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

@WebServlet("/computer/edit")
public class EditComputerController extends BaseSpringServlet {

  private static final long serialVersionUID = 1704952071605732498L;
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

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {
    Long computerId = Util.parsePositiveLong(req.getParameter("id"));
    ComputerDTO computerDTO;
    if (computerId == null) {
      computerDTO = null;
    } else {
      Computer computer = computerService.getFromId(computerId);
      computerDTO = computerMapper.mapToDTO(computer);
    }

    showEditComputerForm(req, resp, computerDTO, null);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {
    Map<String, String> errors = new HashMap<>();

    Long computerId = Util.parsePositiveLong(req.getParameter("computerId"));
    if (computerId == null || computerService.getFromId(computerId) == null) {
      showEditComputerForm(req, resp, null, errors);
      return;
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
      Computer computer = computerMapper.mapFromDTO(computerDTO);
      try {
        computerService.update(computer);
      } catch (PersistenceException e) {
        LOGGER.error("Unable to save the computer", e);
        errors.put("bug", "Internal error: unable to save the computer.");
      }
    }

    if (errors.isEmpty()) {
      resp.sendRedirect("../dashboard");
    } else {
      showEditComputerForm(req, resp, computerDTO, errors);
    }
  }

  private void showEditComputerForm(HttpServletRequest req, HttpServletResponse resp,
      ComputerDTO computer, Map<String, String> errors) throws ServletException, IOException {
    List<CompanyDTO> companies = companyMapper.mapToDTOList(companyService.findAll());
    req.setAttribute("computer", computer);
    req.setAttribute("companies", companies);
    req.setAttribute("errors", errors);
    req.getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(req, resp);
  }
}

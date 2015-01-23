package com.tonymanou.computerdb.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tonymanou.computerdb.domain.Company;
import com.tonymanou.computerdb.domain.Computer;
import com.tonymanou.computerdb.dto.CompanyDTO;
import com.tonymanou.computerdb.exception.PersistenceException;
import com.tonymanou.computerdb.mapper.IEntityMapper;
import com.tonymanou.computerdb.mapper.MapperManager;
import com.tonymanou.computerdb.service.ICompanyService;
import com.tonymanou.computerdb.service.IComputerService;
import com.tonymanou.computerdb.service.ServiceManager;
import com.tonymanou.computerdb.util.Util;

@WebServlet("/computer/add")
public class AddComputerController extends HttpServlet {

  private static final long serialVersionUID = -4711445570099851743L;
  private static final Logger LOGGER = LoggerFactory.getLogger(AddComputerController.class);

  private ICompanyService companyService;
  private IComputerService computerService;
  private IEntityMapper<Company, CompanyDTO> companyMapper;

  public AddComputerController() {
    companyService = ServiceManager.INSTANCE.getCompanyService();
    computerService = ServiceManager.INSTANCE.getComputerService();
    companyMapper = MapperManager.INSTANCE.getCompanyMapper();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {
    showAddComputerForm(req, resp, null, null);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {
    List<String> errors = new ArrayList<>();

    String name = req.getParameter("computerName");
    if (Util.isStringEmpty(name)) {
      errors.add("You must enter a computer name.");
    }

    String introducedDate = req.getParameter("introduced");
    LocalDate introduced;
    if (Util.isStringEmpty(introducedDate)) {
      introduced = null;
    } else {
      introduced = Util.parseLocalDate(introducedDate);
      if (introduced == null) {
        errors.add("You must enter a valid introduced date (yyyy-MM-dd).");
      }
    }

    String discontinuedDate = req.getParameter("discontinued");
    LocalDate discontinued;
    if (Util.isStringEmpty(discontinuedDate)) {
      discontinued = null;
    } else {
      discontinued = Util.parseLocalDate(discontinuedDate);
      if (discontinued == null) {
        errors.add("You must enter a valid discontinued date (yyyy-MM-dd).");
      }
    }

    String companyId = req.getParameter("companyId");
    Company company = null;
    if (!Util.isStringEmpty(companyId)) {
      Long id = Util.parseLong(companyId);
      if (id == null) {
        errors.add("You must choose a valid company.");
      } else if (id != 0) {
        company = companyService.getFromId(id);
        if (company == null) {
          // Company not found
          errors.add("You must choose a valid company.");
        }
      }
    }

    // @formatter:off
    Computer computer = Computer.getBuilder(name)
        .setIntroduced(introduced)
        .setDiscontinued(discontinued)
        .setCompany(company)
        .build();
    // @formatter:on

    if (errors.isEmpty()) {
      try {
        computerService.create(computer);
      } catch (PersistenceException e) {
        LOGGER.error("Unable to save the computer", e);
        errors.add("Internal error: unable to save the computer.");
      }
    }

    if (errors.isEmpty()) {
      resp.sendRedirect("../dashboard");
    } else {
      showAddComputerForm(req, resp, computer, errors);
    }
  }

  private void showAddComputerForm(HttpServletRequest req, HttpServletResponse resp,
      Computer computer, List<String> errors) throws ServletException, IOException {
    List<CompanyDTO> companies = companyMapper.mapToDTOList(companyService.findAll());
    req.setAttribute("computer", computer);
    req.setAttribute("companies", companies);
    req.setAttribute("errors", errors);
    req.getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(req, resp);
  }
}

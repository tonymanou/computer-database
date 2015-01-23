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

@WebServlet("/computer/edit")
public class EditComputerController extends HttpServlet {

  private static final long serialVersionUID = 1704952071605732498L;
  private static final Logger LOGGER = LoggerFactory.getLogger(EditComputerController.class);

  private ICompanyService companyService;
  private IComputerService computerService;
  private IEntityMapper<Company, CompanyDTO> companyMapper;

  public EditComputerController() {
    companyService = ServiceManager.INSTANCE.getCompanyService();
    computerService = ServiceManager.INSTANCE.getComputerService();
    companyMapper = MapperManager.INSTANCE.getCompanyMapper();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {
    Long computerId = Util.parseLong(req.getParameter("id"));
    Computer computer;
    if (computerId == null) {
      computer = null;
    } else {
      computer = computerService.getFromId(computerId);
    }

    showEditComputerForm(req, resp, computer, null);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {
    List<String> errors = new ArrayList<>();
    Computer computer = null;

    Long computerId = Util.parseLong(req.getParameter("computerId"));
    if (computerId == null || (computer = computerService.getFromId(computerId)) == null) {
      showEditComputerForm(req, resp, null, errors);
      return;
    }

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

    computer.setIntroduced(introduced);
    computer.setDiscontinued(discontinued);
    computer.setCompany(company);

    if (errors.isEmpty()) {
      try {
        computerService.update(computer);
      } catch (PersistenceException e) {
        LOGGER.error("Unable to save the computer", e);
        errors.add("Internal error: unable to save the computer.");
      }
    }

    if (errors.isEmpty()) {
      resp.sendRedirect("../dashboard");
    } else {
      showEditComputerForm(req, resp, computer, errors);
    }
  }

  private void showEditComputerForm(HttpServletRequest req, HttpServletResponse resp,
      Computer computer, List<String> errors) throws ServletException, IOException {
    List<CompanyDTO> companies = companyMapper.mapToDTOList(companyService.findAll());
    req.setAttribute("computer", computer);
    req.setAttribute("companies", companies);
    req.setAttribute("errors", errors);
    req.getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(req, resp);
  }
}

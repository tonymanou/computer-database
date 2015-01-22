package com.tonymanou.computerdb.controller;

import java.io.IOException;
import java.time.LocalDate;
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
    Long id = Util.parseLong(req.getParameter("id"));
    if (id == null) {
      RuntimeException exception = new IllegalArgumentException("Computer id cannot be emty");
      LOGGER.error(exception.getMessage(), exception);
      throw exception;
    }

    List<CompanyDTO> companies = companyMapper.mapToDTOList(companyService.findAll());
    Computer computer = computerService.getFromId(id);
    req.setAttribute("computer", computer);
    req.setAttribute("companies", companies);
    req.getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {
    Long id = Long.parseLong(req.getParameter("computerId"));
    if (id == null) {
      RuntimeException exception = new IllegalArgumentException("Empty computer id");
      LOGGER.error(exception.getMessage(), exception);
      throw exception;
    }

    String name = req.getParameter("computerName");
    if (Util.isStringEmpty(name)) {
      RuntimeException exception = new IllegalArgumentException("Computer name cannot be empty");
      LOGGER.error(exception.getMessage(), exception);
      throw exception;
    }

    String introducedDate = req.getParameter("introduced");
    LocalDate introduced;
    if (Util.isStringEmpty(introducedDate)) {
      introduced = null;
    } else {
      introduced = Util.parseLocalDate(introducedDate);
      if (introduced == null) {
        // TODO Cannot parse date
      }
    }

    String discontinuedDate = req.getParameter("discontinued");
    LocalDate discontinued;
    if (Util.isStringEmpty(discontinuedDate)) {
      discontinued = null;
    } else {
      discontinued = Util.parseLocalDate(discontinuedDate);
      if (discontinued == null) {
        // TODO Cannot parse date
      }
    }

    Long companyId = Long.parseLong(req.getParameter("companyId"));

    // @formatter:off
    computerService.update(Computer.getBuilder(name)
        .setId(id)
        .setIntroduced(introduced)
        .setDiscontinued(discontinued)
        .setCompany((companyId != 0) ? companyService.getFromId(companyId) : null)
        .build());
    // @formatter:on

    resp.sendRedirect("../dashboard");
  }
}

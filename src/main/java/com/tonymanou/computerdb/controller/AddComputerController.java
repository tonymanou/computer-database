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

import com.tonymanou.computerdb.dto.CompanyDTO;
import com.tonymanou.computerdb.entity.Computer;
import com.tonymanou.computerdb.mapper.CompanyMapper;
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

  public AddComputerController() {
    companyService = ServiceManager.INSTANCE.getCompanyService();
    computerService = ServiceManager.INSTANCE.getComputerService();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {
    List<CompanyDTO> companies = CompanyMapper.mapToDTOList(companyService.findAll());
    req.setAttribute("companies", companies);
    req.getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {
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
    computerService.create(Computer.getBuilder(name)
        .setIntroduced(introduced)
        .setDiscontinued(discontinued)
        .setCompany((companyId != 0) ? companyService.getFromId(companyId) : null)
        .build());
    // @formatter:on

    resp.sendRedirect("../dashboard");
  }
}

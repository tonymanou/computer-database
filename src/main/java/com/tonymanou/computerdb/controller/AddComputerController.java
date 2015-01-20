package com.tonymanou.computerdb.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tonymanou.computerdb.entity.Company;
import com.tonymanou.computerdb.entity.Computer;
import com.tonymanou.computerdb.service.ICompanyService;
import com.tonymanou.computerdb.service.IComputerService;
import com.tonymanou.computerdb.service.ServiceManager;
import com.tonymanou.computerdb.util.Util;

@WebServlet("/computer/add")
public class AddComputerController extends HttpServlet {

  private static final long serialVersionUID = -4711445570099851743L;

  private ICompanyService companyService;
  private IComputerService computerService;

  public AddComputerController() {
    companyService = ServiceManager.INSTANCE.getCompanyService();
    computerService = ServiceManager.INSTANCE.getComputerService();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {
    List<Company> companies = companyService.findAll();
    req.setAttribute("companies", companies);
    req.getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {
    String name = req.getParameter("computerName");
    LocalDate introduced = Util.parseLocalDate(req.getParameter("introduced"));
    LocalDate discontinued = Util.parseLocalDate(req.getParameter("discontinued"));
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

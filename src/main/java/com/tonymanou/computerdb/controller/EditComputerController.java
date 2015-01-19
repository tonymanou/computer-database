package com.tonymanou.computerdb.controller;

import java.io.IOException;
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

@WebServlet("/computer/edit")
public class EditComputerController extends HttpServlet {

  private static final long serialVersionUID = 1704952071605732498L;

  private ICompanyService companyService;
  private IComputerService computerService;

  public EditComputerController() {
    companyService = ServiceManager.INSTANCE.getCompanyService();
    computerService = ServiceManager.INSTANCE.getComputerService();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {
    Long id = Long.parseLong(req.getParameter("id"));
    List<Company> companies = companyService.findAll();
    Computer computer = computerService.getFromId(id);
    req.setAttribute("computer", computer);
    req.setAttribute("companies", companies);
    req.getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(req, resp);
  }
}

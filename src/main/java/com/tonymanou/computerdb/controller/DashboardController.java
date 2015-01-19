package com.tonymanou.computerdb.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tonymanou.computerdb.entity.Computer;
import com.tonymanou.computerdb.service.ServiceManager;

@WebServlet("/dashboard")
public class DashboardController extends HttpServlet {

  private static final long serialVersionUID = 1075773814185556399L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {
    List<Computer> computers = ServiceManager.INSTANCE.getComputerService().findAll();
    req.setAttribute("computers", computers);
    req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);
  }
}

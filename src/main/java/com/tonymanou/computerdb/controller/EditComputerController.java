package com.tonymanou.computerdb.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/computer/edit")
public class EditComputerController extends HttpServlet {

  private static final long serialVersionUID = 1704952071605732498L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {
    req.getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(req, resp);
  }
}

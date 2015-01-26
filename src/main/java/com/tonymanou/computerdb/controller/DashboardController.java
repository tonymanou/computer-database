package com.tonymanou.computerdb.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tonymanou.computerdb.domain.Computer;
import com.tonymanou.computerdb.dto.ComputerDTO;
import com.tonymanou.computerdb.mapper.IEntityMapper;
import com.tonymanou.computerdb.mapper.MapperManager;
import com.tonymanou.computerdb.pagination.ComputerPage;
import com.tonymanou.computerdb.service.IComputerService;
import com.tonymanou.computerdb.service.ServiceManager;
import com.tonymanou.computerdb.util.Util;

@WebServlet("/dashboard")
public class DashboardController extends HttpServlet {

  private static final long serialVersionUID = 1075773814185556399L;
  private static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);
  private static final String ELEMENT_PER_PAGE = "element_per_page";

  private IComputerService computerService;
  private IEntityMapper<Computer, ComputerDTO> computerMapper;

  public DashboardController() {
    computerService = ServiceManager.INSTANCE.getComputerService();
    computerMapper = MapperManager.INSTANCE.getComputerMapper();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {
    HttpSession session = req.getSession();
    ComputerPage.Builder page = ComputerPage.getBuilder();

    Object elementsPerPage = session.getAttribute(ELEMENT_PER_PAGE);
    if (elementsPerPage != null) {
      page.setNumElementsPerPage((int) elementsPerPage);
    }

    String search = req.getParameter("search");
    if (!Util.isStringEmpty(search)) {
      page.setSearchQuery(search);
    }

    String pageNumber = req.getParameter("page");
    if (!Util.isStringEmpty(pageNumber)) {
      int number = Util.parsePositiveLong(pageNumber).intValue();
      page.setCurrentPage(number);
    }

    List<ComputerDTO> computers = computerMapper.mapToDTOList(computerService.findAll(page));
    req.setAttribute("computers", computers);
    req.setAttribute("page", page.build());
    req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {
    String idList = req.getParameter("selection");

    if (idList != null) {
      String[] idsList = idList.split(",");

      for (String current : idsList) {
        Long id = Util.parsePositiveLong(current);

        if (id != null) {
          computerService.delete(id);
        } else {
          LOGGER.warn("Unparsable id: " + current);
        }
      }
    } else {
      LOGGER.error("Empty selection list");
    }

    doGet(req, resp);
  }
}

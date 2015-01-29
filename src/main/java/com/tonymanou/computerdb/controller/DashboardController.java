package com.tonymanou.computerdb.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tonymanou.computerdb.domain.Computer;
import com.tonymanou.computerdb.dto.ComputerDTO;
import com.tonymanou.computerdb.mapper.IEntityMapper;
import com.tonymanou.computerdb.pagination.ComputerPage;
import com.tonymanou.computerdb.service.IComputerService;
import com.tonymanou.computerdb.util.Util;

@Controller
public class DashboardController {

  private static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);

  @Autowired
  private IComputerService computerService;
  @Autowired
  private IEntityMapper<Computer, ComputerDTO> computerMapper;

  @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
  protected String doGet(HttpServletRequest req, HttpServletResponse resp) {
    ComputerPage.Builder page = ComputerPage.getBuilder();

    String perPage = req.getParameter("elems");
    if (!Util.isStringEmpty(perPage)) {
      int elementsPerPage = Util.parsePositiveLong(perPage).intValue();
      page.setNumElementsPerPage((int) elementsPerPage);
    }

    String search = req.getParameter("search");
    if (!Util.isStringEmpty(search)) {
      page.setSearchQuery(search.trim());
    }

    String pageNumber = req.getParameter("page");
    if (!Util.isStringEmpty(pageNumber)) {
      int number = Util.parsePositiveLong(pageNumber).intValue();
      page.setCurrentPage(number);
    }

    List<ComputerDTO> computers = computerMapper.toDTOList(computerService.findAll(page));
    req.setAttribute("computers", computers);
    req.setAttribute("page", page.build());

    return "dashboard";
  }

  @RequestMapping(value = "/dashboard", method = RequestMethod.POST)
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

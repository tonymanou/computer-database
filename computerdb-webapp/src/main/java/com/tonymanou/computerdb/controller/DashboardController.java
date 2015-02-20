package com.tonymanou.computerdb.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.HtmlUtils;

import com.tonymanou.computerdb.dto.ComputerDTO;
import com.tonymanou.computerdb.mapper.IEntityMapper;
import com.tonymanou.computerdb.model.Computer;
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
  protected String dashboardGet(HttpServletRequest req) {
    ComputerPage.Builder page = ComputerPage.getBuilder();

    String perPage = req.getParameter("elems");
    if (!Util.isStringEmpty(perPage)) {
      int elementsPerPage = Util.parsePositiveLong(perPage).intValue();
      page.setNumElementsPerPage((int) elementsPerPage);
    }

    String search = HtmlUtils.htmlEscape(req.getParameter("search"), "UTF-8");
    if (!Util.isStringEmpty(search)) {
      page.setSearchQuery(search.trim());
    }

    String pageNumber = req.getParameter("page");
    if (!Util.isStringEmpty(pageNumber)) {
      int number = Util.parsePositiveLong(pageNumber).intValue();
      page.setCurrentPage(number);
    }

    List<ComputerDTO> computers = computerMapper.toDTOList(computerService.findPage(page));
    req.setAttribute("computers", computers);
    req.setAttribute("page", page.build());

    return "dashboard";
  }

  @RequestMapping(value = "/dashboard", method = RequestMethod.POST)
  protected String dashboardPost(HttpServletRequest req) {
    String idList = req.getParameter("selection");

    if (!Util.isStringEmpty(idList)) {
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
      LOGGER.info("Empty selection list");
    }

    return dashboardGet(req);
  }
}

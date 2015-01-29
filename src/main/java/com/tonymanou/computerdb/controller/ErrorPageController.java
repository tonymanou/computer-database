package com.tonymanou.computerdb.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorPageController {

  @RequestMapping(value = "/error")
  protected String displayError(HttpServletRequest req, Model model) {
    model.addAttribute("errorCode", req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
    model.addAttribute("errorMessage", req.getAttribute(RequestDispatcher.ERROR_MESSAGE));
    return "error";
  }
}

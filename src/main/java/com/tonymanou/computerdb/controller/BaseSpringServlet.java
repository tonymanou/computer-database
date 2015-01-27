package com.tonymanou.computerdb.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class BaseSpringServlet extends HttpServlet {

  private static final long serialVersionUID = 7905032361351573177L;

  @Override
  public void init() throws ServletException {
    super.init();
    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
  }
}

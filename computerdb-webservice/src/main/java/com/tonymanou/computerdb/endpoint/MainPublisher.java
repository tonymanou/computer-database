package com.tonymanou.computerdb.endpoint;

import javax.xml.ws.Endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tonymanou.computerdb.webservice.impl.CompanyWS;
import com.tonymanou.computerdb.webservice.impl.ComputerWS;

public class MainPublisher {

  private static final String URL_BASE = "http://localhost:9999/computerdb-webservice/";
  private static final Logger LOGGER = LoggerFactory.getLogger(MainPublisher.class);

  public static void main(String[] args) {
    LOGGER.info("Starting company webservice...");
    Endpoint.publish(URL_BASE + "company", new CompanyWS());

    LOGGER.info("Starting computer webservice...");
    Endpoint.publish(URL_BASE + "computer", new ComputerWS());

    LOGGER.info("Succesfully started.");
  }
}

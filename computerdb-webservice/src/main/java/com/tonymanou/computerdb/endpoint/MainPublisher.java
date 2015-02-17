package com.tonymanou.computerdb.endpoint;

import javax.xml.ws.Endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tonymanou.computerdb.webservice.ICompanyWS;
import com.tonymanou.computerdb.webservice.IComputerWS;

public class MainPublisher {

  private static final String URL_BASE = "http://localhost:9999/computerdb-webservice/";
  private static final Logger LOGGER = LoggerFactory.getLogger(MainPublisher.class);

  private static ClassPathXmlApplicationContext context;

  public static void main(String[] args) {
    context = new ClassPathXmlApplicationContext(
        "classpath:applicationContext-webservice.xml");

    LOGGER.info("Starting company webservice...");
    ICompanyWS companyWS = context.getBean(ICompanyWS.class);
    Endpoint.publish(URL_BASE + "company", companyWS);

    LOGGER.info("Starting computer webservice...");
    IComputerWS computerWS = context.getBean(IComputerWS.class);
    Endpoint.publish(URL_BASE + "computer", computerWS);

    LOGGER.info("Succesfully started.");
  }
}

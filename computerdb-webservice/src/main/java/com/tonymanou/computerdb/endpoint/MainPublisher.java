package com.tonymanou.computerdb.endpoint;

import javax.xml.ws.Endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tonymanou.computerdb.webservice.ICompanyWS;
import com.tonymanou.computerdb.webservice.IComputerWS;
import com.tonymanou.computerdb.webservice.WebserviceProperties;

public class MainPublisher {

  private static final Logger LOGGER = LoggerFactory.getLogger(MainPublisher.class);

  private static ClassPathXmlApplicationContext context;

  public static void main(String[] args) {
    // Load Spring context
    context = new ClassPathXmlApplicationContext(
        "classpath:applicationContext-webservice.xml");

    // Retrieve properties
    WebserviceProperties properties = context.getBean(WebserviceProperties.class);
    final String URL_BASE = properties.getUrl();

    // Register webservices
    LOGGER.info("Starting webservice on " + URL_BASE);

    ICompanyWS companyWS = context.getBean(ICompanyWS.class);
    Endpoint.publish(URL_BASE + "company", companyWS);

    IComputerWS computerWS = context.getBean(IComputerWS.class);
    Endpoint.publish(URL_BASE + "computer", computerWS);

    LOGGER.info("Succesfully started.");
  }
}

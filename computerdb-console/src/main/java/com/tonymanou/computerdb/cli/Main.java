package com.tonymanou.computerdb.cli;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tonymanou.computerdb.webservice.ICompanyWS;
import com.tonymanou.computerdb.webservice.IComputerWS;

/**
 * Program's entry point.
 *
 * @author tonymanou
 */
public class Main {

  private static final String NAMESPACE = "http://impl.webservice.computerdb.tonymanou.com/";

  public static void main(String[] args) throws MalformedURLException {
    // Create Spring context
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
        "classpath:applicationContext-console.xml");

    // Retrieve properties
    WebserviceProperties properties = context.getBean(WebserviceProperties.class);
    final String URL_BASE = properties.getUrl();

    // Retrieve webservice objects
    URL url1 = new URL(URL_BASE + "computer?wsdl");
    QName qname1 = new QName(NAMESPACE, "ComputerWSService");
    Service service1 = Service.create(url1, qname1);
    IComputerWS computerWS = service1.getPort(IComputerWS.class);

    URL url2 = new URL(URL_BASE + "company?wsdl");
    QName qname2 = new QName(NAMESPACE, "CompanyWSService");
    Service service2 = Service.create(url2, qname2);
    ICompanyWS companyWS = service2.getPort(ICompanyWS.class);

    // Launch CLI routine
    CLIRoutine routine = context.getBean(CLIRoutine.class);
    routine.init(computerWS, companyWS);
    routine.doMainMenu();

    // Close Spring context
    context.close();
  }
}

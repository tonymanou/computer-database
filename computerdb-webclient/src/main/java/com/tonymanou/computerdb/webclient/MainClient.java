package com.tonymanou.computerdb.webclient;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.tonymanou.computerdb.webservice.ICompanyWS;
import com.tonymanou.computerdb.webservice.IComputerWS;

/**
 * Program's entry point.
 *
 * @author tonymanou
 */
public class MainClient {

  private static final String URL_BASE = "http://localhost:9999/computerdb-webservice/";
  private static final String NAMESPACE = "http://impl.webservice.computerdb.tonymanou.com/";

  public static void main(String[] args) throws MalformedURLException {
    URL url1 = new URL(URL_BASE + "computer?wsdl");
    QName qname1 = new QName(NAMESPACE, "ComputerWSService");
    Service service1 = Service.create(url1, qname1);
    IComputerWS computerWS = service1.getPort(IComputerWS.class);

    URL url2 = new URL(URL_BASE + "company?wsdl");
    QName qname2 = new QName(NAMESPACE, "CompanyWSService");
    Service service2 = Service.create(url2, qname2);
    ICompanyWS companyWS = service2.getPort(ICompanyWS.class);

    CLIRoutine routine = new CLIRoutine(computerWS, companyWS);
    routine.doMainMenu();
  }
}

package com.tonymanou.computerdb.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import com.tonymanou.computerdb.model.Company;
import com.tonymanou.computerdb.webservice.wrapper.ListWrapper;

/**
 * Interface implemented by web-services to manage companies.
 *
 * @author tonymanou
 */
@WebService
@SOAPBinding(style = Style.RPC)
public interface ICompanyWS {

  /**
   * Retrieve all the companies from the database.
   *
   * @return A list containing all the companies.
   */
  @WebMethod
  ListWrapper<Company> findAll();

  /**
   * Retrieve a company from the database thanks to the given id.
   *
   * @param id
   *          The id of the company to retrieve.
   * @return The {@link Company}, or null if no matching company was found.
   */
  @WebMethod
  Company getFromId(Long id);

  /**
   * Remove a company, and its computers, from the database.
   * 
   * @param connection
   *          Active database connection.
   * @param id
   *          The id of the company.
   * @param connection
   */
  @WebMethod
  void delete(Long id);
}

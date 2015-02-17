package com.tonymanou.computerdb.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import com.tonymanou.computerdb.dto.ComputerDTO;
import com.tonymanou.computerdb.pagination.ComputerPage;
import com.tonymanou.computerdb.webservice.wrapper.ListWrapper;

/**
 * Interface implemented by web-services to manage computers.
 * 
 * @author tonymanou
 */
@WebService
@SOAPBinding(style = Style.RPC)
public interface IComputerWS {

  /**
   * Retrieve all the computers from the database.
   *
   * @return A list containing all the computers.
   */
  @WebMethod
  ListWrapper<ComputerDTO> findAll();

  /**
   * Retrieve a page of computers from the database.
   *
   * @param page
   *          Description of the pagination to use.
   * @return A list containing the computers.
   */
  @WebMethod
  ListWrapper<ComputerDTO> findPage(ComputerPage.Builder page);

  /**
   * Create a new computer in the database.
   *
   * @param computerDTO
   *          The computer to create.
   */
  @WebMethod
  void create(ComputerDTO computerDTO);

  /**
   * Update a computer in the database.
   *
   * @param computerDTO
   *          The computer to update.
   */
  @WebMethod
  void update(ComputerDTO computerDTO);

  /**
   * Delete a computer from the database.
   *
   * @param id
   *          Id of the computer to delete.
   */
  @WebMethod
  void delete(Long id);

  /**
   * Retrieve a computer from the database thanks to the given id.
   *
   * @param id
   *          The id of the computer to retrieve.
   * @return The {@link ComputerDTO}, or null if no matching company was found.
   */
  @WebMethod
  ComputerDTO getFromId(Long id);
}

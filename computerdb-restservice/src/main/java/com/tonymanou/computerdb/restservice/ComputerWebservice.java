package com.tonymanou.computerdb.restservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tonymanou.computerdb.dto.ComputerDTO;
import com.tonymanou.computerdb.mapper.IEntityMapper;
import com.tonymanou.computerdb.model.Computer;
import com.tonymanou.computerdb.pagination.ComputerPage;
import com.tonymanou.computerdb.pagination.ComputerPage.ComputerOrder;
import com.tonymanou.computerdb.pagination.ComputerPage.OrderType;
import com.tonymanou.computerdb.service.IComputerService;

@RestController
@RequestMapping("/computer")
public class ComputerWebservice {

  @Autowired
  private IComputerService computerService;
  @Autowired
  private IEntityMapper<Computer, ComputerDTO> computerMapper;

  @RequestMapping(method = RequestMethod.GET)
  public List<ComputerDTO> getAll() {
    List<Computer> list = computerService.findAll();
    return computerMapper.toDTOList(list);
  }

  @RequestMapping(value = "/page/{page}", method = RequestMethod.GET)
  public List<ComputerDTO> findPage(@PathVariable("page") Integer page, String orderType,
      String order, String search) {
    ComputerPage.Builder pBuilder = ComputerPage.getBuilder();
    pBuilder.setNumElementsPerPage(10);
    pBuilder.setCurrentPage(page);
    if (orderType != null) {
      pBuilder.setOrderType(OrderType.valueOf(orderType));
    }
    if (order != null) {
      pBuilder.setOrder(ComputerOrder.valueOf(order));
    }
    pBuilder.setSearchQuery(search);
    List<Computer> list = computerService.findPage(pBuilder);
    return computerMapper.toDTOList(list);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ComputerDTO getFromId(@PathVariable("id") Long id) {
    Computer computer = computerService.getFromId(id);
    return computerMapper.toDTO(computer);
  }

  @RequestMapping(method = RequestMethod.POST)
  public String create(ComputerDTO computerDTO) {
    Computer computer = computerMapper.fromDTO(computerDTO);
    computerService.create(computer);
    return "ok";
  }

  @RequestMapping(method = RequestMethod.PUT)
  public String update(ComputerDTO computerDTO) {
    Computer computer = computerMapper.fromDTO(computerDTO);
    computerService.update(computer);
    return "ok";
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public String delete(Long id) {
    computerService.delete(id);
    return "ok";
  }
}

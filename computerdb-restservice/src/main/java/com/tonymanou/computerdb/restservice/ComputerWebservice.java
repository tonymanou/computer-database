package com.tonymanou.computerdb.restservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tonymanou.computerdb.dto.ComputerDTO;
import com.tonymanou.computerdb.mapper.IEntityMapper;
import com.tonymanou.computerdb.model.Computer;
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
}

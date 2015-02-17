package com.tonymanou.computerdb.webservice.impl;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tonymanou.computerdb.dto.ComputerDTO;
import com.tonymanou.computerdb.mapper.IEntityMapper;
import com.tonymanou.computerdb.model.Computer;
import com.tonymanou.computerdb.pagination.ComputerPage.Builder;
import com.tonymanou.computerdb.service.IComputerService;
import com.tonymanou.computerdb.webservice.IComputerWS;
import com.tonymanou.computerdb.webservice.wrapper.ListWrapper;

@Service
@WebService(endpointInterface = "com.tonymanou.computerdb.webservice.IComputerWS")
public class ComputerWS implements IComputerWS {

  @Autowired
  private IComputerService computerService;
  @Autowired
  private IEntityMapper<Computer, ComputerDTO> computerMapper;

  @Override
  public ListWrapper<ComputerDTO> findAll() {
    ListWrapper<ComputerDTO> wrapper = new ListWrapper<>();
    List<Computer> list = computerService.findAll();
    wrapper.setList(computerMapper.toDTOList(list));
    return wrapper;
  }

  @Override
  public ListWrapper<ComputerDTO> findPage(Builder page) {
    ListWrapper<ComputerDTO> wrapper = new ListWrapper<>();
    List<Computer> list = computerService.findPage(page);
    wrapper.setList(computerMapper.toDTOList(list));
    return wrapper;
  }

  @Override
  public void create(ComputerDTO computerDTO) {
    Computer computer = computerMapper.fromDTO(computerDTO);
    computerService.create(computer);
  }

  @Override
  public void update(ComputerDTO computerDTO) {
    Computer computer = computerMapper.fromDTO(computerDTO);
    computerService.update(computer);
  }

  @Override
  public void delete(Long id) {
    computerService.delete(id);
  }

  @Override
  public ComputerDTO getFromId(Long id) {
    Computer computer = computerService.getFromId(id);
    return computerMapper.toDTO(computer);
  }
}

package com.tonymanou.computerdb.mapper.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.tonymanou.computerdb.dao.impl.SQLUtil;
import com.tonymanou.computerdb.domain.Company;
import com.tonymanou.computerdb.domain.Computer;

/**
 * Implementation of a RowMapper to map a ResultSet entry to a Computer.
 * 
 * @author tonymanou
 */
@Component
public class ComputerRowMapper implements RowMapper<Computer> {

  @Override
  public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
    // @formatter:off
    Computer.Builder builderComputer = Computer
        .getBuilder(rs.getString(2))
        .setId(rs.getLong(1))
        .setIntroduced(SQLUtil.getLocalDate(rs.getTimestamp(3)))
        .setDiscontinued(SQLUtil.getLocalDate(rs.getTimestamp(4)));
    // @formatter:on

    Long companyId = rs.getLong(5);
    Company company;
    if (companyId != 0) {
      // @formatter:off
      company = Company
          .getBuilder(rs.getString(6))
          .setId(companyId)
          .build();
      // @formatter:on
    } else {
      company = null;
    }
    builderComputer.setCompany(company);

    return builderComputer.build();
  }
}

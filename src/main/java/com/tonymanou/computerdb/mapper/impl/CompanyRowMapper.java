package com.tonymanou.computerdb.mapper.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.tonymanou.computerdb.domain.Company;

/**
 * Implementation of a RowMapper to map a ResultSet entry to a Company.
 * 
 * @author tonymanou
 */
@Component
public class CompanyRowMapper implements RowMapper<Company> {

  @Override
  public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
    // @formatter:off
    return Company
        .getBuilder(rs.getString(2))
        .setId(rs.getLong(1))
        .build();
    // @formatter:on
  }
}

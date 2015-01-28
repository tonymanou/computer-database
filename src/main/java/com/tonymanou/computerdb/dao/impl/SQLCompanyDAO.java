package com.tonymanou.computerdb.dao.impl;

import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.tonymanou.computerdb.dao.ICompanyDAO;
import com.tonymanou.computerdb.domain.Company;

/**
 * DAO implementation to manage companies in a SQL database.
 *
 * @author tonymanou
 */
@Component
public class SQLCompanyDAO implements ICompanyDAO {

  @Autowired
  private DataSource dataSource;
  @Autowired
  private RowMapper<Company> companyRowMapper;

  @Override
  public List<Company> findAll() {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

    return jdbcTemplate.query("SELECT id, name FROM company;", companyRowMapper);
  }

  @Override
  public Company getFromId(Long id) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    Object[] args = { id };
    int[] types = { Types.BIGINT };

    List<Company> list = jdbcTemplate.query("SELECT id, name FROM company WHERE id=?;", args,
        types, companyRowMapper);

    Company company;
    if (list.size() == 0) {
      company = null;
    } else {
      company = list.get(0);
    }
    return company;
  }

  @Override
  public void delete(Long id) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    Object[] args = { id };
    int[] types = { Types.BIGINT };

    jdbcTemplate.update("DELETE FROM company WHERE id=?;", args, types);
  }
}

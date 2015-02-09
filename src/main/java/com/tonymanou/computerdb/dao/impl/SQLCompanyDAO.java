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

  private static final String FIND_ALL_QUERY = "SELECT id, name FROM company;";
  private static final String FIND_BY_ID_QUERY = "SELECT id, name FROM company company WHERE id=?;";
  private static final String DELETE_BY_ID_QUERY = "DELETE FROM company WHERE id=?;";

  @Autowired
  private DataSource dataSource;
  @Autowired
  private RowMapper<Company> companyRowMapper;

  @Override
  public List<Company> findAll() {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

    return jdbcTemplate.query(FIND_ALL_QUERY, companyRowMapper);
  }

  @Override
  public Company getFromId(Long id) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    Object[] args = { id };
    int[] types = { Types.BIGINT };

    List<Company> list = jdbcTemplate.query(FIND_BY_ID_QUERY, args, types, companyRowMapper);

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

    jdbcTemplate.update(DELETE_BY_ID_QUERY, args, types);
  }
}

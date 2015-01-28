package com.tonymanou.computerdb.dao.impl;

import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.tonymanou.computerdb.dao.IComputerDAO;
import com.tonymanou.computerdb.domain.Company;
import com.tonymanou.computerdb.domain.Computer;
import com.tonymanou.computerdb.exception.PersistenceException;
import com.tonymanou.computerdb.pagination.ComputerPage;
import com.tonymanou.computerdb.util.Util;

/**
 * DAO implementation to manage computers in a SQL database.
 *
 * @author tonymanou
 */
@Component
public class SQLComputerDAO implements IComputerDAO {

  private static final Logger LOGGER = LoggerFactory.getLogger(SQLComputerDAO.class);

  @Autowired
  private DataSource dataSource;
  @Autowired
  private RowMapper<Computer> computerRowMapper;

  @Override
  public List<Computer> findAll(ComputerPage.Builder pageBuilder) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

    /* ========== First query: element count ========== */

    String searchString = pageBuilder.getSearchQuery();
    boolean searching = !Util.isStringEmpty(searchString);
    String search;
    if (searching) {
      search = new StringBuilder("%").append(searchString).append("%").toString();
    } else {
      search = null;
    }

    // Create query
    StringBuilder query1 = new StringBuilder("SELECT COUNT(c.id) FROM computer c");
    if (searching) {
      query1
          .append(" LEFT JOIN company d ON c.company_id = d.id WHERE c.name LIKE ? OR d.name LIKE ?");
    }
    query1.append(';');

    // Fill-in parameters
    Object[] args1;
    int[] types1;

    // Fill-in parameters
    if (searching) {
      args1 = new Object[] { search, search };
      types1 = new int[] { Types.VARCHAR, Types.VARCHAR };
    } else {
      args1 = new Object[0];
      types1 = new int[0];
    }

    Long count = jdbcTemplate.queryForObject(query1.toString(), args1, types1, Long.class);
    if (count == null) {
      RuntimeException e = new PersistenceException("Computer count is null");
      LOGGER.error("Error while getting the number of computers matching the search query", e);
      throw e;
    } else {
      pageBuilder.setNumElements(count);
    }

    /* ========== Second query: computer list ========== */

    // Create query
    StringBuilder query2 = new StringBuilder(
        "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, d.name FROM computer c LEFT JOIN company d ON c.company_id = d.id");
    if (searching) {
      query2.append(" WHERE c.name LIKE ? OR d.name LIKE ?");
    }
    query2.append(" ORDER BY ? ? LIMIT ? OFFSET ?;");

    // Fill-in parameters
    Object[] args2;
    int[] types2;

    ComputerPage page = pageBuilder.build();
    String order;
    switch (page.getOrder()) {
    case NAME:
      order = "c.name";
      break;
    case COMPANY:
      order = "d.name";
      break;
    default:
      order = "c.id";
      break;
    }

    if (searching) {
      // @formatter:off
      args2 = new Object[]{
          search,
          search,
          order,
          page.getOrderType().name(),
          page.getNumElementsPerPage(),
          page.getElementsOffset()
      };
      types2 = new int[]{
          Types.VARCHAR,
          Types.VARCHAR,
          Types.VARCHAR,
          Types.VARCHAR,
          Types.BIGINT,
          Types.BIGINT
      };
      // @formatter:on
    } else {
      // @formatter:off
      args2 = new Object[]{
          order,
          page.getOrderType().name(),
          page.getNumElementsPerPage(),
          page.getElementsOffset()
      };
      types2 = new int[]{
          Types.VARCHAR,
          Types.VARCHAR,
          Types.BIGINT,
          Types.BIGINT
      };
      // @formatter:on
    }

    return jdbcTemplate.query(query2.toString(), args2, types2, computerRowMapper);
  }

  @Override
  public void create(Computer computer) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    Company company = computer.getCompany();
    // @formatter:off
    Object[] args = {
        computer.getName(),
        SQLUtil.getTimestamp(computer.getIntroduced()),
        SQLUtil.getTimestamp(computer.getDiscontinued()),
        company == null ? null : company.getId()
    };
    int[] types = {
        Types.VARCHAR,
        Types.TIMESTAMP,
        Types.TIMESTAMP,
        Types.BIGINT
    };
    // @formatter:on

    jdbcTemplate.update(
        "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?);",
        args, types);
  }

  @Override
  public void update(Computer computer) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    Company company = computer.getCompany();
    // @formatter:off
    Object[] args = {
        computer.getName(),
        SQLUtil.getTimestamp(computer.getIntroduced()),
        SQLUtil.getTimestamp(computer.getDiscontinued()),
        company == null ? null : company.getId(),
        computer.getId()
    };
    int[] types = {
        Types.VARCHAR,
        Types.TIMESTAMP,
        Types.TIMESTAMP,
        Types.BIGINT,
        Types.BIGINT
    };
    // @formatter:on

    jdbcTemplate.update(
        "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?;", args,
        types);
  }

  @Override
  public void delete(Long id) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    Object[] args = { id };
    int[] types = { Types.BIGINT };

    jdbcTemplate.update("DELETE FROM computer WHERE id=?;", args, types);
  }

  @Override
  public void deleteAllWithCompanyId(Long companyId) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    Object[] args = { companyId };
    int[] types = { Types.BIGINT };

    jdbcTemplate.update("DELETE FROM computer WHERE company_id=?;", args, types);
  }

  @Override
  public Computer getFromId(Long id) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    Object[] args = { id };
    int[] types = { Types.BIGINT };

    List<Computer> list = jdbcTemplate
        .query(
            "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, d.name FROM computer c LEFT JOIN company d ON c.company_id = d.id WHERE c.id=?;",
            args, types, computerRowMapper);

    Computer computer;
    if (list.size() <= 0) {
      computer = null;
    } else {
      computer = list.get(0);
    }
    return computer;
  }
}

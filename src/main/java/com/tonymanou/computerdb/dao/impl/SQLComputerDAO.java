package com.tonymanou.computerdb.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tonymanou.computerdb.dao.IComputerDAO;
import com.tonymanou.computerdb.domain.Company;
import com.tonymanou.computerdb.domain.Computer;
import com.tonymanou.computerdb.exception.PersistenceException;
import com.tonymanou.computerdb.pagination.ComputerPage;

/**
 * DAO implementation to manage computers in a SQL database.
 *
 * @author tonymanou
 */
public class SQLComputerDAO implements IComputerDAO {

  private static final Logger LOGGER = LoggerFactory.getLogger(SQLComputerDAO.class);

  @Override
  public List<Computer> findAll() {
    return findAll(new ComputerPage());
  }

  @Override
  public List<Computer> findAll(ComputerPage page) {
    List<Computer> list = null;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultat = null;

    try {
      connection = SQLUtil.getConnection();
      statement = connection
          .prepareStatement("SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, d.name FROM computer c LEFT JOIN company d ON c.company_id = d.id ORDER BY ? ? LIMIT ? OFFSET ?;");

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
      statement.setString(1, order);
      statement.setString(2, page.getOrderType().name());
      statement.setInt(3, page.getElementsPerPage());
      statement.setInt(4, page.getElementsOffset());

      resultat = statement.executeQuery();
      list = extractToList(resultat);
    } catch (SQLException e) {
      LOGGER.error("SQL exception", e);
      throw new PersistenceException(e);
    } finally {
      SQLUtil.close(resultat, statement, connection);
    }

    return list;
  }

  @Override
  public void create(Computer computer) {
    Connection connection = null;
    PreparedStatement statement = null;

    try {
      connection = SQLUtil.getConnection();
      statement = connection
          .prepareStatement("INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?);");
      statement.setString(1, computer.getName());
      statement.setTimestamp(2, SQLUtil.getTimestamp(computer.getIntroduced()));
      statement.setTimestamp(3, SQLUtil.getTimestamp(computer.getDiscontinued()));
      Company company = computer.getCompany();
      if (company == null) {
        statement.setNull(4, Types.BIGINT);
      } else {
        statement.setLong(4, company.getId());
      }
      statement.executeUpdate();
    } catch (SQLException e) {
      LOGGER.error("SQL exception", e);
      throw new PersistenceException(e);
    } finally {
      SQLUtil.close(statement, connection);
    }
  }

  @Override
  public void update(Computer computer) {
    Connection connection = null;
    PreparedStatement statement = null;

    try {
      connection = SQLUtil.getConnection();
      statement = connection
          .prepareStatement("UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?;");
      statement.setString(1, computer.getName());
      statement.setTimestamp(2, SQLUtil.getTimestamp(computer.getIntroduced()));
      statement.setTimestamp(3, SQLUtil.getTimestamp(computer.getDiscontinued()));
      Company company = computer.getCompany();
      if (company == null) {
        statement.setNull(4, Types.BIGINT);
      } else {
        statement.setLong(4, company.getId());
      }
      statement.setLong(5, computer.getId());
      statement.executeUpdate();
    } catch (SQLException e) {
      LOGGER.error("SQL exception", e);
      throw new PersistenceException(e);
    } finally {
      SQLUtil.close(statement, connection);
    }
  }

  @Override
  public void delete(Long id) {
    Connection connection = null;
    PreparedStatement statement = null;

    try {
      connection = SQLUtil.getConnection();
      statement = connection.prepareStatement("DELETE FROM computer WHERE id=?;");
      statement.setLong(1, id);
      statement.executeUpdate();
    } catch (SQLException e) {
      LOGGER.error("SQL exception", e);
      throw new PersistenceException(e);
    } finally {
      SQLUtil.close(statement, connection);
    }
  }

  @Override
  public Computer getFromId(Long id) {
    Computer computer = null;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultat = null;

    try {
      connection = SQLUtil.getConnection();
      statement = connection
          .prepareStatement("SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, d.name FROM computer c LEFT JOIN company d ON c.company_id = d.id WHERE c.id=?;");
      statement.setLong(1, id);
      resultat = statement.executeQuery();

      List<Computer> list = extractToList(resultat);
      if (list.size() == 0) {
        StringBuilder sb = new StringBuilder("No computer found with the id ").append(id);
        LOGGER.warn(sb.toString());
        computer = null;
      } else {
        computer = list.get(0);
      }
    } catch (SQLException e) {
      LOGGER.error("SQL exception", e);
      throw new PersistenceException(e);
    } finally {
      SQLUtil.close(resultat, statement, connection);
    }

    return computer;
  }

  @Override
  public int count() {
    int result = 0;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultat = null;

    try {
      connection = SQLUtil.getConnection();
      statement = connection.prepareStatement("SELECT COUNT(id) FROM computer;");
      resultat = statement.executeQuery();

      resultat.next(); // Do not check if it succeeded and let the next instruction crash
      result = resultat.getInt(1);
    } catch (SQLException e) {
      LOGGER.error("SQL exception", e);
      throw new PersistenceException(e);
    } finally {
      SQLUtil.close(statement, connection);
    }

    return result;
  }

  /**
   * Parse a {@link ResultSet} and populate a list of {@link Computer}s.
   *
   * @param resultat
   *          The result set to parse.
   * @return a list of computers.
   * @throws SQLException
   *           if an error occurred while parsing the result set.
   */
  private static List<Computer> extractToList(ResultSet resultat) throws SQLException {
    List<Computer> list = new ArrayList<Computer>();

    while (resultat.next()) {
      // @formatter:off
      Computer.Builder builderComputer = Computer.getBuilder(resultat.getString(2))
          .setId(resultat.getLong(1))
          .setIntroduced(SQLUtil.getLocalDate(resultat.getTimestamp(3)))
          .setDiscontinued(SQLUtil.getLocalDate(resultat.getTimestamp(4)));
      // @formatter:on

      Long companyId = resultat.getLong(5);
      Company company;
      if (companyId != 0) {
        // @formatter:off
        company = Company.getBuilder(resultat.getString(6))
            .setId(companyId)
            .build();
        // @formatter:on
      } else {
        company = null;
      }
      builderComputer.setCompany(company);

      list.add(builderComputer.build());
    }

    return list;
  }
}

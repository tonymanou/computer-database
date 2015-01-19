package com.tonymanou.computerdb.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.tonymanou.computerdb.dao.IComputerDAO;
import com.tonymanou.computerdb.entity.Company;
import com.tonymanou.computerdb.entity.Computer;
import com.tonymanou.computerdb.exception.PersistenceException;

/**
 * DAO implementation to manage computers in a SQL database.
 *
 * @author tonymanou
 */
public class SQLComputerDAO implements IComputerDAO {

  @Override
  public List<Computer> findAll() {
    List<Computer> list = null;
    Connection connection = null;
    Statement statement = null;
    ResultSet resultat = null;

    try {
      connection = SQLUtil.getConnection();
      statement = connection.createStatement();
      resultat = statement
          .executeQuery("SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, d.name FROM computer c LEFT JOIN company d ON c.company_id = d.id;");
      list = extractToList(resultat);
    } catch (SQLException e) {
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
      computer = list.size() == 0 ? null : list.get(0);
    } catch (SQLException e) {
      throw new PersistenceException(e);
    } finally {
      SQLUtil.close(resultat, statement, connection);
    }

    return computer;
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
      Computer.Builder builderComputer = Computer.getBuilder(null)
          .setId(resultat.getLong(1))
          .setName(resultat.getString(2))
          .setIntroduced(SQLUtil.getLocalDateTime(resultat.getTimestamp(3)))
          .setDiscontinued(SQLUtil.getLocalDateTime(resultat.getTimestamp(4)));
      // @formatter:on

      Long companyId = resultat.getLong(5);
      Company company = null;
      if (companyId != 0) {
        // @formatter:off
        company = Company.getBuilder(null)
            .setId(companyId)
            .setName(resultat.getString(6))
            .build();
        // @formatter:on
      }
      builderComputer.setCompany(company);

      list.add(builderComputer.build());
    }

    return list;
  }
}

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
    List<Computer> list = new ArrayList<Computer>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultat = null;

    try {
      connection = SQLUtil.getConnection();
      statement = connection.createStatement();
      resultat = statement
          .executeQuery("SELECT id, name, introduced, discontinued, company_id FROM computer;");

      SQLCompanyDAO companyDAO = new SQLCompanyDAO();
      Computer.Builder builder = Computer.getBuilder(null);

      while (resultat.next()) {
        builder.setId(resultat.getLong(1));
        builder.setName(resultat.getString(2));
        builder.setIntroduced(SQLUtil.getLocalDateTime(resultat.getTimestamp(3)));
        builder.setDiscontinued(SQLUtil.getLocalDateTime(resultat.getTimestamp(4)));

        Long companyId = resultat.getLong(5);
        Company company = null;
        if (companyId != 0) {
          company = companyDAO.getFromId(companyId);
        }
        builder.setCompany(company);

        list.add(builder.build());
      }
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
    SQLCompanyDAO companyDAO = new SQLCompanyDAO();

    try {
      connection = SQLUtil.getConnection();
      statement = connection
          .prepareStatement("SELECT id, name, introduced, discontinued, company_id FROM computer WHERE id=?;");
      statement.setLong(1, id);
      resultat = statement.executeQuery();

      if (resultat.first()) {
        Computer.Builder builder = Computer.getBuilder(resultat.getString(2));
        builder.setId(resultat.getLong(1));
        builder.setIntroduced(SQLUtil.getLocalDateTime(resultat.getTimestamp(3)));
        builder.setDiscontinued(SQLUtil.getLocalDateTime(resultat.getTimestamp(4)));

        Long companyId = resultat.getLong(5);
        if (companyId != 0) {
          builder.setCompany(companyDAO.getFromId(companyId));
        }

        computer = builder.build();
      }
    } catch (SQLException e) {
      throw new PersistenceException(e);
    } finally {
      SQLUtil.close(resultat, statement, connection);
    }

    return computer;
  }
}

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
import com.tonymanou.computerdb.dao.Util;
import com.tonymanou.computerdb.entity.Company;
import com.tonymanou.computerdb.entity.Computer;
import com.tonymanou.computerdb.exception.PersistenceException;

/**
 * Helper class to make actions on computers in the database.
 *
 * @author tonymanou
 */
public class SQLComputerDAO implements IComputerDAO {

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Computer> findAll() {
    List<Computer> list = new ArrayList<Computer>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultat = null;

    try {
      connection = Util.getConnection();
      statement = connection.createStatement();
      resultat = statement
          .executeQuery("SELECT id, name, introduced, discontinued, company_id FROM computer;");

      SQLCompanyDAO companyDAO = new SQLCompanyDAO();

      while (resultat.next()) {
        Computer c = new Computer();
        c.setId(resultat.getLong(1));
        c.setName(resultat.getString(2));
        c.setIntroduced(resultat.getDate(3));
        c.setDiscontinued(resultat.getDate(4));

        Long companyId = resultat.getLong(5);
        if (companyId != 0) {
          c.setCompany(companyDAO.getFromId(companyId));
        }

        list.add(c);
      }
    } catch (SQLException e) {
      throw new PersistenceException(e);
    } finally {
      Util.close(resultat, statement, connection);
    }

    return list;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void create(Computer computer) {
    Connection connection = null;
    PreparedStatement statement = null;

    try {
      connection = Util.getConnection();
      statement = connection
          .prepareStatement("INSERT INTO computer (id, name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?, ?)");
      statement.setLong(1, computer.getId());
      statement.setString(2, computer.getName());
      statement.setTimestamp(3, Util.getTimestamp(computer.getIntroduced()));
      statement.setTimestamp(4, Util.getTimestamp(computer.getDiscontinued()));
      Company company = computer.getCompany();
      if (company == null) {
        statement.setNull(5, Types.BIGINT);
      } else {
        statement.setLong(5, company.getId());
      }
      statement.executeUpdate();
    } catch (SQLException e) {
      throw new PersistenceException(e);
    } finally {
      Util.close(statement, connection);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void update(Computer computer) {
    Connection connection = null;
    PreparedStatement statement = null;

    try {
      connection = Util.getConnection();
      statement = connection
          .prepareStatement("UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?");
      statement.setString(1, computer.getName());
      statement.setTimestamp(2, Util.getTimestamp(computer.getIntroduced()));
      statement.setTimestamp(3, Util.getTimestamp(computer.getDiscontinued()));
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
      Util.close(statement, connection);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void delete(Long id) {
    Connection connection = null;
    PreparedStatement statement = null;

    try {
      connection = Util.getConnection();
      statement = connection.prepareStatement("DELETE FROM computer WHERE company_id=?");
      statement.setLong(1, id);
      statement.executeUpdate();
    } catch (SQLException e) {
      throw new PersistenceException(e);
    } finally {
      Util.close(statement, connection);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Computer getFromId(Long id) {
    Computer computer = null;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultat = null;
    SQLCompanyDAO companyDAO = new SQLCompanyDAO();

    try {
      connection = Util.getConnection();
      statement = connection
          .prepareStatement("SELECT id, name, introduced, discontinued, company_id FROM computer WHERE id=?");
      statement.setLong(1, id);
      resultat = statement.executeQuery();

      if (resultat.first()) {
        computer = new Computer();
        computer.setId(resultat.getLong(1));
        computer.setName(resultat.getString(2));
        computer.setIntroduced(resultat.getDate(3));
        computer.setDiscontinued(resultat.getDate(4));

        Long companyId = resultat.getLong(5);
        if (companyId != 0) {
          computer.setCompany(companyDAO.getFromId(companyId));
        }
      }
    } catch (SQLException e) {
      throw new PersistenceException(e);
    } finally {
      Util.close(resultat, statement, connection);
    }

    return computer;
  }
}

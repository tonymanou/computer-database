package com.tonymanou.computerdb.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tonymanou.computerdb.entity.Company;
import com.tonymanou.computerdb.entity.Computer;

/**
 * Helper class to make actions on computers in the database.
 *
 * @author tonymanou
 */
public class ComputerDAO {

  /**
   * Retrieve all the computers from the database.
   *
   * @return A list containing all the computers.
   * @throws SQLException
   *           if a database access error occurs
   */
  public List<Computer> findAll() throws SQLException {
    List<Computer> list = new ArrayList<Computer>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultat = null;

    try {
      connection = Util.getConnection();
      statement = connection.createStatement();
      resultat = statement
          .executeQuery("SELECT id, name, introduced, discontinued, company_id FROM computer;");

      CompanyDAO companyDAO = new CompanyDAO();

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
      throw e;
    } finally {
      Util.close(resultat, statement, connection);
    }

    return list;
  }

  /**
   * Create a new computer in the database.
   *
   * @param computer
   *          The computer to create.
   * @throws SQLException
   *           if a database access error occurs
   */
  public void create(Computer computer) throws SQLException {
    Connection connection = null;
    Statement statement = null;

    try {
      connection = Util.getConnection();
      statement = connection.createStatement();

      StringBuilder query = new StringBuilder(
          "INSERT INTO computer (id, name, introduced, discontinued, company_id) VALUES (");
      query.append(computer.getId());
      query.append(",'");
      query.append(computer.getName());
      query.append("','");
      query.append(Util.getTimestamp(computer.getIntroduced()));
      query.append("','");
      query.append(Util.getTimestamp(computer.getDiscontinued()));
      query.append("',");
      Company company = computer.getCompany();
      query.append(company == null ? null : company.getId());
      query.append(");");

      statement.executeUpdate(query.toString());
    } catch (SQLException e) {
      throw e;
    } finally {
      Util.close(statement, connection);
    }
  }

  /**
   * Update a computer in the database.
   *
   * @param computer
   *          The computer to update.
   * @throws SQLException
   *           if a database access error occurs
   */
  public void update(Computer computer) throws SQLException {
    Connection connection = null;
    Statement statement = null;

    try {
      connection = Util.getConnection();
      statement = connection.createStatement();

      StringBuilder query = new StringBuilder("UPDATE computer SET name='");
      query.append(computer.getName());
      query.append("', introduced='");
      query.append(Util.getTimestamp(computer.getIntroduced()));
      query.append("', discontinued='");
      query.append(Util.getTimestamp(computer.getDiscontinued()));
      query.append("', company_id=");
      Company company = computer.getCompany();
      query.append(company == null ? null : company.getId());
      query.append(" WHERE id=");
      query.append(computer.getId());
      query.append(";");

      statement.executeUpdate(query.toString());
    } catch (SQLException e) {
      throw e;
    } finally {
      Util.close(statement, connection);
    }
  }

  /**
   * Delete a computer from the database.
   *
   * @param id
   *          Id of the computer to delete.
   * @throws SQLException
   *           if a database access error occurs
   */
  public void delete(Long id) throws SQLException {
    Connection connection = null;
    Statement statement = null;

    try {
      connection = Util.getConnection();
      statement = connection.createStatement();

      StringBuilder query = new StringBuilder("DELETE FROM computer WHERE company_id=");
      query.append(id);
      query.append(";");

      statement.executeUpdate(query.toString());
    } catch (SQLException e) {
      throw e;
    } finally {
      Util.close(statement, connection);
    }
  }

  /**
   * Retrieve a computer from the database thanks to the given id.
   *
   * @param id
   *          The id of the computer to retrieve.
   * @return The {@link Computer}, or null if no matching company was found.
   * @throws SQLException
   *           if a database access error occurs
   */
  public Computer getFromId(Long id) throws SQLException {
    Computer computer = null;
    Connection connection = null;
    Statement statement = null;
    ResultSet resultat = null;
    CompanyDAO companyDAO = new CompanyDAO();

    try {
      StringBuilder query = new StringBuilder(
          "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE id=");
      query.append(id);
      query.append(";");

      connection = Util.getConnection();
      statement = connection.createStatement();
      resultat = statement.executeQuery(query.toString());

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
      throw e;
    } finally {
      Util.close(resultat, statement, connection);
    }

    return computer;
  }
}

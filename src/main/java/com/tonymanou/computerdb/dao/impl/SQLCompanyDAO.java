package com.tonymanou.computerdb.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tonymanou.computerdb.dao.ICompanyDAO;
import com.tonymanou.computerdb.dao.Util;
import com.tonymanou.computerdb.entity.Company;
import com.tonymanou.computerdb.exception.PersistenceException;

/**
 * Helper class to make actions on companies in the database.
 *
 * @author tonymanou
 */
public class SQLCompanyDAO implements ICompanyDAO {

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Company> findAll() {
    List<Company> list = new ArrayList<Company>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultat = null;

    try {
      connection = Util.getConnection();
      statement = connection.createStatement();
      resultat = statement.executeQuery("SELECT id, name FROM company;");

      while (resultat.next()) {
        Company c = new Company();
        c.setId(resultat.getLong(1));
        c.setName(resultat.getString(2));

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
  public Company getFromId(Long id) {
    Company company = null;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultat = null;

    try {
      connection = Util.getConnection();
      statement = connection.prepareStatement("SELECT id, name FROM company WHERE id=?");
      statement.setLong(1, id);
      resultat = statement.executeQuery();

      if (resultat.first()) {
        company = new Company();
        company.setId(resultat.getLong(1));
        company.setName(resultat.getString(2));
      }
    } catch (SQLException e) {
      throw new PersistenceException(e);
    } finally {
      Util.close(resultat, statement, connection);
    }

    return company;
  }
}

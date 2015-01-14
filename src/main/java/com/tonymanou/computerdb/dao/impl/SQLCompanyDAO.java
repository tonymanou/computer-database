package com.tonymanou.computerdb.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tonymanou.computerdb.dao.ICompanyDAO;
import com.tonymanou.computerdb.entity.Company;
import com.tonymanou.computerdb.exception.PersistenceException;

/**
 * DAO implementation to manage companies in a SQL database.
 *
 * @author tonymanou
 */
public class SQLCompanyDAO implements ICompanyDAO {

  @Override
  public List<Company> findAll() {
    List<Company> list = new ArrayList<Company>();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultat = null;

    try {
      connection = SQLUtil.getConnection();
      statement = connection.createStatement();
      resultat = statement.executeQuery("SELECT id, name FROM company;");

      Company.Builder builder = Company.getBuilder(null);
      while (resultat.next()) {
        // @formatter:off
        list.add(builder
            .setId(resultat.getLong(1))
            .setName(resultat.getString(2))
            .build());
        // @formatter:on
      }
    } catch (SQLException e) {
      throw new PersistenceException(e);
    } finally {
      SQLUtil.close(resultat, statement, connection);
    }

    return list;
  }

  @Override
  public Company getFromId(Long id) {
    Company company = null;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultat = null;

    try {
      connection = SQLUtil.getConnection();
      statement = connection.prepareStatement("SELECT id, name FROM company WHERE id=?;");
      statement.setLong(1, id);
      resultat = statement.executeQuery();

      Company.Builder builder = Company.getBuilder(null);
      if (resultat.first()) {
        // @formatter:off
        company =builder
            .setId(resultat.getLong(1))
            .setName(resultat.getString(2))
            .build();
        // @formatter:off
      }
    } catch (SQLException e) {
      throw new PersistenceException(e);
    } finally {
      SQLUtil.close(resultat, statement, connection);
    }

    return company;
  }
}

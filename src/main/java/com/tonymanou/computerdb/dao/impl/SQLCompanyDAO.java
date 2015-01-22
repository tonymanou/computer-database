package com.tonymanou.computerdb.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tonymanou.computerdb.dao.ICompanyDAO;
import com.tonymanou.computerdb.domain.Company;
import com.tonymanou.computerdb.exception.PersistenceException;

/**
 * DAO implementation to manage companies in a SQL database.
 *
 * @author tonymanou
 */
public class SQLCompanyDAO implements ICompanyDAO {

  private static final Logger LOGGER = LoggerFactory.getLogger(SQLCompanyDAO.class);

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

      while (resultat.next()) {
        // @formatter:off
        list.add(Company.getBuilder(resultat.getString(2))
            .setId(resultat.getLong(1))
            .build());
        // @formatter:on
      }
    } catch (SQLException e) {
      LOGGER.error("SQL exception", e);
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

      if (resultat.first()) {
        // @formatter:off
        company = Company.getBuilder(resultat.getString(2))
            .setId(resultat.getLong(1))
            .build();
        // @formatter:off
      } else {
        StringBuilder sb = new StringBuilder("No company found with the id ").append(id);
        LOGGER.warn(sb.toString());
      }
    } catch (SQLException e) {
      LOGGER.error("SQL exception", e);
      throw new PersistenceException(e);
    } finally {
      SQLUtil.close(resultat, statement, connection);
    }

    return company;
  }
}

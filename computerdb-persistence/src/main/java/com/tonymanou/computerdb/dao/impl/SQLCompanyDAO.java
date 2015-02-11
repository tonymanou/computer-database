package com.tonymanou.computerdb.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tonymanou.computerdb.dao.ICompanyDAO;
import com.tonymanou.computerdb.domain.Company;

/**
 * DAO implementation to manage companies in a SQL database.
 *
 * @author tonymanou
 */
@Repository
public class SQLCompanyDAO extends SQLBaseDAO implements ICompanyDAO {

  private static final String DELETE_BY_ID_QUERY = "DELETE FROM company WHERE id=?;";

  @Override
  @SuppressWarnings("unchecked")
  public List<Company> findAll() {
    // @formatter:off
    return (List<Company>) getSession()
        .createCriteria(Company.class)
        .list();
    // @formatter:on
  }

  @Override
  public Company getFromId(Long id) {
    return (Company) getSession().get(Company.class, id);
  }

  @Override
  public void delete(Long id) {
    // @formatter:off
    getSession()
        .createSQLQuery(DELETE_BY_ID_QUERY)
        .setLong(0, id)
        .executeUpdate();
    // @formatter:on
  }
}

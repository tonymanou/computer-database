package com.tonymanou.computerdb.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.tonymanou.computerdb.dao.ICompanyDAO;
import com.tonymanou.computerdb.model.Company;

/**
 * DAO implementation to manage companies in a SQL database.
 *
 * @author tonymanou
 */
@Repository
public class SQLCompanyDAO implements ICompanyDAO {

  private static final String DELETE_BY_ID_QUERY = "DELETE FROM company WHERE id=?;";

  @Resource(name = "sessionFactory")
  private SessionFactory sessionFactory;

  @Override
  @SuppressWarnings("unchecked")
  public List<Company> findAll() {
    return (List<Company>) sessionFactory.getCurrentSession()
        .createCriteria(Company.class)
        .list();
  }

  @Override
  public Company getFromId(Long id) {
    return (Company) sessionFactory.getCurrentSession()
        .get(Company.class, id);
  }

  @Override
  public void delete(Long id) {
    sessionFactory.getCurrentSession()
        .createSQLQuery(DELETE_BY_ID_QUERY)
        .setLong(0, id)
        .executeUpdate();
  }
}

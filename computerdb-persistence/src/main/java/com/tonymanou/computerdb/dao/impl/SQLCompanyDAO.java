package com.tonymanou.computerdb.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.tonymanou.computerdb.dao.ICompanyDAO;
import com.tonymanou.computerdb.domain.Company;

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

  protected Session getSession() {
    return sessionFactory.getCurrentSession();
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<Company> findAll() {
    return (List<Company>) getSession()
        .createCriteria(Company.class)
        .list();
  }

  @Override
  public Company getFromId(Long id) {
    return (Company) getSession().get(Company.class, id);
  }

  @Override
  public void delete(Long id) {
    getSession()
        .createSQLQuery(DELETE_BY_ID_QUERY)
        .setLong(0, id)
        .executeUpdate();
  }
}

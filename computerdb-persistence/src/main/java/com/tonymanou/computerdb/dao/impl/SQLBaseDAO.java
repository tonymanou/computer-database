package com.tonymanou.computerdb.dao.impl;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Base DAO class providing Hibernate sessions.
 * 
 * @author tonymanou
 */
public abstract class SQLBaseDAO {

  @Resource(name = "sessionFactory")
  private SessionFactory sessionFactory;

  protected Session getSession() {
    return sessionFactory.getCurrentSession();
  }
}

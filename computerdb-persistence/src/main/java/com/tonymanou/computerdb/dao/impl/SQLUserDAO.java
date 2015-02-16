package com.tonymanou.computerdb.dao.impl;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.tonymanou.computerdb.dao.IUserDAO;
import com.tonymanou.computerdb.model.User;

@Repository
public class SQLUserDAO implements IUserDAO {

  @Autowired
  @Qualifier("sessionFactory")
  private SessionFactory sessionFactory;

  @Override
  public User findByName(String name) {
    return (User) sessionFactory.getCurrentSession()
        .createCriteria(User.class)
        .add(Restrictions.eq("name", name))
        .uniqueResult();
  }
}

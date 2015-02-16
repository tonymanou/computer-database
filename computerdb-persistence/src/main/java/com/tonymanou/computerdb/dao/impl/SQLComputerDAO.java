package com.tonymanou.computerdb.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.tonymanou.computerdb.dao.IComputerDAO;
import com.tonymanou.computerdb.model.Computer;
import com.tonymanou.computerdb.pagination.ComputerPage;
import com.tonymanou.computerdb.util.Util;

/**
 * DAO implementation to manage computers in a SQL database.
 *
 * @author tonymanou
 */
@Repository
public class SQLComputerDAO implements IComputerDAO {

  private static final String COMPUTER_ALIAS = "c";
  private static final String COMPANY_ALIAS = "d";

  private static final String COMPUTER_NAME_FIELD = COMPUTER_ALIAS + ".name";
  private static final String COMPANY_NAME_FIELD = COMPANY_ALIAS + ".name";
  private static final String COMPUTER_ID_FIELD = COMPUTER_ALIAS + ".id";

  private static final String DELETE_BY_ID_QUERY = "DELETE FROM computer WHERE id=?;";
  private static final String DELETE_BY_COMPANY_QUERY = "DELETE FROM computer WHERE company_id=?;";

  @Autowired
  @Qualifier("sessionFactory")
  private SessionFactory sessionFactory;

  @SuppressWarnings("unchecked")
  @Override
  public List<Computer> findAll() {
    return sessionFactory.getCurrentSession()
        .createCriteria(Computer.class)
        .list();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Computer> findPage(ComputerPage.Builder pageBuilder) {
    Session session = sessionFactory.getCurrentSession();

    String searchString = pageBuilder.getSearchQuery();
    boolean searching = !Util.isStringEmpty(searchString);
    String search;
    if (searching) {
      search = new StringBuilder("%").append(searchString).append("%").toString();
    } else {
      search = null;
    }

    /* ========== First query: element count ========== */

    Criteria computerCount = session.createCriteria(Computer.class, COMPUTER_ALIAS)
        .setProjection(Projections.rowCount());

    if (searching) {
      computerCount
          .createCriteria("company", COMPANY_ALIAS, JoinType.LEFT_OUTER_JOIN)
          .add(Restrictions.or(
              Restrictions.like(COMPUTER_NAME_FIELD, search),
              Restrictions.like(COMPANY_NAME_FIELD, search))
          );
    }

    Long count = (Long) computerCount.uniqueResult();
    if (count == null) {
      count = 0L;
    }
    pageBuilder.setNumElements(count);

    /* ========== Second query: computer list ========== */

    // Prepare parameters
    ComputerPage page = pageBuilder.build();
    String order;
    switch (page.getOrder()) {
    case NAME:
      order = COMPUTER_NAME_FIELD;
      break;
    case COMPANY:
      order = COMPANY_NAME_FIELD;
      break;
    default:
      order = COMPUTER_ID_FIELD;
      break;
    }

    Order orderType;
    switch (page.getOrderType()) {
    case DESC:
      orderType = Order.desc(order);
      break;
    default:
      orderType = Order.asc(order);
      break;
    }

    // Create query
    Criteria computerList = session.createCriteria(Computer.class, COMPUTER_ALIAS);

    if (searching) {
      computerList
          .createCriteria("company", COMPANY_ALIAS, JoinType.LEFT_OUTER_JOIN)
          .add(Restrictions.or(
              Restrictions.like(COMPUTER_NAME_FIELD, search),
              Restrictions.like(COMPANY_NAME_FIELD, search))
          );
    }

    return computerList.addOrder(orderType)
        .setFirstResult(page.getElementsOffset())
        .setMaxResults(page.getNumElementsPerPage())
        .list();
  }

  @Override
  public void create(Computer computer) {
    sessionFactory.getCurrentSession()
        .save(computer);
  }

  @Override
  public void update(Computer computer) {
    sessionFactory.getCurrentSession()
        .update(computer);
  }

  @Override
  public void delete(Long id) {
    sessionFactory.getCurrentSession()
        .createSQLQuery(DELETE_BY_ID_QUERY)
        .setLong(0, id)
        .executeUpdate();
  }

  @Override
  public void deleteAllWithCompanyId(Long companyId) {
    sessionFactory.getCurrentSession()
        .createSQLQuery(DELETE_BY_COMPANY_QUERY)
        .setLong(0, companyId)
        .executeUpdate();
  }

  @Override
  public Computer getFromId(Long id) {
    return (Computer) sessionFactory.getCurrentSession()
        .get(Computer.class, id);
  }
}

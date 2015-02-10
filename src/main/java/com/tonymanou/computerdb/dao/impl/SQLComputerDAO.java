package com.tonymanou.computerdb.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.tonymanou.computerdb.dao.IComputerDAO;
import com.tonymanou.computerdb.domain.Computer;
import com.tonymanou.computerdb.exception.PersistenceException;
import com.tonymanou.computerdb.pagination.ComputerPage;
import com.tonymanou.computerdb.util.Util;

/**
 * DAO implementation to manage computers in a SQL database.
 *
 * @author tonymanou
 */
@Repository
public class SQLComputerDAO extends SQLBaseDAO implements IComputerDAO {

  private static final String COMPUTER_ALIAS = "c";
  private static final String COMPANY_ALIAS = "d";

  private static final String COMPUTER_NAME_FIELD = COMPUTER_ALIAS + ".name";
  private static final String COMPANY_NAME_FIELD = COMPANY_ALIAS + ".name";
  private static final String COMPUTER_ID_FIELD = COMPUTER_ALIAS + ".id";

  private static final String DELETE_BY_ID_QUERY = "DELETE FROM computer WHERE id=?;";
  private static final String DELETE_BY_COMPANY_QUERY = "DELETE FROM computer WHERE company_id=?;";

  private static final Logger LOGGER = LoggerFactory.getLogger(SQLComputerDAO.class);

  @SuppressWarnings("unchecked")
  @Override
  public List<Computer> findAll(ComputerPage.Builder pageBuilder) {
    Session session = getSession();

    String searchString = pageBuilder.getSearchQuery();
    boolean searching = !Util.isStringEmpty(searchString);
    String search;
    if (searching) {
      search = new StringBuilder("%").append(searchString).append("%").toString();
    } else {
      search = null;
    }

    /* ========== First query: element count ========== */

    // Create query
    Criteria c1 = session.createCriteria(Computer.class, "c").setProjection(Projections.rowCount());

    if (searching) {
      // @formatter:off
      c1.createCriteria("company", COMPANY_ALIAS, JoinType.LEFT_OUTER_JOIN)
          .add(Restrictions.or(
              Restrictions.like(COMPUTER_NAME_FIELD, search),
              Restrictions.like(COMPANY_NAME_FIELD, search)));
      // @formatter:on
    }

    Long count = (Long) c1.uniqueResult();
    if (count == null) {
      RuntimeException e = new PersistenceException("Computer count is null");
      LOGGER.error("Error while getting the number of computers matching the search query", e);
      throw e;
    } else {
      pageBuilder.setNumElements(count);
    }

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
    Criteria c2 = session.createCriteria(Computer.class, COMPUTER_ALIAS);

    if (searching) {
      // @formatter:off
      c2.createCriteria("company", COMPANY_ALIAS, JoinType.LEFT_OUTER_JOIN)
          .add(Restrictions.or(
              Restrictions.like(COMPUTER_NAME_FIELD, search),
              Restrictions.like(COMPANY_NAME_FIELD, search)));
      // @formatter:on
    }

    // @formatter:off
    return c2.addOrder(orderType)
        .setFirstResult(page.getElementsOffset())
        .setMaxResults(page.getNumElementsPerPage())
        .list();
    // @formatter:on
  }

  @Override
  public void create(Computer computer) {
    getSession().save(computer);
  }

  @Override
  public void update(Computer computer) {
    getSession().update(computer);
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

  @Override
  public void deleteAllWithCompanyId(Long companyId) {
    // @formatter:off
    getSession()
        .createSQLQuery(DELETE_BY_COMPANY_QUERY)
        .setLong(0, companyId)
        .executeUpdate();
    // @formatter:on
  }

  @Override
  public Computer getFromId(Long id) {
    return (Computer) getSession().get(Computer.class, id);
  }
}

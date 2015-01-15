package com.tonymanou.computerdb.dao.impl;

import java.io.FileInputStream;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;

public class SQLDAOTest extends DBTestCase {

  public SQLDAOTest() {
    this(null);
  }

  public SQLDAOTest(String name) {
    super(name);
    System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS,
        "com.mysql.jdbc.Driver");
    System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL,
        "jdbc:mysql://localhost:3306/computer-database-db-test?zeroDateTimeBehavior=convertToNull");
    System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "admincdb");
    System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "qwerty1234");
    // System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA, "" );
  }

  @Override
  protected IDataSet getDataSet() throws Exception {
    return new FlatXmlDataSetBuilder().build(new FileInputStream("dataset.xml"));
  }
}

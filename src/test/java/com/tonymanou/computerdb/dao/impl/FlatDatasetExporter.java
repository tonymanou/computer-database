package com.tonymanou.computerdb.dao.impl;

import java.io.FileOutputStream;
import java.sql.Connection;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

public class FlatDatasetExporter {

  // @org.junit.Test
  public void doExportDatabase() {
    try {
      Connection jdbcConnection = SQLUtil.getConnection();
      IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);

      /* full database export */
      IDataSet fullDataSet = connection.createDataSet();
      FlatXmlDataSet.write(fullDataSet, new FileOutputStream("dataset.xml"));

      jdbcConnection.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

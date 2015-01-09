package com.tonymanou.computerdb.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tonymanou.computerdb.entity.Company;
import com.tonymanou.computerdb.persistence.SQLUtil;

public class CompanyDAO {

	public List<Company> findAll() throws SQLException {
		List<Company> list = new ArrayList<Company>();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultat = null;

		try {
			connection = SQLUtil.getConnection();
			statement = connection.createStatement();
			resultat = statement.executeQuery("SELECT id, name FROM company;");

			while (resultat.next()) {
				Company c = new Company();
				c.setId(resultat.getLong(1));
				c.setName(resultat.getString(2));

				list.add(c);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (resultat != null) {
				try {
					resultat.close();
				} catch (SQLException e) {
					// Ignored
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					// Ignored
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// Ignored
				}
			}
		}

		return list;
	}

	public static Company getFromId(Long id) throws SQLException {
		Company company = null;
		Connection connection = null;
		Statement statement = null;
		ResultSet resultat = null;

		try {
			StringBuilder query = new StringBuilder(
					"SELECT id, name FROM company WHERE id=");
			query.append(id);
			query.append(";");

			connection = SQLUtil.getConnection();
			statement = connection.createStatement();
			resultat = statement.executeQuery(query.toString());

			if (resultat.first()) {
				company = new Company();
				company.setId(resultat.getLong(1));
				company.setName(resultat.getString(2));
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (resultat != null) {
				try {
					resultat.close();
				} catch (SQLException e) {
					// Ignored
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					// Ignored
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// Ignored
				}
			}
		}

		return company;
	}
}

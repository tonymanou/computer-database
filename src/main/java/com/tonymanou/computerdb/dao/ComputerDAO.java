package com.tonymanou.computerdb.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tonymanou.computerdb.entity.Company;
import com.tonymanou.computerdb.entity.Computer;
import com.tonymanou.computerdb.persistence.SQLUtil;

public class ComputerDAO {

	public List<Computer> findAll() throws SQLException {
		List<Computer> list = new ArrayList<Computer>();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultat = null;

		try {
			connection = SQLUtil.getConnection();
			statement = connection.createStatement();
			resultat = statement
					.executeQuery("SELECT id, name, introduced, discontinued, company_id FROM computer;");

			while (resultat.next()) {
				Computer c = new Computer();
				c.setId(resultat.getLong(1));
				c.setName(resultat.getString(2));
				c.setIntroduced(resultat.getDate(3));
				c.setDiscontinued(resultat.getDate(4));

				Long companyId = resultat.getLong(5);
				c.setCompany(CompanyDAO.getFromId(companyId));

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

	public void create(Computer computer) throws SQLException {
		Connection connection = null;
		Statement statement = null;

		try {
			connection = SQLUtil.getConnection();
			statement = connection.createStatement();

			StringBuilder query = new StringBuilder(
					"INSERT INTO computer (id, name, introduced, discontinued, company_id) VALUES (");
			query.append(computer.getId());
			query.append(",'");
			query.append(computer.getName());
			query.append("',");
			query.append(computer.getIntroduced());
			query.append(",");
			query.append(computer.getDiscontinued());
			query.append(",");
			Company company = computer.getCompany();
			query.append(company == null ? null : company.getId());
			query.append(");");

			statement.executeUpdate(query.toString());
		} catch (SQLException e) {
			throw e;
		} finally {
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
	}

	public void update(Computer computer) throws SQLException {
		Connection connection = null;
		Statement statement = null;

		try {
			connection = SQLUtil.getConnection();
			statement = connection.createStatement();

			StringBuilder query = new StringBuilder(
					"UPDATE computer SET name='");
			query.append(computer.getName());
			query.append("', introduced=");
			query.append(computer.getIntroduced());
			query.append(", discontinued=");
			query.append(computer.getDiscontinued());
			query.append(", company_id=");
			Company company = computer.getCompany();
			query.append(company == null ? null : company.getId());
			query.append(" WHERE id=");
			query.append(computer.getId());
			query.append(";");

			statement.executeUpdate(query.toString());
		} catch (SQLException e) {
			throw e;
		} finally {
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
	}

	public void delete(Computer computer) throws SQLException {
		Connection connection = null;
		Statement statement = null;

		try {
			connection = SQLUtil.getConnection();
			statement = connection.createStatement();

			StringBuilder query = new StringBuilder(
					"DELETE FROM computer WHERE company_id=");
			query.append(computer.getId());
			query.append(";");

			statement.executeUpdate(query.toString());
		} catch (SQLException e) {
			throw e;
		} finally {
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
	}
}

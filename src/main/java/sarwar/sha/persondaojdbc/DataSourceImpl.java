package sarwar.sha.persondaojdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * implementation of DAO interface using Spring DataSource
 * 
 * Person framework
 * 
 * @author Sarwar.Sha-Mohammad
 */
import sarwar.sha.domain.Person;

public class DataSourceImpl implements DaoInterface {

	private static final Logger logger = LoggerFactory.getLogger(DataSourceImpl.class);

	private DataSource dataSource;

	private StringBuilder builder;

	private Person person;

	@Autowired
	public void setDataSource(DataSource dataSource) {

		this.dataSource = dataSource;

	}

	@Override
	public Person getById(long id) {

		person = new Person();
		String query = "SELECT * FROM person WHERE id = ?;";

		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setLong(1, id);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				person.setId(rs.getLong("id"));
				person.setFirstName(rs.getString("first_name"));
				person.setLastName(rs.getString("last_name"));
				person.setPhoneNumber(rs.getString("phone_number"));
				person.setEmailAddress(rs.getString("email_address"));
				if (rs.getDate("start_date") != null) {
					person.setStartDate(rs.getDate("start_date"));
				}
				if (rs.getDate("end_date") != null) {
					person.setEndDate(rs.getDate("end_date"));
				}
				person.setLogin(rs.getString("login"));
				person.setPassword(rs.getString("password"));
			}
			rs.close();
			statement.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					logger.error("closing connection failure");
					logger.error(e.getMessage());
				}
			}
		}
		return person;
	}

	@Override
	public void create(Person person) {

		builder = new StringBuilder();
		String query = builder.append("INSERT INTO person")
				.append(" (id, first_name, last_name, phone_number, email_address,")
				.append(" start_date, end_date, login, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);").toString();
		Connection conn = null;

		try {

			conn = dataSource.getConnection();
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setLong(1, person.getId());
			statement.setString(2, person.getFirstName());
			statement.setString(3, person.getLastName());
			statement.setString(4, person.getPhoneNumber());
			statement.setString(5, person.getEmailAddress());
			if (person.getStartDate() != null) {
				statement.setDate(6, (Date) person.getStartDate());
			} else
				statement.setDate(6, null);
			if (person.getEndDate() != null) {
				statement.setDate(7, (Date) person.getEndDate());
			} else
				statement.setDate(7, null);
			statement.setString(8, person.getLogin());
			statement.setString(9, person.getPassword());
			statement.executeUpdate();
			statement.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					logger.error("closing connection failure");
					logger.error(e.getMessage());
				}
			}
		}
	}

	@Override
	public void delete(Person person) {

		String query = "DELETE FROM person WHERE id=?";
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(query);
			statement.setLong(1, person.getId());
			statement.execute();
		} catch (SQLException e) {
			logger.error("closing connection or closing statement failure");
			logger.error(e.getMessage());
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				logger.error("closing connection or closing statement failure.");
				logger.error(e.getMessage());
			}
		}
	}

}

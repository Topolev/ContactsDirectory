package by.topolev.contacts.dao;

import static by.topolev.contacts.config.ConfigUtil.PASSWORD_DATABASE;
import static by.topolev.contacts.config.ConfigUtil.URL_DATABASE;
import static by.topolev.contacts.config.ConfigUtil.USER_DATABASE;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDaoJDBC {
	private static final Logger LOG = LoggerFactory.getLogger(ContactDaoJDBC.class);
	
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			LOG.error("Can not upload JDBC driver");
		}
	}
	
	protected Connection getConnection() throws SQLException{
		return  DriverManager.getConnection(URL_DATABASE, USER_DATABASE, PASSWORD_DATABASE);
	}
	
	protected void closeConnection(Connection connection){
		if (connection == null) return;
		try {
			connection.close();
		} catch (SQLException e) {
			LOG.error("Problem with closing current connection with DB", e);
		}
	}
}

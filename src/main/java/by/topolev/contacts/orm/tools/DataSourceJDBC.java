package by.topolev.contacts.orm.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSourceJDBC implements DataSource {
	
	private static final Logger LOG = LoggerFactory.getLogger(DataSourceJDBC.class);
	
	private String driverClassName;
	private String url;
	private String username;
	private String password;
	
	@Override
	public Connection getConnection() throws SQLException {
		try {
			Class.forName(driverClassName);
		} catch (ClassNotFoundException e) {
			throw new SQLException("JDBC driver isn't found", e);
		}
		return DriverManager.getConnection(url, username, password);
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

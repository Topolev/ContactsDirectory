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

    public DataSourceJDBC(String driverClassName, String url, String username, String password) {
        this.driverClassName = driverClassName;

        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            LOG.error("JDBC driver isn't found");
            throw new RuntimeException(e);
        }

        this.url = url;
        this.username = username;
        this.password = password;
    }


    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}

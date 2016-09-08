package by.topolev.contacts.orm.tools;

import java.sql.Connection;
import java.sql.SQLDataException;
import java.sql.SQLException;

public interface DataSource {
	Connection getConnection() throws SQLException;
}

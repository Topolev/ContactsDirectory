package by.topolev.contacts.orm.tools;

import by.topolev.contacts.config.ConfigUtil;

import java.sql.SQLException;

import static by.topolev.contacts.config.ConfigUtil.*;
public class DataSourceFactory {



	public static DataSource getDataSource()  {

		DataSourceJDBC dataSource = new DataSourceJDBC(
				ConfigUtil.getJdbcDriver(),
				ConfigUtil.getDatebaseUrl(),
				ConfigUtil.getDatebaseUser(),
				ConfigUtil.getDatebasePassword());
		return dataSource;
	}
}

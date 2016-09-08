package by.topolev.contacts.orm.tools;

import static by.topolev.contacts.config.ConfigUtil.*;
public class DataSourceFactory {
	public static DataSource getDataSource(){
		DataSourceJDBC dataSource = new DataSourceJDBC();
		dataSource.setDriverClassName(JDBC_DRIVER);
		dataSource.setUrl(URL_DATABASE);
		dataSource.setUsername(USER_DATABASE);
		dataSource.setPassword(PASSWORD_DATABASE);
		return dataSource;
	}
}

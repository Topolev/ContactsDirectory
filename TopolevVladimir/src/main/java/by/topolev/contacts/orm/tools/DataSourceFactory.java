package by.topolev.contacts.orm.tools;

import by.topolev.contacts.config.ConfigUtil;

import static by.topolev.contacts.config.ConfigUtil.*;
public class DataSourceFactory {



	public static DataSource getDataSource(){
		DataSourceJDBC dataSource = new DataSourceJDBC();
		dataSource.setDriverClassName(ConfigUtil.getJdbcDriver());
		dataSource.setUrl(ConfigUtil.getDatebaseUrl());
		dataSource.setUsername(ConfigUtil.getDatebaseUser());
		dataSource.setPassword(ConfigUtil.getDatebasePassword());
		return dataSource;
	}
}

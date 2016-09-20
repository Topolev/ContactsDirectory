package by.topolev.contacts.config;

import by.topolev.contacts.servlets.frontcontroller.FrontControllerFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Properties;

public class ConfigUtil {

	private static final Logger LOG = LoggerFactory.getLogger(ConfigUtil.class);

	public static final String DATABASE_URL = "database.url";
	public static final String DATABASE_DRIVER = "database.driver";
	public static final String DATABASE_USER = "database.user";
	public static final String DATABASE_PASSWORD = "database.password";
	public static final String PATH_UPLOAD_PROFILE_IMAGE = "path.upload.profile.image";
	public static final String PATH_UPLOAD_PROFILE_FILES = "path.upload.profile.files";
	public static final String GMAIL_USERNAME = "gmail.username";
	public static final String GMAIL_PASSWORD = "gmail.password";

	private static Properties properties;

	public static void init(ServletContext servletContext) throws ServletException {
		properties = new Properties();

		try {
			properties.load(servletContext.getResourceAsStream("/resources/application.properties"));
		} catch (IOException e) {
			LOG.error("Can not start up application because configuration properties are not initialized.");
			throw new ServletException(e);
		}
	}

	public static String getJdbcDriver(){
		return properties.getProperty(DATABASE_DRIVER);
	}

	public static String getDatebaseUrl(){
		return properties.getProperty(DATABASE_URL);
	}

	public static String getDatebaseUser(){
		return properties.getProperty(DATABASE_USER);
	}

	public static String getDatebasePassword(){
		return properties.getProperty(DATABASE_PASSWORD);
	}

	public static String getPathUploadProfileImage(){
		return properties.getProperty(PATH_UPLOAD_PROFILE_IMAGE);
	}

	public static String getPathUploadProfileFiles(){
		return properties.getProperty(PATH_UPLOAD_PROFILE_FILES);
	}

	public static String getGmailUsername(){
		return properties.getProperty(GMAIL_USERNAME);
	}

	public static String getGmailPassword(){
		return properties.getProperty(GMAIL_PASSWORD);
	}


}

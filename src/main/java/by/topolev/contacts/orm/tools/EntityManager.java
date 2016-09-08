package by.topolev.contacts.orm.tools;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.topolev.contacts.entity.Contact;
import by.topolev.contacts.orm.annotation.Column;
import by.topolev.contacts.orm.annotation.Id;
import by.topolev.contacts.orm.annotation.OneToOne;
import by.topolev.contacts.orm.annotation.Table;

public class EntityManager {

	private final static Logger LOG = LoggerFactory.getLogger(EntityManager.class);

	DataSource dataSource = DataSourceFactory.getDataSource();

	public EntityManager(){
		System.out.println("Entity manager");
	}
	
	public <T> List<T> getListEntity(String query, Class<T> clazz) {
		List<T> entityList = new ArrayList<T>();
		Connection connection = null;

		try {
			connection = dataSource.getConnection();
			PreparedStatement statment = connection.prepareStatement(query);
			ResultSet result = statment.executeQuery();
			while (result.next()) {
				entityList.add(getEntityFromResultSet(result, clazz));
			}
		} catch (SQLException e) {
			LOG.debug("Problem with getting of entity list", e);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOG.debug("Can not close connection", e);
			}
		}

		return entityList;
	}

	public <T> T getEntity(String query, Class<T> clazz) {
		T entity = null;
		Connection connection = null;

		try {
			connection = dataSource.getConnection();
			PreparedStatement statment = connection.prepareStatement(query);
			ResultSet result = statment.executeQuery();
			if (!result.next())
				return null;
			entity = getEntityFromResultSet(result, clazz);
		} catch (SQLException e) {
			LOG.debug("Problem with getting of entity list", e);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOG.debug("Can not close connection", e);
			}
		}

		return entity;
	}
	
	public <T> void deleteEntity(Class<T> clazz, int... idList){
		StringBuilder query = new StringBuilder();
		Table table = clazz.getAnnotation(Table.class);
		query.append("DELETE FROM " + table.name() + " WHERE");
		for (int id : idList){
			query.append(" id = " + id + " OR");
		}
		query.delete(query.length()-3, query.length());
		
		Connection connection = null;
		try{
			connection = dataSource.getConnection();
			connection.createStatement().executeUpdate(query.toString());
		} catch(SQLException e){
			LOG.debug("Problem with delete", e);
		} finally{
			try {
				connection.close();
			} catch (SQLException e) {
				LOG.debug("Can not close connection", e);
			}
		}
		
		System.out.println(query);
	}
	
	public int getCountAllEntity(Class<?> clazz){
		Connection connection = null;
		Table table = clazz.getAnnotation(Table.class);
		System.out.println(table.name());
		try{
			connection = dataSource.getConnection();
			PreparedStatement statment = connection.prepareStatement("SELECT COUNT(*) AS count FROM " + table.name());
			ResultSet result = statment.executeQuery();
			result.next();
			return result.getInt("count");
		} catch(SQLException e){
			LOG.debug("Problem with getting number of row", e);
		} finally{
			try {
				connection.close();
			} catch (SQLException e) {
				LOG.debug("Can not close connection", e);
			}
		}
		return 0;
	}
	
	private <T> T getEntityFromResultSet(ResultSet result, Class<T> clazz) {
		try {
			T entity = clazz.newInstance();
			Table table = clazz.getAnnotation(Table.class);
			long entity_id = -1;
			
			Field[] classFields = clazz.getDeclaredFields();
			

			for (Field field : classFields) {
				Column column = field.getAnnotation(Column.class);
				if (column != null) {
					setValueField(entity, field, result.getObject(column.name()));
				}
				Id id = field.getAnnotation(Id.class);
				if (id != null) {
					entity_id = Integer.valueOf(result.getObject("id").toString());
					setValueField(entity, field, result.getObject("id"));
				}
			}

			for (Field field : classFields) {
				OneToOne oneToOne = field.getAnnotation(OneToOne.class);
				if (oneToOne != null) {
					try {
						Class clazzOneToOne = Class.forName(oneToOne.clazz());
						if (entity_id != -1) {
							Object connectedObject = getEntity(
									String.format("SELECT * FROM %s WHERE %s=%d", oneToOne.table(), table.name() + "_id", entity_id),
									clazzOneToOne);
							setValueField(entity, field, connectedObject);
						}
					} catch (ClassNotFoundException e) {
						LOG.debug("Invalid className for entity field {}", field.getName(), e);
					}
				}
			}
			return entity;

		} catch (InstantiationException | IllegalAccessException | SQLException e) {
			LOG.info("Problem with entity getting", e);
		}
		return null;
	}

	private void setValueField(Object object, Field field, Object value) {
		field.setAccessible(true);
		try {
			field.set(object, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			LOG.debug("Assigning variable '{}' for field '{}' in class '{}' passed with error", value, field.getName(),
					object.getClass().getName(), e);
		}
		field.setAccessible(false);
	}

	public static void main(String[] arg) {
		EntityManager em = new EntityManager();
		em.deleteEntity(Contact.class, 1,2);
		
		Contact contact = em.getEntity("SELECT * FROM contact", Contact.class);
		List<Contact> contacts = em.getListEntity("SELECT * FROM contact", Contact.class);
		System.out.println(contacts);
		for (Contact c : contacts) {
			System.out.println(c.getAddress());
		}

	}

}

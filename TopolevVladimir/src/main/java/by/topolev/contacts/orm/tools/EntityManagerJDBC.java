package by.topolev.contacts.orm.tools;

import by.topolev.contacts.entity.Address;
import by.topolev.contacts.entity.Attachment;
import by.topolev.contacts.entity.Contact;
import by.topolev.contacts.entity.Phone;
import by.topolev.contacts.orm.annotation.Column;
import by.topolev.contacts.orm.annotation.OneToMany;
import by.topolev.contacts.orm.annotation.OneToOne;
import by.topolev.contacts.orm.annotation.Table;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class EntityManagerJDBC implements EntityManager {

    private final static Logger LOG = LoggerFactory.getLogger(EntityManagerJDBC.class);
    public static final String CAN_NOT_CLOSE_CONNECTION = "Can not close connection";

    private DataSource dataSource;

    private static volatile EntityManager instance;

    private Map<Class<?>, MetaEntity> metaEntityList = new HashMap<>();
    private List<Class<?>> classEntity = new ArrayList<>();
    private MetaEntityReader metaEntityReader = new MetaEntityReader();

    private EntityManagerJDBC() {
        LOG.debug("Init Entity Manager for JDBC");

        dataSource = DataSourceFactory.getDataSource();
        classEntity.add(Contact.class);
        classEntity.add(Address.class);
        classEntity.add(Phone.class);
        classEntity.add(Attachment.class);

        metaEntityList = metaEntityReader.getMetaEntity(classEntity);
    }

    public static EntityManager getEntityManager() {
        EntityManager localInstance = instance;
        if (localInstance == null) {
            synchronized (EntityManagerJDBC.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new EntityManagerJDBC();
                }
            }
        }
        return localInstance;
    }

    @Override
    public <T> void updateEntity(T entity) {
        MetaEntity metaEntity = metaEntityList.get(entity.getClass());
        Integer id = getIdEntity(entity);
        StringBuilder query = new StringBuilder();

        if (id == null) {
            query.append(String.format("INSERT INTO %s SET ", metaEntity.getTable().name()))
                    .append(getSetSectionQuery(entity));
            id = executeQueryInsertUpdateRow(query.toString());
        } else {
            query.append(String.format("UPDATE %s SET ", metaEntity.getTable().name()))
                    .append(getSetSectionQuery(entity))
                    .append(" WHERE id=")
                    .append(id);
            executeQueryInsertUpdateRow(query.toString());
        }

        LOG.debug(query.toString());

        if (id != null) {

            for (Map.Entry<Field, OneToOne> entry : metaEntity.getFieldsOneToOne().entrySet()) {
                Object currentObject = getValueField(entity, entry.getKey());
                Field foreignkey = getFieldByName(currentObject, entry.getValue().foreignkey());
                setValueField(currentObject, foreignkey, id);
                updateEntity(currentObject);
            }

            for (Map.Entry<Field, OneToMany> entry : metaEntity.getFieldsOneToMany().entrySet()) {
                List listOfEntity = (List) getValueField(entity, entry.getKey());

                if (CollectionUtils.isNotEmpty(listOfEntity)) {
                    for (Object currentObject : listOfEntity) {
                        Field foreignkey = getFieldByName(currentObject, entry.getValue().foreignkey());
                        setValueField(currentObject, foreignkey, id);
                        updateEntity(currentObject);
                    }
                }
            }

        }

    }

    @Override
    public <T> List<T> getListEntity(String query, Class<T> clazz) {
        List<T> entityList = new ArrayList<T>();
        Connection connection = null;
        PreparedStatement statment = null;

        try {
            connection = dataSource.getConnection();
            statment = connection.prepareStatement(query);
            ResultSet result = statment.executeQuery();
            while (result.next()) {
                entityList.add(getEntityFromResultSet(result, clazz));
            }
        } catch (SQLException e) {
            LOG.debug("Problem with getting of entity list", e);
        } finally {
            closeConnection(connection, statment);
        }

        return entityList;
    }

    private void closeConnection(Connection connection, Statement statment) {
        try {
            if (connection != null) {
                connection.close();
            }
            if (statment != null) {
                statment.close();
            }
        } catch (SQLException e) {
            LOG.debug(CAN_NOT_CLOSE_CONNECTION, e);
        }
    }



    @Override
    public <T> T getEntity(String query, Class<T> clazz) {
        T entity = null;
        Connection connection = null;
        PreparedStatement statment = null;

        try {
            connection = dataSource.getConnection();
            statment = connection.prepareStatement(query);
            ResultSet result = statment.executeQuery();
            if (!result.next())
                return null;
            entity = getEntityFromResultSet(result, clazz);
        } catch (SQLException e) {
            LOG.debug("Problem with getting of entity list", e);
        } finally {
            closeConnection(connection, statment);
        }
        return entity;
    }


    @Override
    public <T> List<T> getEntitiesById(Class<T> clazz, Integer... idList) {

        if (ArrayUtils.isEmpty(idList)) return null;

        StringBuilder query = new StringBuilder();
        MetaEntity metaEntity = metaEntityList.get(clazz);

        query.append("SELECT * FROM " + metaEntity.getTable().name() + " WHERE");
        query.append(getSectionQueryIdConnectedWithOr(idList));

        return getListEntity(query.toString(), clazz);
    }

    @Override
    public <T> void deleteEntity(Class<T> clazz, Integer... idList) {
        if (ArrayUtils.isEmpty(idList)) return;

        StringBuilder query = new StringBuilder();
        MetaEntity metaEntity = metaEntityList.get(clazz);

        query.append("DELETE FROM " + metaEntity.getTable().name() + " WHERE");
        query.append(getSectionQueryIdConnectedWithOr(idList));

        Connection connection = null;
        Statement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(query.toString());
        } catch (SQLException e) {
            LOG.debug("Problem with delete", e);
        } finally {
            closeConnection(connection, statement);
        }

        LOG.debug("Complete query: {}", query);
    }

    @Override
    public int getCountRows(String query, Class<?> clazz){
        Table table = clazz.getAnnotation(Table.class);
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            result.next();
            return result.getInt(1);
        } catch(SQLException e){
            LOG.debug("Can get numbers of rows with query {}", query, e);
        }
        finally{
            closeConnection(connection, statement);
        }
        return 0;
    }

    @Override
    public int getCountAllEntity(Class<?> clazz) {
        Table table = clazz.getAnnotation(Table.class);
        return getCountRows("SELECT COUNT(*) FROM " + table.name(), clazz);
    }


    private String getSectionQueryIdConnectedWithOr(Integer... idList) {
        StringBuilder query = new StringBuilder();
        for (Integer id : idList) {
            query.append(" id = " + id + " OR");
        }
        query.delete(query.length() - 3, query.length());
        return query.toString();
    }

    private String getStringValue(Object obj, Field field) {

        field.setAccessible(true);
        Object value;
        try {
            value = field.get(obj);
            field.setAccessible(false);
            if (value == null)
                return "null";
            if (field.getType() == String.class) {
                return "'" + value.toString() + "'";
            }
            if (field.getType() == Date.class) {
                DateFormat format = new SimpleDateFormat("yyyy-dd-mm");
                return "'" + format.format((Date) value) + "'";
            }
            return value.toString();
        } catch (IllegalArgumentException | IllegalAccessException e) {
            return "null";
        }
    }

    private <T> Integer getIdEntity(T entity) {
        MetaEntity metaEntity = metaEntityList.get(entity.getClass());
        Field idField = metaEntity.getIdField();
        return (Integer) getValueField(entity, idField);
    }


    private <T> String getSetSectionQuery(T entity) {
        MetaEntity metaEntity = metaEntityList.get(entity.getClass());
        StringBuilder query = new StringBuilder();
        for (Map.Entry<Field, Column> entry : metaEntity.getFieldsColumn().entrySet()) {
            query.append(String.format(" %s = %s ,", entry.getValue().name(), getStringValue(entity, entry.getKey())));
        }
        query.delete(query.length() - 1, query.length()); // delete the last comma
        return query.toString();
    }

    private Integer executeQueryInsertUpdateRow(String query) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            LOG.debug("Can not insert or update row. Query: {}", query);
        } finally {
            closeConnection(connection, statement);
        }
        return null;
    }

    private Field getFieldByName(Object obj, String fieldName) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) return field;
        }
        return null;
    }


    private <T> T getEntityFromResultSet(ResultSet result, Class<T> clazz) {
        try {
            T entity = clazz.newInstance();
            MetaEntity metaEntity = metaEntityList.get(clazz);

            for (Map.Entry<Field, Column> entry : metaEntity.getFieldsColumn().entrySet()) {
                setValueField(entity, entry.getKey(), result.getObject(entry.getValue().name()));
            }

            Integer id = result.getInt("id");
            setValueField(entity, metaEntity.getIdField(), id);

            if (id != null) {
                for (Map.Entry<Field, OneToOne> entry : metaEntity.getFieldsOneToOne().entrySet()) {
                    String query = String.format("SELECT * FROM %s WHERE %s=%d", entry.getValue().table(), entry.getValue().foreignkey(), id);
                    setValueField(entity, entry.getKey(), getEntity(query, entry.getKey().getType()));
                }

                for (Map.Entry<Field, OneToMany> entry : metaEntity.getFieldsOneToMany().entrySet()) {
                    String query = String.format("SELECT * FROM %s WHERE %s=%d", entry.getValue().table(), entry.getValue().foreignkey(), id);
                    setValueField(entity, entry.getKey(), getListEntity(query, getGenericTypeOfField(entry.getKey())));
                }
            }
            return entity;
        } catch (InstantiationException | IllegalAccessException | SQLException e) {
            LOG.info("Problem with getting entity", e);
        }
        return null;
    }

    private Class<?> getGenericTypeOfField(Field field) {
        ParameterizedType generic = (ParameterizedType) field.getGenericType();
        return (Class<?>) generic.getActualTypeArguments()[0];
    }

    private void setValueField(Object object, Field field, Object value) {
        if (field == null) {
            LOG.debug("Method gets empty field for object {}. System attempts assign value {}", object.getClass().getName(), value);
            return;
        }
        field.setAccessible(true);
        try {
            field.set(object, value);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            LOG.debug("Assigning variable '{}' for field '{}' in class '{}' passed with error", value, field.getName(),
                    object.getClass().getName(), e);
        }
        field.setAccessible(false);
    }

    private Object getValueField(Object object, Field field) {
        field.setAccessible(true);
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            return null;
        } finally {
            field.setAccessible(false);
        }
    }


}

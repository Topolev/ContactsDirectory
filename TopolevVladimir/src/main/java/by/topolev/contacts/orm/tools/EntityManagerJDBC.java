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
import org.apache.commons.collections4.iterators.ObjectArrayIterator;
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

    private Map<Class<?>, MetaEntity> metaEntityList = new LinkedHashMap<>();
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
        Map<String, Object> map = getMapForEntity(entity);

        if (id == null) {
            query.append(String.format("INSERT INTO %s SET ", metaEntity.getTable().name()))
                    .append(getTemplateSetSection(entity));
            id = executeQueryInsertUpdateRow(query.toString(), map);
        } else {
            query.append(String.format("UPDATE %s SET ", metaEntity.getTable().name()))
                    .append(getTemplateSetSection(entity))
                    .append(" WHERE id= ?");

            map.put("id", id);
            executeQueryInsertUpdateRow(query.toString(), map);
        }

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
    public <T> List<T> getListEntity(String templateQuery, Map<String, Object> map, Class<T> clazz, boolean lazyLoad) {
        List<T> entityList = new ArrayList<T>();
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(templateQuery);
            statement = insertValueInPrepareStatement(statement, map);

            LOG.debug("Query: " + statement.toString());

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                entityList.add(getEntityFromResultSet(result, clazz, lazyLoad));
            }
        } catch (SQLException e) {
            LOG.debug("Problem with getting of entity list", e);
        } finally {
            closeConnection(connection, statement);
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
    public <T> T getEntity(String templateQuery, Map<String, Object> map, Class<T> clazz) {
        T entity = null;

        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();

            statement = connection.prepareStatement(templateQuery);
            statement = insertValueInPrepareStatement(statement, map);

            LOG.debug("Query: " + statement.toString());

            ResultSet result = statement.executeQuery();
            if (!result.next())
                return null;
            entity = getEntityFromResultSet(result, clazz, false);
        } catch (SQLException e) {
            LOG.debug("Problem with getting of entity", e);
        } finally {
            closeConnection(connection, statement);
        }
        return entity;
    }


    @Override
    public <T> List<T> getEntitiesById(Class<T> clazz, Integer... idList) {

        if (ArrayUtils.isEmpty(idList)) return null;

        StringBuilder queryTemplate = new StringBuilder();
        MetaEntity metaEntity = metaEntityList.get(clazz);

        queryTemplate.append("SELECT * FROM " + metaEntity.getTable().name() + " WHERE ")
                .append(getTemplateSectionIdConnectedWithOr(idList));
        Map<String, Object> map = getMapFromArray(idList);
        return getListEntity(queryTemplate.toString(), map, clazz, true);
    }

    @Override
    public <T> void deleteEntity(Class<T> clazz, Integer... idList) {
        if (ArrayUtils.isEmpty(idList)) return;

        StringBuilder queryTemplate = new StringBuilder();
        MetaEntity metaEntity = metaEntityList.get(clazz);

        queryTemplate.append("DELETE FROM " + metaEntity.getTable().name() + " WHERE ")
                .append(getTemplateSectionIdConnectedWithOr(idList));
        Map<String, Object> map = getMapFromArray(idList);

        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(queryTemplate.toString());
            statement = insertValueInPrepareStatement(statement, map);

            LOG.debug("Query: " + statement.toString());

            statement.executeUpdate();
        } catch (SQLException e) {
            LOG.debug("Problem with delete", e);
        } finally {
            closeConnection(connection, statement);
        }

    }

    @Override
    public int getCountRows(String templateQuery, Map<String, Object> map, Class<?> clazz) {
        Table table = clazz.getAnnotation(Table.class);
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(templateQuery);
            statement = insertValueInPrepareStatement(statement, map);

            LOG.debug("Query: " + statement.toString());

            ResultSet result = statement.executeQuery();
            result.next();
            return result.getInt(1);
        } catch (SQLException e) {
            LOG.debug("Can not get numbers of rows ", e);
        } finally {
            closeConnection(connection, statement);
        }
        return 0;
    }

    @Override
    public int getCountAllEntity(Class<?> clazz) {
        Table table = clazz.getAnnotation(Table.class);
        return getCountRows("SELECT COUNT(*) FROM " + table.name(), null, clazz);
    }

    private <T> String getTemplateSetSection(T entity) {
        MetaEntity metaEntity = metaEntityList.get(entity.getClass());
        StringBuilder template = new StringBuilder();

        Iterator<Map.Entry<Field, Column>> iterator = metaEntity.getFieldsColumn().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Field, Column> entry = iterator.next();
            template.append(String.format(" %s = ? ", entry.getValue().name()));
            if (iterator.hasNext()) {
                template.append(",");
            }
        }

        return template.toString();
    }

    private <T> Map<String, Object> getMapForEntity(T entity) {
        Map<String, Object> map = new LinkedHashMap<>();
        MetaEntity metaEntity = metaEntityList.get(entity.getClass());
        for (Map.Entry<Field, Column> entry : metaEntity.getFieldsColumn().entrySet()) {
            map.put(entry.getValue().name(), getObjectField(entity, entry.getKey()));
        }
        return map;
    }


    private String getTemplateSectionIdConnectedWithOr(Integer... idList) {
        StringBuilder query = new StringBuilder();

        if (idList != null) {
            Iterator<Integer> iterator = Arrays.asList(idList).iterator();
            while (iterator.hasNext()) {
                iterator.next();
                query.append(" id = ? ");
                if (iterator.hasNext()) {
                    query.append(" OR ");
                }
            }
        }
        return query.toString();
    }

    private Map<String, Object> getMapFromArray(Integer... idList) {
        Map<String, Object> map = new LinkedHashMap<>();
        for (Integer i = 0; i < idList.length; i++) {
            map.put(i.toString(), idList[i]);
        }
        return map;
    }

   /* private String getSectionQueryIdConnectedWithOr(Integer... idList) {
        StringBuilder query = new StringBuilder();
        for (Integer id : idList) {
            query.append(" id = " + id + " OR");
        }
        query.delete(query.length() - 3, query.length());
        return query.toString();
    }*/

    private Object getObjectField(Object obj, Field field) {
        field.setAccessible(true);
        Object value;
        try {
            value = field.get(obj);
            field.setAccessible(false);
            return value;
        } catch (IllegalArgumentException | IllegalAccessException e) {
            return null;
        }
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
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
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


    /*private <T> String getSetSectionQuery(T entity) {
        MetaEntity metaEntity = metaEntityList.get(entity.getClass());
        StringBuilder query = new StringBuilder();
        for (Map.Entry<Field, Column> entry : metaEntity.getFieldsColumn().entrySet()) {
            query.append(String.format(" %s = %s ,", entry.getValue().name(), getStringValue(entity, entry.getKey())));
        }
        query.delete(query.length() - 1, query.length()); // delete the last comma
        return query.toString();
    }*/


    private PreparedStatement insertValueInPrepareStatement(PreparedStatement statement, Map<String, Object> map) throws SQLException {
        if (map != null) {
            Integer i = 0;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                statement.setObject(++i, entry.getValue());
            }
        }
        return statement;
    }


    private Integer executeQueryInsertUpdateRow(String template, Map<String, Object> map) {

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(template, Statement.RETURN_GENERATED_KEYS);
            statement = insertValueInPrepareStatement(statement, map);

            LOG.debug("Query: " + statement.toString());

            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            LOG.debug("Can not insert or update row. Query: {}", statement.toString());
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


    private <T> T getEntityFromResultSet(ResultSet result, Class<T> clazz, boolean lazyLoad) {
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
                    String templateQuery = String.format("SELECT * FROM %s WHERE %s = ?", entry.getValue().table(), entry.getValue().foreignkey());

                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("foreignKey", id);

                    setValueField(entity, entry.getKey(), getEntity(templateQuery, map, entry.getKey().getType()));
                }

                if (!lazyLoad) {
                    for (Map.Entry<Field, OneToMany> entry : metaEntity.getFieldsOneToMany().entrySet()) {
                        String templateQuery = String.format("SELECT * FROM %s WHERE %s = ?", entry.getValue().table(), entry.getValue().foreignkey());
                        Map<String, Object> map = new LinkedHashMap<>();
                        map.put("id", id);
                        setValueField(entity, entry.getKey(), getListEntity(templateQuery, map, getGenericTypeOfField(entry.getKey()), lazyLoad));
                    }
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

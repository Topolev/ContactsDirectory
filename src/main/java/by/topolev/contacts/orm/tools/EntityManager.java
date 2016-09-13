package by.topolev.contacts.orm.tools;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.topolev.contacts.entity.Phone;
import by.topolev.contacts.orm.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.topolev.contacts.entity.Address;
import by.topolev.contacts.entity.Contact;

public class EntityManager {

    private final static Logger LOG = LoggerFactory.getLogger(EntityManager.class);

    DataSource dataSource = DataSourceFactory.getDataSource();

    private Map<Class<?>, MetaEntity> metaEntityList = new HashMap<>();
    private List<Class<?>> classEntity = new ArrayList<>();

    public EntityManager() {
        System.out.println("Entity manager");

        classEntity.add(Contact.class);
        classEntity.add(Address.class);
        classEntity.add(Phone.class);

        for (Class<?> clazz : classEntity) {
            metaEntityList.put(clazz, createMetaEntity(clazz));
        }
    }

    private MetaEntity createMetaEntity(Class<?> clazz) {
        MetaEntity metaEntity = new MetaEntity();
        metaEntity.setTable(clazz.getAnnotation(Table.class));

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Id id = field.getAnnotation(Id.class);
            Column column = field.getAnnotation(Column.class);
            OneToOne oneToOne = field.getAnnotation(OneToOne.class);
            OneToMany oneToMany = field.getAnnotation(OneToMany.class);
            if (id != null)
                metaEntity.setIdField(field);
            if (column != null)
                metaEntity.getFieldsColumn().put(field, column);
            if (oneToOne != null)
                metaEntity.getFieldsOneToOne().put(field, oneToOne);
            if (oneToMany != null)
                metaEntity.getFieldsOneToMany().put(field, oneToMany);
        }
        return metaEntity;
    }

    private String getStringValue(Object obj, Field field) {
        field.setAccessible(true);
        Object value;
        try {
            value = field.get(obj);
            field.setAccessible(false);
            if (value == null)
                return "null";
            if (field.getType() == String.class)
                return "'" + value.toString() + "'";
            return value.toString();
        } catch (IllegalArgumentException | IllegalAccessException e) {
            return "null";
        }
    }
    /*
    public <T> void insertInnerEntity(T entity, String extraEneQuery){
        MetaEntity metaEntity = metaEntityList.get(entity.getClass());
        StringBuilder query = new StringBuilder();
        query.append(String.format("INSERT INTO %s SET ", metaEntity.getTable().name()));

        for (Map.Entry<Field, Column> entry : metaEntity.getFieldsColumn().entrySet()) {
            query.append(String.format(" %s = %s ,", entry.getValue().name(), getStringValue(entity, entry.getKey())));
        }

    }*/

    private <T> Integer getIdEntity(T entity){
        MetaEntity metaEntity = metaEntityList.get(entity.getClass());
        Field idField = metaEntity.getIdField();
        return (Integer) getValueField(entity,idField);
    }

    public <T> void updateEntity(T entity){
        Integer id = getIdEntity(entity);
        if (id== null){
            insertNewEntity(entity);
        } else{
            updateCurrentEntity(entity);
        }
    }


    private <T> String getSetSectionQuery(T entity){
        MetaEntity metaEntity = metaEntityList.get(entity.getClass());
        StringBuilder query = new StringBuilder();
        for (Map.Entry<Field, Column> entry : metaEntity.getFieldsColumn().entrySet()) {
            query.append(String.format(" %s = %s ,", entry.getValue().name(), getStringValue(entity, entry.getKey())));
        }
        query.delete(query.length() - 1, query.length()); // delete the last comma
        return query.toString();
    }

    private Integer excecuteQueryInsertNewRow(String query){
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()){
                return generatedKeys.getInt(1);
            }
        } catch(SQLException e){
            LOG.debug("Can not insert new row. Query: {}", query);
        }
        return null;
    }

    private void excuteQueryUpdateCurrentRow(String query){
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeUpdate();
        } catch(SQLException e){
            LOG.debug("Can not update current row. Query: {}", query);
        }
    }

    public <T> void insertNewEntity(T entity) {
        MetaEntity metaEntity = metaEntityList.get(entity.getClass());
        StringBuilder query = new StringBuilder();
        query.append(String.format("INSERT INTO %s SET ", metaEntity.getTable().name()))
                .append(getSetSectionQuery(entity));

        LOG.debug(query.toString());

        Integer id = excecuteQueryInsertNewRow(query.toString());
        if (id != null) {
            for (Map.Entry<Field, OneToOne> entry : metaEntity.getFieldsOneToOne().entrySet()) {
                Object currentObject = getValueField(entity, entry.getKey());
                Field foreignkey = getFieldByName(currentObject, entry.getValue().foreignkey());
                setValueField(currentObject, foreignkey, id);
                updateEntity(currentObject);
            }
        }
    }

    public <T> void updateCurrentEntity(T entity){
        MetaEntity metaEntity = metaEntityList.get(entity.getClass());
        Integer id = getIdEntity(entity);
        StringBuilder query = new StringBuilder();
        query.append(String.format("UPDATE %s SET ", metaEntity.getTable().name()))
                .append(getSetSectionQuery(entity))
                .append(" WHERE id=")
                .append(id);
        LOG.debug(query.toString());

        excuteQueryUpdateCurrentRow(query.toString());

        for (Map.Entry<Field, OneToOne> entry : metaEntity.getFieldsOneToOne().entrySet()) {
            Object currentObject = getValueField(entity, entry.getKey());
            Field foreignkey = getFieldByName(currentObject, entry.getValue().foreignkey());
            setValueField(currentObject, foreignkey, id);
            updateEntity(currentObject);
        }
    }

    private Field getFieldByName(Object obj, String fieldName){
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields){
            if (field.getName().equals(fieldName)) return field;
        }
        return null;
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

    public <T> void deleteEntity(Class<T> clazz, Integer... idList) {
        StringBuilder query = new StringBuilder();
        MetaEntity metaEntity = metaEntityList.get(clazz);

        query.append("DELETE FROM " + metaEntity.getTable().name() + " WHERE");
        for (Integer id : idList) {
            query.append(" id = " + id + " OR");
        }
        query.delete(query.length() - 3, query.length());

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.createStatement().executeUpdate(query.toString());
        } catch (SQLException e) {
            LOG.debug("Problem with delete", e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.debug("Can not close connection", e);
            }
        }

        System.out.println(query);
    }

    public int getCountAllEntity(Class<?> clazz) {
        Connection connection = null;
        Table table = clazz.getAnnotation(Table.class);
        System.out.println(table.name());
        try {
            connection = dataSource.getConnection();
            PreparedStatement statment = connection.prepareStatement("SELECT COUNT(*) AS count FROM " + table.name());
            ResultSet result = statment.executeQuery();
            result.next();
            return result.getInt("count");
        } catch (SQLException e) {
            LOG.debug("Problem with getting number of row", e);
        } finally {
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
            MetaEntity metaEntity = metaEntityList.get(clazz);

            for (Map.Entry<Field, Column> entry : metaEntity.getFieldsColumn().entrySet()) {
                setValueField(entity, entry.getKey(), result.getObject(entry.getValue().name()));
            }

            Integer id = result.getInt("id");
            setValueField(entity, metaEntity.getIdField(),id);

            if (id != null){
                for (Map.Entry<Field, OneToOne> entry : metaEntity.getFieldsOneToOne().entrySet()){
                    String query = String.format("SELECT * FROM %s WHERE %s=%d", entry.getValue().table(), entry.getValue().foreignkey(), id );
                    setValueField(entity, entry.getKey(),getEntity(query,entry.getKey().getType()));
                }

                for (Map.Entry<Field, OneToMany> entry : metaEntity.getFieldsOneToMany().entrySet()){
                    String query = String.format("SELECT * FROM %s WHERE %s=%d", entry.getValue().table(), entry.getValue().foreignkey(), id );
                    setValueField(entity, entry.getKey(), getListEntity(query, getGenericTypeOfField(entry.getKey())));
                }
            }
            return entity;
        } catch (InstantiationException | IllegalAccessException | SQLException e) {
            LOG.info("Problem with getting entity", e);
        }
        return null;
    }

    private Class<?> getGenericTypeOfField(Field field){
        ParameterizedType generic = (ParameterizedType) field.getGenericType();
        return (Class<?>) generic.getActualTypeArguments()[0];
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

    private Object getValueField(Object object, Field field){
        field.setAccessible(true);
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            return null;
        } finally{
            field.setAccessible(false);
        }
    }

    public static void main(String[] arg) {
        EntityManager em = new EntityManager();
        em.deleteEntity(Contact.class, 1, 2);

        Contact contact = em.getEntity("SELECT * FROM contact WHERE id=3", Contact.class);
        List<Contact> contacts = em.getListEntity("SELECT * FROM contact", Contact.class);
        System.out.println(contacts);
        for (Contact c : contacts) {
            System.out.println(c.getAddress());
        }

    }

}

class MetaEntity {
    private Table table;
    private Field idField;
    private Map<Field, Column> fieldsColumn = new HashMap<>();
    private Map<Field, OneToOne> fieldsOneToOne = new HashMap<>();
    private Map<Field, OneToMany> fieldsOneToMany = new HashMap<>();

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Field getIdField() {
        return idField;
    }

    public void setIdField(Field idField) {
        this.idField = idField;
    }

    public Map<Field, Column> getFieldsColumn() {
        return fieldsColumn;
    }

    public void setFieldsColumn(Map<Field, Column> fieldsColumn) {
        this.fieldsColumn = fieldsColumn;
    }

    public Map<Field, OneToOne> getFieldsOneToOne() {
        return fieldsOneToOne;
    }

    public void setFieldsOneToOne(Map<Field, OneToOne> fieldsOneToOne) {
        this.fieldsOneToOne = fieldsOneToOne;
    }

    public Map<Field, OneToMany> getFieldsOneToMany() {
        return fieldsOneToMany;
    }

    public void setFieldsOneToMany(Map<Field, OneToMany> fieldsOneToMany) {
        this.fieldsOneToMany = fieldsOneToMany;
    }
}

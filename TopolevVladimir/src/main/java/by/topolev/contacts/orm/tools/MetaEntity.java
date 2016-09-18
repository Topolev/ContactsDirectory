package by.topolev.contacts.orm.tools;

import by.topolev.contacts.orm.annotation.Column;
import by.topolev.contacts.orm.annotation.OneToMany;
import by.topolev.contacts.orm.annotation.OneToOne;
import by.topolev.contacts.orm.annotation.Table;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vladimir on 14.09.2016.
 */
public class MetaEntity {
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

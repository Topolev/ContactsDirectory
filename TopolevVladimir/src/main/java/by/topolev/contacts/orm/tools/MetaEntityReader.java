package by.topolev.contacts.orm.tools;

import by.topolev.contacts.orm.annotation.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Vladimir on 18.09.2016.
 */
public class MetaEntityReader {

    public  Map<Class<?>, MetaEntity> getMetaEntity(List<Class<?>> classEntityList){
        Map<Class<?>, MetaEntity> metaEntityList = new HashMap<>();

        for (Class<?> clazz : classEntityList) {
            metaEntityList.put(clazz, createMetaEntity(clazz));
        }

        return metaEntityList;
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
}

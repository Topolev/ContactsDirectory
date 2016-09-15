package by.topolev.contacts.servlets.utils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class EntityFromFormUtil<T> {
    private static final Logger LOG = LoggerFactory.getLogger(EntityFromFormUtil.class);
    private Class<T> clazz;

    private ObjectMapper map = new ObjectMapper();

    public EntityFromFormUtil(Class<T> clazz) {
        this.clazz = clazz;
    }


    public <ENTITY> ENTITY createEntityFromRequest(List<FileItem> list, Class<ENTITY> clazz) {
        ENTITY entity = null;
        try {
            entity = clazz.newInstance();
            createEntityFromRequest(list, "", entity);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return entity;
    }

    private <ENTITY> void createEntityFromRequest(List<FileItem> list, String rootClass, ENTITY entity) throws InstantiationException, IllegalAccessException {
        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (isSimpleField(field)) {
                setField(entity, field, getValueFromForm(list, field, rootClass));
            } else {
                if (isListField(field)) {
                    Class<?> typeElementsOfList = getGenericTypeOfField(field);
                    String nameInnerEntity = typeElementsOfList.getSimpleName().toLowerCase();
                    FileItem fileItem = getFileItem(list, nameInnerEntity + ".indexes", rootClass);

                    Integer[] array = getArrayFromString(fileItem.getString());
                    if (array != null) {
                        List listOfEntity = new ArrayList();

                        for (Integer index : array) {
                            Object innerEntity = typeElementsOfList.newInstance();
                            createEntityFromRequest(list, nameInnerEntity + index + ".", innerEntity);
                            listOfEntity.add(innerEntity);
                        }
                        setField(entity, field, listOfEntity);
                    }

                } else {
                    Object innerEntity = field.getType().newInstance();
                    setField(entity, field, innerEntity);
                    createEntityFromRequest(list, field.getName() + ".", innerEntity);
                }
            }
        }
    }

    private Integer[] getArrayFromString(String arrayStr) {
        try {
            return map.readValue(arrayStr, Integer[].class);
        } catch (IOException e) {
            return null;
        }
    }

    private boolean isSimpleField(Field field) {
        Class<?> clazz = field.getType();
        return clazz == int.class || clazz == long.class || clazz == String.class || clazz == Integer.class || clazz == Long.class || clazz == Date.class;
    }

    private boolean isListField(Field field) {
        return field.getType() == List.class;
    }

    private FileItem getFileItem(List<FileItem> list, Field field, String rootClass) {
        List<FileItem> collect = list.stream()
                .filter(byFieldName(rootClass + field.getName()).and(isFormField()))
                .collect(toList());
        return collect.isEmpty() ? null : collect.get(0);
    }

    private FileItem getFileItem(List<FileItem> list, String nameField, String rootClass) {
        List<FileItem> collect = list.stream()
                .filter(byFieldName(rootClass + nameField).and(isFormField()))
                .collect(toList());
        return collect.isEmpty() ? null : collect.get(0);
    }

    private Class<?> getGenericTypeOfField(Field field) {
        ParameterizedType generic = (ParameterizedType) field.getGenericType();
        return (Class<?>) generic.getActualTypeArguments()[0];
    }

    private Predicate<FileItem> byFieldName(String anObject) {
        return entry -> entry.getFieldName().equals(anObject);
    }

    private Predicate<FileItem> isFormField() {
        return item -> item.isFormField();
    }


    private Object getValueFromForm(List<FileItem> list, Field field, String rootClass) {
        FileItem item = getFileItem(list, field, rootClass);
        if (item != null) {
            String valueStr = new String(item.get(), StandardCharsets.UTF_8);

            if (isEmpty(valueStr)) {
                return null;
            }
            if (field.getType() == String.class) {
                return valueStr;
            }
            if (field.getType() == int.class || field.getType() == Integer.class) {
                try{
                    return Integer.valueOf(valueStr);
                } catch(NumberFormatException e){
                    return null;
                }
            }
            if (field.getType() == long.class || field.getType() == Long.class) {
                return Long.valueOf(valueStr);
            }
            if (field.getType() == Date.class) {
                DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
                try {
                    return format.parse(valueStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private void setField(Object obj, Field field, Object value) {
        field.setAccessible(true);
        try {
            field.set(obj, value);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            LOG.debug("Problem with setting new value ('{}') for field ('{}') object ('{}')", value, field.getName(),
                    obj, e);
        }
        field.setAccessible(false);
    }
}

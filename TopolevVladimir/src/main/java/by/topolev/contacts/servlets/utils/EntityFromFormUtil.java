package by.topolev.contacts.servlets.utils;

import by.topolev.contacts.servlets.formdata.ErrorForm;
import by.topolev.contacts.servlets.utils.validation.TypeValidator;
import by.topolev.contacts.servlets.utils.validation.Validation;
import by.topolev.contacts.servlets.utils.validation.Validator;
import by.topolev.contacts.servlets.utils.validation.ValidatorFactory;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import by.topolev.contacts.servlets.formdata.Error;

public class EntityFromFormUtil {
    private static final Logger LOG = LoggerFactory.getLogger(EntityFromFormUtil.class);
    public static final String POINT = ".";

    private static ObjectMapper map = new ObjectMapper();

    public static <ENTITY> ENTITY createEntityFromRequest(List<FileItem> list, Class<ENTITY> clazz, ErrorForm error) {
        ENTITY entity = null;
        try {
            entity = clazz.newInstance();
            createEntityFromRequest(list, EMPTY, entity, error);
        } catch (InstantiationException | IllegalAccessException e) {
            LOG.debug("Can not create new entity of {}.", clazz.getName(), e);
        }
        return entity;
    }

    private static <ENTITY> void createEntityFromRequest(List<FileItem> list, String rootClass, ENTITY entity, ErrorForm error) throws InstantiationException, IllegalAccessException {
        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (isSimpleField(field)) {
                setField(entity, field, getValueFromForm(list, field, rootClass, error));
            } else {
                if (isListField(field)) {
                    setListField(entity, field, list, rootClass, error);
                } else {
                    setObjectField(entity, field, list, error);
                }
            }
        }
    }

    private static <ENTITY> void setObjectField(ENTITY entity, Field field, List<FileItem> list, ErrorForm error) throws InstantiationException, IllegalAccessException {
        Object innerEntity = field.getType().newInstance();
        setField(entity, field, innerEntity);
        createEntityFromRequest(list, field.getName() + POINT, innerEntity, error);
    }

    private static <ENTITY> void setListField(ENTITY entity, Field field, List<FileItem> list, String rootClass, ErrorForm error) throws InstantiationException, IllegalAccessException {
        final Class<?> typeElementsOfList = getGenericTypeOfField(field);
        if (typeElementsOfList != null) {
            String nameInnerEntity = typeElementsOfList.getSimpleName().toLowerCase();
            FileItem fileItem = getFileItem(list, nameInnerEntity + ".indexes", rootClass);

            Integer[] array = getArrayFromString(fileItem.getString());
            if (isNotEmpty(array)) {
                List listOfEntity = new ArrayList();

                for (Integer index : array) {
                    Object innerEntity = typeElementsOfList.newInstance();
                    createEntityFromRequest(list, nameInnerEntity + index + ".", innerEntity, error);
                    listOfEntity.add(innerEntity);
                }
                setField(entity, field, listOfEntity);
            }
        }
    }

    private static Integer[] getArrayFromString(String arrayStr) {
        try {
            return map.readValue(arrayStr, Integer[].class);
        } catch (IOException e) {
            LOG.debug("Can't parse string in array");
            return null;
        }
    }

    private static boolean isSimpleField(Field field) {
        Class<?> clazz = field.getType();
        return clazz == int.class || clazz == long.class || clazz == String.class || clazz == Integer.class || clazz == Long.class || clazz == Date.class;
    }

    private static boolean isListField(Field field) {
        return field.getType() == List.class;
    }

    private static FileItem getFileItem(List<FileItem> list, Field field, String rootClass) {
        Optional<FileItem> collect = list.stream()
                .filter(byFieldName(rootClass + field.getName()).and(isFormField()))
                .findFirst();
        return collect.isPresent() ? collect.get() : null;
    }

    private static FileItem getFileItem(List<FileItem> list, String nameField, String rootClass) {
        Optional<FileItem> collect = list.stream()
                .filter(byFieldName(rootClass + nameField).and(isFormField()))
                .findFirst();
        return collect.isPresent() ? collect.get() : null;
    }

    private static Class<?> getGenericTypeOfField(Field field) {
        ParameterizedType generic = (ParameterizedType) field.getGenericType();
        Type[] actualTypeArguments = generic.getActualTypeArguments();
        return ArrayUtils.isNotEmpty(actualTypeArguments) ? (Class<?>) actualTypeArguments[0] : null;
    }

    private static Predicate<FileItem> byFieldName(String anObject) {
        return entry -> StringUtils.equals(entry.getFieldName(), anObject);
    }

    private static Predicate<FileItem> isFormField() {
        return item -> item.isFormField();
    }


    private static Object getValueFromForm(List<FileItem> list, Field field, String rootClass, ErrorForm error) {
        FileItem item = getFileItem(list, field, rootClass);
        if (item != null) {
            String value = new String(item.get(), UTF_8);

            validField(value.trim(), field, error);

            if (isEmpty(value.trim())) {
                return null;
            }
            if (field.getType() == String.class) {
                return value.trim();
            }
            if (field.getType() == int.class || field.getType() == Integer.class) {
                return getInteger(value);
            }
            if (field.getType() == long.class || field.getType() == Long.class) {
                return getIntLong(value);
            }
            if (field.getType() == Date.class) {
                return getDate(value);
            }
        }
        return null;
    }

    private static void validField(String value, Field field, ErrorForm error) {

        Validation validation = field.getAnnotation(Validation.class);
        if (validation != null) {
            TypeValidator[] listValidator =  validation.listValidator();
            for (TypeValidator typeValidator : listValidator){
                Validator validator = ValidatorFactory.getValidator(typeValidator);
                if (!validator.test(value)) {
                    error.addError(field.getName(), validator.getMessageError());
                }
            }
        }
    }

    private static Object getDate(String value) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(value);
        } catch (ParseException e) {
            LOG.debug("Can't parse the date", e);
            return null;
        }
    }

    private static Object getIntLong(String value) {
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException e) {
            LOG.debug("Can't parse the number", e);
            return null;
        }
    }

    private static Object getInteger(String value) {
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            LOG.debug("Can't parse the number", e);
            return null;
        }
    }

    private static void setField(Object obj, Field field, Object value) {
        field.setAccessible(true);
        try {
            field.set(obj, value);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            LOG.debug("Problem with setting new value ('{}') for field ('{}') for object ('{}')", value, field.getName(),
                    obj, e);
        }
        field.setAccessible(false);
    }
}

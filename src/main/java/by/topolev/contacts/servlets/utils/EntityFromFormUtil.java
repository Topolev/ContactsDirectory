package by.topolev.contacts.servlets.utils;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityFromFormUtil<T> {
	private static final Logger LOG = LoggerFactory.getLogger(EntityFromFormUtil.class);
	private Class<T> clazz;
	
	public EntityFromFormUtil(Class<T> clazz){
		this.clazz = clazz;
	}
	
	
	public T createEntityFromRequest(List<FileItem> list) {
		T entity = null;
		try {
			entity = clazz.newInstance();
			Field[] fields = entity.getClass().getDeclaredFields();
			for (Field field : fields) {
				if (isExistedFieldInForm(list, field)) {
					setField(entity, field, getValueFromForm(list, field));
				}
			}
			return entity;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	private boolean isExistedFieldInForm(List<FileItem> list, Field field) {
		for (FileItem item : list) {
			if (item.isFormField() && item.getFieldName().equals(field.getName()))
				return true;
		}
		return false;
	}

	private Object getValueFromForm(List<FileItem> list, Field field) {
		String valueStr = null; 
		for (FileItem item : list) {
			if (item.isFormField() && item.getFieldName().equals(field.getName())) {
				valueStr = new String(item.get(), StandardCharsets.UTF_8);
				break;
			}
		}
		if (field.getType() == String.class) {
			return valueStr;
		}
		if (field.getType() == int.class) {
			return Integer.valueOf(valueStr);
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

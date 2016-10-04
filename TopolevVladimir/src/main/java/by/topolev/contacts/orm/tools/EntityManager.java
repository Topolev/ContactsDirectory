package by.topolev.contacts.orm.tools;

import java.util.List;
import java.util.Map;

/**
 * Created by Vladimir on 25.09.2016.
 */
public interface EntityManager {
    <T> void updateEntity(T entity);
    <T> List<T> getListEntity(String templateQuery, Map<String, Object> map, Class<T> clazz, boolean lazyLoad);
    <T> T getEntity(String templateQuery, Map<String, Object> map, Class<T> clazz);

    <T> List<T> getEntitiesById(Class<T> clazz, Integer... idList);
    <T> void deleteEntity(Class<T> clazz, Integer... idList);
    int getCountAllEntity(Class<?> clazz);
    int getCountRows(String templateQuery,Map<String, Object> map, Class<?> clazz);

}

package by.topolev.contacts.orm.tools;

import java.util.List;

/**
 * Created by Vladimir on 25.09.2016.
 */
public interface EntityManager {
    public <T> void updateEntity(T entity);
    public <T> List<T> getListEntity(String query, Class<T> clazz);
    public <T> T getEntity(String query, Class<T> clazz);
    public <T> List<T> getEntitiesById(Class<T> clazz, Integer... idList);
    public <T> void deleteEntity(Class<T> clazz, Integer... idList);
    public int getCountAllEntity(Class<?> clazz);

}

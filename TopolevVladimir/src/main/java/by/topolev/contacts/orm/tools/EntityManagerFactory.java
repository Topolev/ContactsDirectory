package by.topolev.contacts.orm.tools;

/**
 * Created by Vladimir on 25.09.2016.
 */
public class EntityManagerFactory {


    public static EntityManager getEntityManager()  {
        return EntityManagerJDBC.getEntityManager();
    }
}

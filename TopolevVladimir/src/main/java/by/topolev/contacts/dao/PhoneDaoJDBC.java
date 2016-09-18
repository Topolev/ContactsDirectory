package by.topolev.contacts.dao;

import by.topolev.contacts.entity.Contact;
import by.topolev.contacts.entity.Phone;
import by.topolev.contacts.orm.tools.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Vladimir on 15.09.2016.
 */
public class PhoneDaoJDBC implements PhoneDao {

    private static final Logger LOG = LoggerFactory.getLogger(ContactDaoJDBC.class);

    private EntityManager em = new EntityManager();
    @Override
    public void deletePhones(Integer... idList) {
        em.deleteEntity(Phone.class, idList);
    }
}

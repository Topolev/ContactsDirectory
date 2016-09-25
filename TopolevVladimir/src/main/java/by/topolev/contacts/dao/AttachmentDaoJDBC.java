package by.topolev.contacts.dao;

import by.topolev.contacts.entity.Attachment;
import by.topolev.contacts.orm.tools.EntityManager;
import by.topolev.contacts.orm.tools.EntityManagerFactory;
import by.topolev.contacts.orm.tools.EntityManagerJDBC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Vladimir on 17.09.2016.
 */
public class AttachmentDaoJDBC implements AttachmentDao {
    private static final Logger LOG = LoggerFactory.getLogger(ContactDaoJDBC.class);

    private EntityManager em = EntityManagerFactory.getEntityManager();

    @Override
    public void deleteAttachment(Integer... idList) {
        em.deleteEntity(Attachment.class, idList);
    }
}

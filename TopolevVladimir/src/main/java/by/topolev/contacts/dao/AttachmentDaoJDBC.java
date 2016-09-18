package by.topolev.contacts.dao;

import by.topolev.contacts.entity.Attachment;
import by.topolev.contacts.entity.Phone;
import by.topolev.contacts.orm.tools.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Vladimir on 17.09.2016.
 */
public class AttachmentDaoJDBC implements AttachmentDao {
    private static final Logger LOG = LoggerFactory.getLogger(ContactDaoJDBC.class);

    private EntityManager em = new EntityManager();

    @Override
    public void deleteAttachment(Integer... idList) {
        em.deleteEntity(Attachment.class, idList);
    }
}

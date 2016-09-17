package by.topolev.contacts.dao;

/**
 * Created by Vladimir on 17.09.2016.
 */
public class AttachmentDaoFactory  {
    public static AttachmentDao getAttachmentDao(){
        return new AttachmentDaoJDBC();
    }
}

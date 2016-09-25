package by.topolev.contacts.servlets.commands;

import by.topolev.contacts.dao.AttachmentDao;
import by.topolev.contacts.dao.AttachmentDaoFactory;
import by.topolev.contacts.dao.PhoneDao;
import by.topolev.contacts.dao.PhoneDaoFactory;
import by.topolev.contacts.entity.Attachment;
import by.topolev.contacts.entity.Contact;
import by.topolev.contacts.services.*;
import by.topolev.contacts.servlets.frontcontroller.Command;
import by.topolev.contacts.servlets.utils.EntityFromFormUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * Created by Vladimir on 18.09.2016.
 */
public class ContactCreateUpdateCommand implements Command {

    private static final Logger LOG = LoggerFactory.getLogger(ContactCreateUpdateCommand.class);
    private static final ObjectMapper map = new ObjectMapper();
    public static final String UPLOADPHOTO = "uploadphoto";
    public static final String FILE = "file";
    public static final String ATTACHMENT_INDEXES = "attachment.indexes";
    public static final String ID = "id";

    private ServletFileUpload upload;

    private ContactService contactService = ContactServiceFactory.getContactService();
    private UploadImageService uploadImageService = UploadImageServiceFactory.getUploadImageService();

    private UploadFileServiceImpl uploadFileService = new UploadFileServiceImpl();

    private PhoneDao phoneDao = PhoneDaoFactory.getPhoneDao();
    private AttachmentDao attachmentDao = AttachmentDaoFactory.getAttachmentDao();

    private EntityFromFormUtil<Contact> entityFromFormUtil = new EntityFromFormUtil<Contact>(Contact.class);

    public ContactCreateUpdateCommand(ServletContext servletContext) {
        DiskFileItemFactory factory = new DiskFileItemFactory();

        // Configure a repository (to ensure a secure temp location is used)
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);

        // Create a new file upload handler
        upload = new ServletFileUpload(factory);
    }


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOG.debug("Update/Create contact");

        List<FileItem> items = getFileItemList(req);
        if (items != null) {
            String idStr = getFieldValue(ID, items);
            if (isNotEmpty(idStr)) {
                try{
                    Integer.valueOf(idStr);
                }catch(NumberFormatException e){
                    LOG.debug("Someone attempts to apply huck related to SQL Injection");
                    resp.sendRedirect(req.getContextPath() + "/contactlist");
                    return null;
                }
            }


			/*Extract entity from form*/
            Contact contact = entityFromFormUtil.createEntityFromRequest(items, Contact.class);

			/*Save profile image*/
            FileItem photoItem = getFileItemByName(UPLOADPHOTO, items);


            if (photoItem != null && isNotEmpty(photoItem.getName())){
                contact.setPhoto(uploadImageService.saveImage(photoItem));
            }


			/*Save attachments*/
            String listAttachments = getFieldValue(ATTACHMENT_INDEXES,items);
            Integer[] listAttachmentsId = getArrayFromString(listAttachments);

            List<Attachment> attachment = contact.getAttachmentList();

            if (attachment != null) {
                Iterator<Attachment> iterator = attachment.iterator();

                for (Integer i : listAttachmentsId) {
                    FileItem fileItem = getFileItemByName(FILE + i, items);
                    String nameFile = uploadFileService.saveFile(fileItem);
                    Attachment currentAttachment = iterator.next();
                    if (nameFile != null) {
                        currentAttachment.setNameFileInSystem(nameFile);
                    }
                }
            }

            contactService.updateContact(contact);



            /*Delete phones*/
            String listDeletePhoneIdStr = getFieldValue("phone.delete",items);
            Integer[] listDeletePhoneId = getArrayFromString(listDeletePhoneIdStr);
            phoneDao.deletePhones(listDeletePhoneId);

			/*Delete attachtments*/
            String listDeleteAttachmentIdStr = getFieldValue("attachment.delete",items);
            Integer[] listDeleteAttachmentId = getArrayFromString(listDeleteAttachmentIdStr);
            attachmentDao.deleteAttachment(listDeleteAttachmentId);

            int count = contactService.getCountContacts();
            resp.sendRedirect(req.getContextPath() + "/contactlist?countRow=10&page=" + (int) (Math.ceil((double)count/10)-1));

            return null;

        }
        LOG.debug("User sends form without data or form doesn't have enctype=\"multipart/form-data\"");
        return null;
    }

    private List<FileItem> getFileItemList(HttpServletRequest req) {
        List<FileItem> items = null;
        boolean isMultipart = ServletFileUpload.isMultipartContent(req);

        if (!isMultipart) {
            LOG.info("Form isn't exsited multipart data");
        } else {
            try {
                items = upload.parseRequest(req);
            } catch (FileUploadException e) {
                LOG.info("Problems with parsing request to FileItem", e);
            }
        }
        return items;
    }

    private FileItem getFileItemByName(String nameField, List<FileItem> items){
        for (FileItem item : items){
            if (item.getFieldName().equals(nameField)) {
                return item;
            }
        }
        return null;
    }

    private String getFieldValue(String nameField, List<FileItem> items){
        for (FileItem item : items) {
            if (item.isFormField() && item.getFieldName().equals(nameField)) return item.getString();
        }
        return null;
    }

    private Integer[] getArrayFromString(String arrayStr) {
        try {
            return map.readValue(arrayStr, Integer[].class);
        } catch (IOException | NullPointerException e) {
            return null;
        }
    }
}

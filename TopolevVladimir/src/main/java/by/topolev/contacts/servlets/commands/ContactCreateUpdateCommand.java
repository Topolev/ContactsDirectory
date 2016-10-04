package by.topolev.contacts.servlets.commands;

import by.topolev.contacts.dao.AttachmentDao;
import by.topolev.contacts.dao.AttachmentDaoFactory;
import by.topolev.contacts.dao.PhoneDao;
import by.topolev.contacts.dao.PhoneDaoFactory;
import by.topolev.contacts.entity.Attachment;
import by.topolev.contacts.entity.Contact;
import by.topolev.contacts.services.*;
import by.topolev.contacts.servlets.formdata.ErrorForm;
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
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static by.topolev.contacts.servlets.utils.ServletUtil.getFileItemParameter;
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

    public ContactCreateUpdateCommand(ServletContext servletContext) {
        DiskFileItemFactory factory = new DiskFileItemFactory();

        // Configure a repository (to ensure a secure temp location is used)
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);

        // Create a new file upload handler
        upload = new ServletFileUpload(factory);
        upload.setSizeMax(100*1024*1024);
    }


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOG.debug("Update/create contact");

        List<FileItem> items = getFileItemList(req);

        if (items != null) {

           String idStr = getFieldValue(ID, items);
            if (StringUtils.isNotEmpty(idStr) && !StringUtils.isNumeric(idStr)) {
                LOG.debug("Someone attempts to apply huck related to SQL Injection");
                resp.sendRedirect(req.getContextPath() + "/contactlist");
                return null;
            }

			/*Extract entity from form and update*/
			ErrorForm error = new ErrorForm();
            Contact contact = EntityFromFormUtil.createEntityFromRequest(items, Contact.class, error);

            if (!error.isValid()){
                LOG.debug("Can not save/update contact. User entered invalid data");

                req.setAttribute("contact", contact);
                req.setAttribute("page", getFileItemParameter(items, "page", Integer.class, 0) );
                req.setAttribute("countRow", getFileItemParameter(items, "countRow", Integer.class, 10));
                req.setAttribute("error", error);
                return "/contact.jsp";
            }

            updatePhoto(contact, items);
            updateAttachments(contact, items);

            contactService.updateContact(contact);

            /*Delete phones and attachments*/
            deletePhones(items);
            deleteAttachments(items);


            int count = contactService.getCountContacts();

            if (contact.getId() == null){
                LOG.debug("Creating of new contact was successful.");
            } else{
                LOG.debug("Updating of contact with id={} was successful.", contact.getId());
            }

            resp.sendRedirect(req.getContextPath()
                    + "/contactlist?page=" +  getFileItemParameter(items, "page", Integer.class, contact.getId() == null ?(int) (Math.ceil((double) count / 10) - 1) : 0 )
                    + "&countRow=" +  getFileItemParameter(items, "countRow", Integer.class, 10));
            return null;

        } else {
            LOG.debug("User sends form without data or form doesn't have enctype=\"multipart/form-data\" or size of file is too large.");
            LOG.debug(req.getContextPath() + "/contactlist");
            resp.sendRedirect(req.getContextPath() + "/contactlist");
            //return "/error.jsp";
            return null;
        }
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
                return null;
            }
        }
        return items;
    }

    private void updatePhoto(Contact contact, List<FileItem> items){
        FileItem photoItem = getFileItemByName(UPLOADPHOTO, items);

        if (photoItem != null && isNotEmpty(photoItem.getName())) {
            contact.setPhoto(uploadImageService.saveImage(photoItem));
        }
    }

    private void updateAttachments(Contact contact, List<FileItem> items){
        /*Save attachments*/
        String listAttachments = getFieldValue(ATTACHMENT_INDEXES, items);
        Integer[] listAttachmentsId = getArrayFromString(listAttachments);

        List<Attachment> attachment = contact.getAttachmentList();

        if (attachment != null) {
            Iterator<Attachment> iterator = attachment.iterator();

            for (Integer i : listAttachmentsId) {
                FileItem fileItem = getFileItemByName(FILE + i, items);
                String nameFile = uploadFileService.saveFile(fileItem, contact.getId());
                Attachment currentAttachment = iterator.next();
                if (nameFile != null) {
                    currentAttachment.setNameFileInSystem(nameFile);
                }
            }
        }
    }

    private void deletePhones(List<FileItem> items){
        String listDeletePhoneIdStr = getFieldValue("phone.delete", items);
        Integer[] listDeletePhoneId = getArrayFromString(listDeletePhoneIdStr);
        phoneDao.deletePhones(listDeletePhoneId);
    }

    private void deleteAttachments(List<FileItem> items){
        String listDeleteAttachmentIdStr = getFieldValue("attachment.delete", items);
        Integer[] listDeleteAttachmentId = getArrayFromString(listDeleteAttachmentIdStr);
        attachmentDao.deleteAttachment(listDeleteAttachmentId);
    }

    private FileItem getFileItemByName(String nameField, List<FileItem> items) {
        for (FileItem item : items) {
            if (item.getFieldName().equals(nameField)) {
                return item;
            }
        }
        return null;
    }

    private String getFieldValue(String nameField, List<FileItem> items) {
        Optional<String> first = items.stream()
                .filter(item -> item.isFormField())
                .filter(item -> item.getFieldName().equals(nameField))
                .map(FileItem::getString)
                .findFirst();

        return first.isPresent() ? first.get() : null;
    }


    private Integer[] getArrayFromString(String arrayStr) {
        try {
            return arrayStr == null ? null : map.readValue(arrayStr, Integer[].class);
        } catch (IOException e) {
            return null;
        }
    }
}

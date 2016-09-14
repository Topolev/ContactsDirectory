package by.topolev.contacts.servlets;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.topolev.contacts.services.UploadImageService;
import by.topolev.contacts.services.UploadImageServiceFactory;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.rewrite.RewriteAppender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.topolev.contacts.entity.Contact;
import by.topolev.contacts.orm.annotation.Column;
import by.topolev.contacts.services.ContactService;
import by.topolev.contacts.services.ContactServiceFactory;
import by.topolev.contacts.servlets.utils.EntityFromFormUtil;

public class ContactNewServlet extends HttpServlet {

	private static final Logger LOG = LoggerFactory.getLogger(ContactNewServlet.class);

	private ServletFileUpload upload;
	
	private ContactService contactService = ContactServiceFactory.getContactService();
	private UploadImageService uploadImageService = UploadImageServiceFactory.getUploadImageService();
	
	private EntityFromFormUtil<Contact> entityFromFormUtil = new EntityFromFormUtil<Contact>(Contact.class);

	@Override
	public void init() {
		initServletFileUpload();
	}

	private void initServletFileUpload() {
		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Configure a repository (to ensure a secure temp location is used)
		ServletContext servletContext = this.getServletConfig().getServletContext();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);

		// Create a new file upload handler
		upload = new ServletFileUpload(factory);
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/contact.jsp").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LOG.debug("doPOST");

		List<FileItem> items = getFileItemList(req);
		if (items != null) {




			/*Extract entity from form*/
			Contact contact = entityFromFormUtil.createEntityFromRequest(items,Contact.class);

			/*Save profile image*/
			FileItem photoItem = getFileItemByName("uploadphoto", items);


			if (photoItem != null && photoItem.getName() != null && !"".equals(photoItem.getName())){
				contact.setPhoto(uploadImageService.saveImage(photoItem));
			}

			contactService.updateContact(contact);
			int count = contactService.getCountContacts();
			resp.sendRedirect(req.getContextPath() + "/contactlist?countRow=10&page=" + (int) (Math.ceil((double)count/10)-1));



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
			}
		}
		return items;
	}

	private FileItem getFileItemByName(String nameField, List<FileItem> items){
		for (FileItem item : items){
			if (item.getFieldName().equals(nameField)) return item;
		}
		return null;
	}

}

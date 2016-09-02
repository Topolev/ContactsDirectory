package by.topolev.contacts.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.topolev.contacts.entity.Contact;
import by.topolev.contacts.services.ContactService;
import by.topolev.contacts.services.ContactServiceFactory;

public class ContactListServlet extends HttpServlet{
	
	private ContactService contactService = ContactServiceFactory.getContactService();
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		List<Contact> contactList = contactService.getContactList();
		
		req.setAttribute("contactList", contactList);
		req.getRequestDispatcher("/contact_list.jsp").forward(req, resp);
	}
}

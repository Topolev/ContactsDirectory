package by.topolev.contacts.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import by.topolev.contacts.services.ContactService;
import by.topolev.contacts.services.ContactServiceFactory;

public class ContactDeleteServlet extends HttpServlet {

	private ObjectMapper map = new ObjectMapper();

	private ContactService contactService = ContactServiceFactory.getContactService();

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int[] idList = map.readValue(req.getParameter("delete"), int[].class);
		contactService.deleteContact(idList);
		
		resp.sendRedirect(req.getContextPath() + "/contactlist?page=" + req.getParameter("page") + "&countRow=" + req.getParameter("countRow"));
	}

}

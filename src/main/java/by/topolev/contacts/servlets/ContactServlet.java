package by.topolev.contacts.servlets;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.topolev.contacts.entity.Contact;
import by.topolev.contacts.services.ContactService;
import by.topolev.contacts.services.ContactServiceFactory;

public class ContactServlet extends HttpServlet {

	private ContactService contactService = ContactServiceFactory.getContactService();

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*Pattern regExIdPattern = Pattern.compile("/([0-9]*)");
		System.out.println(req.getPathInfo());
		Matcher matcher = regExIdPattern.matcher(req.getPathInfo());
		Integer id = null;
		if (matcher.find()){
			id = Integer.parseInt(matcher.group(1));
		}
		Contact contact = null;
		if (id != null){
			contact = contactService.getContactById(id);
			System.out.println(contact);
		}
		req.setAttribute("contact", contact);
		
		req.getRequestDispatcher("/contact.jsp").forward(req, resp);;*/
		
		int id;
		try {
			id = Integer.valueOf(req.getParameter("id"));
		} catch (NumberFormatException e) {
			System.out.println("Id is valid");
			return;
		}
		
		Contact contact = null;
		contact = contactService.getContactById(id);
		
		if (contact == null){
			System.out.println("User isn't found with this id");
			return;
		}
		
		req.setAttribute("contact", contact);
		req.getRequestDispatcher("/contact.jsp").forward(req, resp);
		
	}
}

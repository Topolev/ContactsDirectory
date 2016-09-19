package by.topolev.contacts.servlets.commands;

import by.topolev.contacts.entity.Contact;
import by.topolev.contacts.services.ContactService;
import by.topolev.contacts.services.ContactServiceFactory;
import by.topolev.contacts.servlets.frontcontroller.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Vladimir on 18.09.2016.
 */
public class ContactShowCommand implements Command{

    private static final Logger LOG = LoggerFactory.getLogger(ContactShowCommand.class);

    private ContactService contactService = ContactServiceFactory.getContactService();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id;
        try {
            id = Integer.parseInt(req.getParameter("id"));
        } catch (NumberFormatException e) {
            LOG.debug("User id is invalid");
            return "/error.jsp";
        }

        Contact contact = null;
        contact = contactService.getContactById(id);

        if (contact == null){
            LOG.debug("User isn't found with id = {}", id);
            return "/error.jsp";
        }

        req.setAttribute("contact", contact);
        return "/contact.jsp";
    }
}

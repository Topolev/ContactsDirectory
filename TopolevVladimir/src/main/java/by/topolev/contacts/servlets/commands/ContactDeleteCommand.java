package by.topolev.contacts.servlets.commands;

import by.topolev.contacts.services.ContactService;
import by.topolev.contacts.services.ContactServiceFactory;
import by.topolev.contacts.servlets.frontcontroller.Command;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Vladimir on 18.09.2016.
 */
public class ContactDeleteCommand implements Command{

    private ObjectMapper map = new ObjectMapper();

    private ContactService contactService = ContactServiceFactory.getContactService();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer[] idList = map.readValue(req.getParameter("delete"), Integer[].class);
        contactService.deleteContact(idList);

        resp.sendRedirect(req.getContextPath() + "/contactlist?page=" + req.getParameter("page") + "&countRow=" + req.getParameter("countRow"));
        return null;
    }
}

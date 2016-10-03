package by.topolev.contacts.servlets.commands;

import by.topolev.contacts.services.ContactService;
import by.topolev.contacts.services.ContactServiceFactory;
import by.topolev.contacts.servlets.frontcontroller.Command;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.topolev.contacts.servlets.utils.ServletUtil.getRequestParameter;

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


        Integer countRow = getRequestParameter(req, "countRow", Integer.class, 10);
        Integer count = contactService.getCountContacts();
        Integer page = getRequestParameter(req, "page", Integer.class, 0);

        if (((int )Math.ceil((double)count / countRow) -1) < page) {
            page--;
        }


        resp.sendRedirect(req.getContextPath()
                + "/contactlist?page=" + page
                + "&countRow=" + countRow);
        return null;
    }
}

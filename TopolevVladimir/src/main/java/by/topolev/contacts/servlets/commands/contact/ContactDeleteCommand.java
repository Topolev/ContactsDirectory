package by.topolev.contacts.servlets.commands.contact;

import by.topolev.contacts.services.ContactService;
import by.topolev.contacts.services.ContactServiceFactory;
import by.topolev.contacts.servlets.frontcontroller.Command;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

import static by.topolev.contacts.servlets.utils.ServletUtil.getRequestParameter;

/**
 * Created by Vladimir on 18.09.2016.
 */
public class ContactDeleteCommand implements Command{

    private static final Logger LOG = LoggerFactory.getLogger(ContactDeleteCommand.class);

    private ObjectMapper map = new ObjectMapper();

    private ContactService contactService = ContactServiceFactory.getContactService();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer[] idList = map.readValue(req.getParameter("delete"), Integer[].class);

        LOG.debug("Delete contacts with id = {}", Arrays.asList(idList));

        contactService.deleteContact(idList);

        Integer countRow = getRequestParameter(req, "countRow", Integer.class, 10);
        Integer page = getPage(req, countRow);

        resp.sendRedirect(req.getContextPath()
                + "/contactlist?page=" + page
                + "&countRow=" + countRow);
        return null;
    }

    private Integer getPage(HttpServletRequest req, Integer countRow){
        Integer count = contactService.getCountContacts();
        Integer page = getRequestParameter(req, "page", Integer.class, 0);

        if (((int )Math.ceil((double)count / countRow) -1) < page) {
            page--;
        }
        return page;
    }
}

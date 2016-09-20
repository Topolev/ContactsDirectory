package by.topolev.contacts.servlets.commands;

import by.topolev.contacts.entity.Contact;
import by.topolev.contacts.services.ContactService;
import by.topolev.contacts.services.ContactServiceFactory;
import by.topolev.contacts.servlets.frontcontroller.Command;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Vladimir on 18.09.2016.
 */
public class ShowSendMessageFormCommand implements Command {

    private static final Logger LOG = LoggerFactory.getLogger(ShowSendMessageFormCommand.class);

    private ObjectMapper map = new ObjectMapper();

    private ContactService contactService = ContactServiceFactory.getContactService();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Integer[] idList = map.readValue(req.getParameter("sendto"), Integer[].class);
        LOG.debug("Send to: {}", idList);

        List<Contact> contacts = contactService.getContactById(idList);

        req.setAttribute("contacts", contacts);
        req.setAttribute("sendto", req.getParameter("sendto"));



        /*contactService.deleteContact(idList);*/

        //resp.sendRedirect(req.getContextPath() + "/contactlist?page=" + req.getParameter("page") + "&countRow=" + req.getParameter("countRow"));
        return "/send_message.jsp";
    }
}

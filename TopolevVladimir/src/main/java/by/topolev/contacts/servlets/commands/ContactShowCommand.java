package by.topolev.contacts.servlets.commands;

import by.topolev.contacts.entity.Contact;
import by.topolev.contacts.services.ContactService;
import by.topolev.contacts.services.ContactServiceFactory;
import by.topolev.contacts.servlets.formdata.Error;
import by.topolev.contacts.servlets.frontcontroller.Command;
import by.topolev.contacts.servlets.utils.PageAttributes.PageContact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.topolev.contacts.servlets.utils.PageNames.PAGE_CONTACT;
import static by.topolev.contacts.servlets.utils.PageNames.PAGE_ERROR;
import static by.topolev.contacts.servlets.utils.ServletUtil.getRequestParameter;
import static java.lang.Integer.parseInt;

/**
 * Created by Vladimir on 18.09.2016.
 */
public class ContactShowCommand implements Command {

    private static final Logger LOG = LoggerFactory.getLogger(ContactShowCommand.class);
    public static final String ID = "id";

    private ContactService contactService = ContactServiceFactory.getContactService();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        return findContactById(req, req.getParameter(ID)) ? PAGE_CONTACT : PAGE_ERROR;
    }

    private boolean findContactById(HttpServletRequest req, String contactId) {
        try {
            LOG.debug("Show contact with id = {}.", contactId);
            Contact contact = contactService.getContactById(parseInt(contactId));
            if (contact == null) {
                LOG.debug("User isn't found with id = {}.", contactId);
                populatePageError(req, String.format("User isn't found with id = %s", contactId));
                return false;
            }
            populatePageContact(req, contact);
        } catch (NumberFormatException e) {
            LOG.debug("Invalid contact id: '{}'.", contactId);
            populatePageError(req, String.format("Invalid contact id = %s", contactId));
            return false;
        }
        LOG.debug("User with id = {} is existed.", contactId);
        return true;
    }

    private void populatePageContact(HttpServletRequest req, Contact contact) {
        req.setAttribute(PageContact.ATTR_CONTACT, contact);
        req.setAttribute(PageContact.ATTR_PAGE, getRequestParameter(req, PageContact.PARAM_PAGE, Integer.class, 0));
        req.setAttribute(PageContact.ATTR_COUNT_PAGE, getRequestParameter(req, PageContact.PARAM_COUNT_ROW, Integer.class, 10));
    }

    private void populatePageError(HttpServletRequest req, String errorMessage){
        Error error = new Error();
        error.addError(errorMessage);
        req.setAttribute("errors", error);
    }
}

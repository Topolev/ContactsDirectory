package by.topolev.contacts.servlets.commands.message;

import by.topolev.contacts.config.ConfigUtil;
import by.topolev.contacts.entity.Contact;
import by.topolev.contacts.services.ContactService;
import by.topolev.contacts.services.ContactServiceFactory;
import by.topolev.contacts.services.SendEmailService;
import by.topolev.contacts.services.SendEmailServiceFactory;
import by.topolev.contacts.servlets.formdata.Error;
import by.topolev.contacts.servlets.frontcontroller.Command;
import org.apache.commons.collections4.CollectionUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stringtemplate.v4.ST;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static by.topolev.contacts.servlets.utils.ServletUtil.convertStringInIntArray;

/**
 * Created by Vladimir on 20.09.2016.
 */
public class SendMessagesCommand implements Command {

    private static final Logger LOG = LoggerFactory.getLogger(SendMessagesCommand.class);

    private static File pathTemplates;

    private ObjectMapper map = new ObjectMapper();


    private ContactService contactService = ContactServiceFactory.getContactService();
    private SendEmailService sendEmailService = SendEmailServiceFactory.getSendEmailService();
    private static ExecutorService executorService = Executors.newCachedThreadPool();


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        List<Contact> contacts = contactService.getContactById(convertStringInIntArray(req.getParameter("sendto")));

        if (CollectionUtils.isNotEmpty(contacts)) {
            final String subject = req.getParameter("subject");

            for (Contact contact : contacts) {
                ST template = new ST(req.getParameter("message"), '$', '$');
                template.add("u", contact);
                final String text = template.render();

                executorService.execute(() -> {
                    sendEmailService.sendMessage(ConfigUtil.getGmailUsername(), contact.getEmail(), subject, text);
                });
            }

            resp.sendRedirect(req.getContextPath() + "/contactlist?countRow=10&page=0");
            return null;

        } else{
            Error error = new Error();
            error.addError("You can choose one contact at least for sending message via email. ");
            req.setAttribute("errors", error);
            return "/error.jsp";
        }

    }


}

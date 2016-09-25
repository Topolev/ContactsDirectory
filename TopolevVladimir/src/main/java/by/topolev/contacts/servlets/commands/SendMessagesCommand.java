package by.topolev.contacts.servlets.commands;

import by.topolev.contacts.config.ConfigUtil;
import by.topolev.contacts.entity.Contact;
import by.topolev.contacts.services.ContactService;
import by.topolev.contacts.services.ContactServiceFactory;
import by.topolev.contacts.services.SendEmailService;
import by.topolev.contacts.services.SendEmailServiceFactory;
import by.topolev.contacts.servlets.frontcontroller.Command;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stringtemplate.v4.ST;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Vladimir on 20.09.2016.
 */
public class SendMessagesCommand implements Command {

    private static final Logger LOG = LoggerFactory.getLogger(SendMessagesCommand.class);

    private ObjectMapper map = new ObjectMapper();

    private ContactService contactService = ContactServiceFactory.getContactService();
    private SendEmailService sendEmailService = SendEmailServiceFactory.getSendEmailService();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOG.debug("POST search");

        LOG.debug(req.getParameter("sendto"));

        Integer[] idList = map.readValue(req.getParameter("sendto"), Integer[].class);

        List<Contact> contacts = contactService.getContactById(idList);

        LOG.debug(req.getParameter("message"));

        final String subject = req.getParameter("subject");

        ExecutorService exec = Executors.newCachedThreadPool();
        for(Contact contact : contacts){
            ST template = new ST(req.getParameter("message"),'$','$');
            template.add("u", contact);
            final String text = template.render();

            exec.execute(() ->{sendEmailService.sendMessage(ConfigUtil.getGmailUsername(), contact.getEmail(), subject, text);});
          //  sendEmailService.sendMessage(ConfigUtil.getGmailUsername(), contact.getEmail(), subject, text);
        }
        exec.shutdown();
        resp.sendRedirect(req.getContextPath() + "/contactlist?countRow=10&page=0");
        return null;
    }
}

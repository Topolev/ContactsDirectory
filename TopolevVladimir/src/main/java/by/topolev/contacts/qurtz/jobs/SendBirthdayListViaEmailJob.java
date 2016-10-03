package by.topolev.contacts.qurtz.jobs;

import by.topolev.contacts.entity.Contact;
import by.topolev.contacts.services.ContactService;
import by.topolev.contacts.services.ContactServiceFactory;
import by.topolev.contacts.services.SendEmailService;
import by.topolev.contacts.services.SendEmailServiceFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Vladimir on 20.09.2016.
 */
public class SendBirthdayListViaEmailJob implements Job{

    private static final Logger LOG = LoggerFactory.getLogger(SendBirthdayListViaEmailJob.class);

    private ContactService contactService = ContactServiceFactory.getContactService();
    private SendEmailService sendEmailService = SendEmailServiceFactory.getSendEmailService();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOG.debug("Execute SendBirthdayListViaEmailJob");

        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");

        StringBuilder sb = new StringBuilder();
        sb.append("Hello, today is ")
                .append(dateFormat.format(new Date()))
                .append( " !\n")
                .append("List of contacts who're celebraiting today own Birthday.\n");

        List<Contact> listContacts = contactService.getContactListWhoTodayCelebrateBirthday();
        for(Contact contact : listContacts){
            sb.append(contact.getFirstname()).append(" ")
                    .append(contact.getLastname()).append(" ")
                    .append(" birthday: ").append(dateFormat.format(contact.getBirthday())).append("\n");
        }

        LOG.debug(sb.toString());

        //sendEmailService.sendMessage(ConfigUtil.getGmailUsername(), ConfigUtil.getMailAdministrator(), "subject", sb.toString());



    }
}

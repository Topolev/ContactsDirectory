package by.topolev.contacts.qurtz.jobs;

import by.topolev.contacts.config.ConfigUtil;
import by.topolev.contacts.entity.Contact;
import by.topolev.contacts.services.ContactService;
import by.topolev.contacts.services.ContactServiceFactory;
import by.topolev.contacts.services.SendEmailService;
import by.topolev.contacts.services.SendEmailServiceFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static by.topolev.contacts.config.ConfigUtil.getGmailUsername;
import static by.topolev.contacts.config.ConfigUtil.getMailAdministrator;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

/**
 * Created by Vladimir on 20.09.2016.
 */
public class SendBirthdayListViaEmailJob implements Job{

    private static final Logger LOG = LoggerFactory.getLogger(SendBirthdayListViaEmailJob.class);

    private ContactService contactService = ContactServiceFactory.getContactService();
    private SendEmailService sendEmailService = SendEmailServiceFactory.getSendEmailService();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(new Date());

        List<Contact> listContacts = contactService.getContactListWhoTodayCelebrateBirthday();
        String message = getMessage(date, listContacts, isEmpty(listContacts));


        sendEmailService.sendMessage(getGmailUsername(), getMailAdministrator(), "List of persons who celebrate Birthday", message);

        LOG.debug("Execute SendBirthdayListViaEmailJob. Today is {}. Number of people who are celebrating Birtday today: {}", date, isEmpty(listContacts)? "" : listContacts.size());

    }

    private String getMessage(String date ,List<Contact> contacts, boolean isEmptyList){
        STGroup group = new STGroupFile("template/template.stg");
        ST birthday = group.getInstanceOf("birthday");
        birthday.add("contacts", contacts);
        birthday.add("date", date);
        birthday.add("isEmptyList", isEmptyList);
        return birthday.render();


    }
}

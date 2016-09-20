package by.topolev.contacts.services;

/**
 * Created by Vladimir on 20.09.2016.
 */
public class SendEmailServiceFactory {
    public static SendEmailService getSendEmailService(){
        return new SendEmailViaGmailService();
    }
}

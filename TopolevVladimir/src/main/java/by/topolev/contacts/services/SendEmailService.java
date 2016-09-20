package by.topolev.contacts.services;

/**
 * Created by Vladimir on 20.09.2016.
 */
public interface SendEmailService {
    void sendMessage(String emailFrom, String emailTo, String subject, String text);
}

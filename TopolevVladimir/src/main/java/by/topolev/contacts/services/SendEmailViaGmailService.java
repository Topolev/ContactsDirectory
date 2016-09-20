package by.topolev.contacts.services;

/**
 * Created by Vladimir on 18.09.2016.
 */

import by.topolev.contacts.config.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmailViaGmailService implements SendEmailService{

    private static final Logger LOG = LoggerFactory.getLogger(SendEmailViaGmailService.class);

    private Session initSession(){
        String username = ConfigUtil.getGmailUsername();
        String password = ConfigUtil.getGmailPassword();

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");


        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        return session;
    }


    public void sendMessage(String emailFrom, String emailTo, String subject, String text){
        Session session = initSession();
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailFrom));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(emailTo));
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);

            LOG.debug("Sending to email '{}' is successfull.", emailTo);
        } catch(MessagingException e){
            LOG.debug("Sending to email {} is not successful.", e);
        }
    }



/*
    public static void main(String[] args) {
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("topolev.itechart@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("topolevvladimir@mail.ru"));
            message.setSubject("Testing Subject");
            message.setText("Dear Mail Crawler,"
                    + "\n\n No spam to my email, please!");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }*/
}


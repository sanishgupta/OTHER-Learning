package com.dev.mail;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import java.util.Properties;


public class SimpleMail {

    private static final String SMTP_HOST_NAME = "devtwist.local";
    private static final String SMTP_AUTH_USER = "sanish@devtwist.local";
    private static final String SMTP_AUTH_PWD  = "sanish";
    private static final String FROM  = "sanish@devtwist.local";
    private static final String TO  = "test@devtwist.local";

    public static void main(String[] args) throws Exception{
       new SimpleMail().test();
    }

    public void test() throws Exception{
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", SMTP_HOST_NAME);
        props.put("mail.smtp.auth", "true");

        Authenticator auth = new SMTPAuthenticator();
        Session mailSession = Session.getDefaultInstance(props, auth);
        // uncomment for debugging infos to stdout
        // mailSession.setDebug(true);
        Transport transport = mailSession.getTransport();

        MimeMessage message = new MimeMessage(mailSession);
        message.setContent("This is a test", "text/plain");
        message.setFrom(new InternetAddress(FROM));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(TO));
        message.addRecipient(Message.RecipientType.BCC, new InternetAddress(FROM));
      //  message.addRecipient(Message.RecipientType.CC, new InternetAddress(FROM));
        message.setSubject("Test Subject");

        transport.connect();
        //transport.sendMessage(message,message.getRecipients(Message.RecipientType.TO));
        transport.sendMessage(message,message.getAllRecipients());
        transport.close();
    }

    private class SMTPAuthenticator extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
           String username = SMTP_AUTH_USER;
           String password = SMTP_AUTH_PWD;
           return new PasswordAuthentication(username, password);
        }
    }
}
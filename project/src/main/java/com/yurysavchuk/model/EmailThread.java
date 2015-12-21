package com.yurysavchuk.model;

import com.yurysavchuk.dao.mysql.DaoException;
import com.yurysavchuk.dao.mysql.MySqlContactDaoImpl;
import com.yurysavchuk.domain.Contact;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

import javax.mail.BodyPart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.io.StringWriter;
import java.util.List;
import java.util.Properties;


public class EmailThread implements Runnable {

    final Logger log = Logger.getLogger(EmailThread.class);

    public void run() {
        log.info("Method EmailThread.run start");
        List<Contact> contacts = null;
        MySqlContactDaoImpl contactDao = new MySqlContactDaoImpl();
        try {
            contacts = contactDao.getContactsBirthdayToday();
        } catch (DaoException e) {
            log.error(e);
        }
        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("yury.savchuk95@gmail.com", "BwSbsTX2");
                    }
                });


        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("yury.savchuk95@gmail.com"));

            message.setSubject("Happy Birthday");

            BodyPart body = new MimeBodyPart();
            VelocityEngine ve = new VelocityEngine();

            ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH,
                    this.getClass().getClassLoader().getResource("/templates").getPath());
            ve.init();

            for (Contact contact : contacts) {
                message.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(contact.getEmail()));


                VelocityContext context = new VelocityContext();
                context.put("fName", contact.getName());
                context.put("lName", new String(contact.getSurname()));
                Template t = ve.getTemplate("birthday.vm");
                StringWriter out = new StringWriter();
                t.merge(context, out);
                message.setContent(out.toString(), "text/html; charset = UTF-8");

                Transport.send(message);
            }

        } catch (Exception e) {
            log.error(e);
        }

    }


}

package com.yurysavchuk.model;

import com.yurysavchuk.dao.mysql.DaoException;
import com.yurysavchuk.domain.Contact;
import com.yurysavchuk.utiles.MailUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;


import javax.mail.Session;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.PasswordAuthentication;
import java.io.*;
import java.net.URLDecoder;
import java.util.List;
import java.util.Properties;


public class SendEmail extends FrontCommand {

    final Logger log = Logger.getLogger(SendEmail.class);

    public void process() {

        log.info("Method process() start");

        String text = null;
        String theme = request.getParameter("theme");
        log.info("Request parameter theme:"+theme);
        String template = request.getParameter("template");
        log.info("Request parameter template:"+template);
        try {
            text = request.getParameter("text");
            log.info("Request parameter text:"+text);
        } catch (Exception e) {
            log.error(e);
        }

        String[] id_contacts = request.getParameterValues("id");
        log.info("Request parameters id:"+id_contacts.toString());
        List<Contact> contacts = null;

        try {
            contacts = contactDao.getContactsList(id_contacts);
        } catch (DaoException e) {
            log.error(e);
        }
        final Properties prop = System.getProperties();

        try {
            InputStream inputStream = getClass().getResourceAsStream("/mail-settings.properties");
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                log.error("File mail-settings.properties not found");
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException e) {
            log.error(e);
        } catch (IOException e) {
            log.error(e);
        }


        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(prop.getProperty("mail.user"),
                                prop.getProperty("mail.password"));
                    }
                });


        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("yury.savchuk95@gmail.com"));

            message.setSubject(theme);

            BodyPart body = new MimeBodyPart();
            VelocityEngine ve = new VelocityEngine();

            String path = this.getClass().getClassLoader().getResource("/templates").getPath();
            log.info("Path to templates:" + path);
            String path2 = URLDecoder.decode(path, "UTF-8");

            ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, path2);
            ve.init();

            for (Contact contact : contacts) {
                message.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(contact.getEmail()));
                if (StringUtils.isNotEmpty(MailUtils.getTemplateName(template))) {
                    log.info("Message is template");

                    VelocityContext context = new VelocityContext();
                    context.put("fName", contact.getName());
                    context.put("lName", contact.getSurname());
                    Template t = ve.getTemplate(MailUtils.getTemplateName(template), "UTF-8");
                    StringWriter out = new StringWriter();
                    t.merge(context, out);
                    message.setContent(out.toString(), "text/html; charset=UTF-8");
                } else {
                    log.info("Message is simple text");
                    message.setText(text);
                }
                Transport.send(message);
            }
            request.setAttribute("id_contacts", id_contacts);
            request.setAttribute("result", "Message was sent successfully!");
            forward("/createRequestEmail.do");
        } catch (Exception e) {
            log.error(e);
        }

    }
}

package com.yurysavchuk.model;

import com.yurysavchuk.domain.Contact;
import org.apache.log4j.Logger;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class CreateEmailRequest extends FrontCommand {

    final Logger log = Logger.getLogger(CreateEmailRequest.class);

    public void process() {

        log.info("Process() start");

        try {
            String[] id_cont = (String[]) request.getAttribute("id_contacts");
            log.info("Id_contacts:" + id_cont.toString());
            List<String> id_contacts = Arrays.asList(id_cont);

            URL url = this.getClass().getClassLoader().getResource("/templates");
            log.info("URL templates path:" + url.toString());
            File file1 = null;
            try {
                file1 = new File(url.toURI());
            } catch (URISyntaxException e) {
                file1 = new File(url.getPath());
            }
            File[] files = file1.listFiles();
            log.info("Count of files:" + files.length);
            List<String> templateNames = new LinkedList<>();
            for (File file : files) {
                templateNames.add(file.getName());
            }
            templateNames.add("");

            List<Contact> contacts = contactDao.getContactsList(id_cont);
            request.setAttribute("contacts", contacts);

            request.setAttribute("id_contacts", id_contacts.toString());

            request.setAttribute("templateNames", templateNames);

            forward("/emailForm.jsp");
        } catch (Exception e) {
            log.error(e);
        }


    }


}

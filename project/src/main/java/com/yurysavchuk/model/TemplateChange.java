package com.yurysavchuk.model;


import com.yurysavchuk.domain.Contact;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;


import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.nio.charset.StandardCharsets;

public class TemplateChange extends FrontCommand {

    final Logger log = Logger.getLogger(TemplateChange.class);

    public void process() {

        log.info("Method process() start");

        String id_contacts = request.getParameter("id");
        log.info("Request parameter id: "+id_contacts);
        String theme = request.getParameter("theme");
        log.info("Request parameter theme: "+theme);
        String[] id_cont = id_contacts.substring(1, id_contacts.length() - 1).split(",");

        String template = request.getParameter("template");
        log.info("Request parameter: "+template);

        String contentTemplate = null;

        URL url = this.getClass().getClassLoader().getResource("/templates");
        log.info("Templates path: "+url.toString());
        File file1 = null;
        try {
            file1 = new File(url.toURI());
        } catch (URISyntaxException e) {
            file1 = new File(url.getPath());
        }
        File[] files = file1.listFiles();


        List<String> templateNames = new LinkedList<>();
        for (File file : files) {
            templateNames.add(file.getName());
        }
        log.info("Templates names:"+templateNames.toString());
        templateNames.add("");
        int ind = templateNames.indexOf(template);
        String buf = templateNames.get(0);
        templateNames.set(0, template);
        templateNames.set(ind, buf);

        try {

            List<Contact> contacts = contactDao.getContactsList(id_cont);

            VelocityEngine ve = new VelocityEngine();

            if (StringUtils.isNotEmpty(template)) {



                String path = this.getClass().getClassLoader().getResource("/templates").getPath();
                log.info("Path to templates:" + path);
//                PrintWriter writer = new PrintWriter(new File(path),"UTF-8");
//                String path2 = writer.toString();
                String path2 = URLDecoder.decode(path, "UTF-8");

                ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, path2);
                ve.init();
                VelocityContext context = new VelocityContext();

                log.info("Try to get template: "+template);


                Template t = ve.getTemplate(template);
                StringWriter out = new StringWriter();
                t.merge(context, out);
                contentTemplate = out.toString();
            } else {
                contentTemplate = "";
            }

            request.setAttribute("template", contentTemplate);
            request.setAttribute("contacts", contacts);

            request.setAttribute("id_contacts", id_contacts.toString());

            request.setAttribute("templateNames", templateNames);
            request.setAttribute("theme", theme);

            forward("/emailForm.jsp");
        } catch (Exception e) {
            log.error(e);
        }
    }
}

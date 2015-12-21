package com.yurysavchuk.controller;

import com.yurysavchuk.model.*;

import static java.util.concurrent.TimeUnit.*;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.HOURS;

/**
 * Created by HP on 15.11.2015.
 */
public class FrontServlet extends HttpServlet {

    static final Map<String, FrontCommand> comandsMap = new HashMap<String, FrontCommand>();

    static {
        comandsMap.put("create", new CreateContact());
        comandsMap.put("viewAllContacts", new ViewListContacts());
        comandsMap.put("edit", new EditContact());
        comandsMap.put("search", new SearchContacts());
        comandsMap.put("createNewContact", new CreateNewContact());
        comandsMap.put("deleteContact", new DeleteContact());
        comandsMap.put("sendEmail", new SendEmail());
        comandsMap.put("createRequestEmail", new CreateEmailRequest());
        comandsMap.put("uploadFile", new UploadFile());
        comandsMap.put("templateChange", new TemplateChange());
    }

    private ScheduledExecutorService service =
            Executors.newScheduledThreadPool(1);

    protected final Logger log = Logger.getLogger(FrontServlet.class);

    @Override
    public void init() throws ServletException {


        Runnable run = new EmailThread();
        Thread thread = new Thread(run);

        service.scheduleAtFixedRate(thread, 0, 24, HOURS);

    }

    @Override
    public void destroy() {
        super.destroy();
        try {
            log.info("Try to finish executorservice");
            service.shutdown();
            log.info("Executor service was finished");
        } catch (Exception e) {
            log.error(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FrontCommand command = null;

        try {
            log.info("Method doPost start: " + request.getRequestURI());
            command = getCommand(request);
            command.init(getServletContext(), request, response);
            command.process();

        } catch (Exception e) {
            log.error(e);
        }
    }

    private FrontCommand getCommand(HttpServletRequest request) {
        FrontCommand result = null;
        String uri = request.getRequestURI();
        log.info("Request uri: "+uri);
        uri = uri.substring(1);
        log.info("Request uri: "+uri);
        int ind = uri.indexOf(".");
         uri= uri.substring(0, ind);
         ind = uri.indexOf('/');
        String command = uri.substring(ind+1);
        log.info("Command: "+command);

        try {
            result = comandsMap.get(command);

        } catch (Exception e) {
            log.error("getCommand " + e);
        }
        return result;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FrontCommand command = null;

        try {
            log.info("Method doGet start: " + request.getRequestURL());
            command = getCommand(request);
            command.init(getServletContext(), request, response);
            command.process();

        } catch (Exception e) {
            log.error(e);
        }
    }
}

package com.yurysavchuk.model;

import org.apache.commons.fileupload.FileItem;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class UploadFile extends FrontCommand {

    final Logger log = Logger.getLogger(UploadFile.class);

    private final int MEMORY_THRESHOLD = 2 * 1024 * 1024;
    private final int MAX_FILE_SIZE = 10 * 1024 * 1024;
    private final int MAX_REQUEST_SIZE = 1024 * 1024 * 10;
    final private String UPLOAD_DIRECTORY = "uploadFiles";

    public void process() {

        log.info("Method process() start");

        if (!ServletFileUpload.isMultipartContent(request)) {
            try {
                PrintWriter writer = response.getWriter();
                writer.println("Error: form must has enctype=multipart/form-date");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        ServletFileUpload upload = new ServletFileUpload(factory);


        upload.setFileSizeMax(MAX_FILE_SIZE);

        upload.setSizeMax(MAX_REQUEST_SIZE);

        String uploadPath = context.getRealPath("") + File.separator + UPLOAD_DIRECTORY;

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        try {
            @SuppressWarnings("unchecked")
            List<FileItem> formitems = upload.parseRequest(request);

            if (formitems != null && formitems.size() > 0) {
                for (FileItem item : formitems) {
                    String fileName = new File(item.getName()).getName();
                    String filePath = uploadPath + File.separator + fileName;
                    File storeFile = new File(filePath);

                    //save file on disk
                    item.write(storeFile);
                    request.setAttribute("message", "Upload has been done successfully");
                }
            }
        } catch (Exception e) {
            log.error(e);
        }
        try {
            context.getRequestDispatcher("/message.jsp").forward(request, response);
        } catch (Exception e) {
            log.error(e);
        }


    }
}

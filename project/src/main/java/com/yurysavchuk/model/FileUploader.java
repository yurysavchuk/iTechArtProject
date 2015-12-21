package com.yurysavchuk.model;

import com.yurysavchuk.domain.Contact;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;

import javax.servlet.ServletContext;
import java.io.File;



public class FileUploader {

    private static final int MEMORY_THRESHOLD = 2 * 1024 * 1024;
    private static final int MAX_FILE_SIZE = 10 * 1024 * 1024;
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 10;
    final static private String UPLOAD_DIRECTORY_FILE = "uploadFiles";
    final static private String UPLOAD_DIRECTORY_IMG = "uploadImages";
    final static Logger log = Logger.getLogger(FileUploader.class);

    public static ServletFileUpload init() {

        ServletFileUpload upload = null;

        log.info("Method init() start");
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(MEMORY_THRESHOLD);
            factory.setRepository(new java.io.File(System.getProperty("java.io.tmpdir")));

            upload = new ServletFileUpload(factory);

            upload.setFileSizeMax(MAX_FILE_SIZE);

            upload.setSizeMax(MAX_REQUEST_SIZE);
        } catch (Exception e) {
            log.info(e);
        } finally {
            return upload;
        }

    }

    public static com.yurysavchuk.domain.File uploadFile(ServletContext context, Contact contact, String fileName,
                                                         FileItem item) {

        log.info("Method uploadFile start:"+context+","+contact+","+fileName);

        com.yurysavchuk.domain.File tempFile = new com.yurysavchuk.domain.File();
        try {

            String uploadPath = context.getRealPath("") + java.io.File.separator + UPLOAD_DIRECTORY_FILE;


            File uploadDir = new java.io.File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String filePath = uploadPath + java.io.File.separator + contact.getId() + "_" + fileName;
            log.info("Path for loading:"+filePath);

            java.io.File storeFile = new java.io.File(filePath);

            //save file on disk

            item.write(storeFile);
            tempFile.setPath(filePath);
            tempFile.setFilename(fileName);
            log.info("Filename:"+fileName);

            LocalDate date = LocalDate.now();
            tempFile.setId(0);
            tempFile.setDateLoad(date.toString());
        } catch (Exception e) {
            log.error(e);
        }
        return tempFile;
    }

    public static void uploadPhoto(Contact contact, ServletContext context, String fileName, FileItem item) {
        String uploadPath2 = null;

        log.info("Method uploadPhoto start");

        try {
            String uploadPath = context.getRealPath("") + java.io.File.separator + UPLOAD_DIRECTORY_IMG;
            log.info("UploadPath:"+uploadPath);
            uploadPath2 = context.getContextPath() + java.io.File.separator + UPLOAD_DIRECTORY_IMG;
            log.info("Display path:"+uploadPath2);

            File uploadDir = new java.io.File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String filePath = uploadPath + java.io.File.separator + contact.getId() + "_" + fileName;
            log.info("FilePath:"+filePath);
            String filePath2 = uploadPath2 + java.io.File.separator + contact.getId() + "_" + fileName;
            log.info("Relative path:"+filePath2);

            java.io.File storeFile = new java.io.File(filePath);

            item.write(storeFile);
            contact.setPathToImg(filePath2);
        } catch (Exception e) {
            log.error(e);
        }

    }


}

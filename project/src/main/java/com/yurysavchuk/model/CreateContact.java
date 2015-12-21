package com.yurysavchuk.model;

import com.yurysavchuk.dao.mysql.MySqlDaoService;
import com.yurysavchuk.domain.Address;
import com.yurysavchuk.domain.Contact;
import com.yurysavchuk.domain.File;
import com.yurysavchuk.domain.PhoneNumber;
import com.yurysavchuk.utiles.AddressParser;
import com.yurysavchuk.utiles.FileParser;
import com.yurysavchuk.utiles.PhoneNumberParser;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.fileupload.FileItem;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;


import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class CreateContact extends FrontCommand {
    final Logger log = Logger.getLogger(CreateContact.class);


    public void process() {

        log.info("Process() start");
        Contact contact = new Contact();
        Address address = new Address();
        List<PhoneNumber> numberList = new LinkedList<>();
        List<File> fileList = new LinkedList<>();
        File tempFile = new File();

        StringBuilder deletedPhones = new StringBuilder();
        StringBuilder deletedFiles = new StringBuilder();

        StringBuilder fileEditPath = new StringBuilder();

        String nextPage = null;

        StringBuilder command = new StringBuilder();
        try {

            //upload file
            if (!ServletFileUpload.isMultipartContent(request)) {
                log.info("request is not multipart");
            }

            ServletFileUpload upload = FileUploader.init();
            try {
                @SuppressWarnings("unchecked")
                List<FileItem> formitems = upload.parseRequest(request);
//                if (CollectionUtils.isNotEmpty(formitems)) {
                    if(formitems!=null && formitems.size()>0){
                    for (FileItem item : formitems) {
                        if (!item.isFormField()) {
                            String fileName = new java.io.File(item.getName()).getName();
                            if (StringUtils.isNotBlank(fileName)) {

                                String mimeType = context.getMimeType(fileName);
                                if (mimeType.startsWith("image/")) {
                                    log.info("Upload image");
                                    FileUploader.uploadPhoto(contact, context, fileName, item);
                                } else {
                                    log.info("Upload file");
                                    tempFile = FileUploader.uploadFile(context, contact, fileName, item);
                                }
                            }

                        } else {
                            String fieldname = item.getFieldName();
                            String fieldvalue = item.getString("UTF-8");
                            setValue(fieldname, fieldvalue, contact, address, numberList, fileList, command, tempFile,
                                    fileEditPath, deletedPhones, deletedFiles, item);
                        }
                    }

                }
                log.info("Collection formitems is empty");
            } catch (Exception e) {
                log.error(e);
            }
            if (command.toString().equals("create")) {

                log.info("Create new contact");
                contact.setAddress(address);
                contact.setListFile(fileList);
                contact.setPhoneNumbers(numberList);
                new MySqlDaoService().addContact(contact, deletedPhones.toString(), deletedFiles.toString());
                nextPage = "/viewAllContacts.do";

            } else if (command.toString().equals("save")) {
                if (StringUtils.isNotBlank(fileEditPath)) {
                    log.info("Edit file");
                    if (StringUtils.isBlank(tempFile.getPath())) {
                        log.info("Edit only files comment");
                        for (File file : fileList) {
                            if (file.getPath().equals(fileEditPath.toString())) {
                                file.setComment(tempFile.getComment());
                            }
                        }
                    } else {
                        log.info("Delete file");
                        for (File file : fileList) {
                            if (file.getPath().equals(fileEditPath.toString())) {
                                java.io.File file2 = new java.io.File(file.getPath());
                                file2.delete();
                                file.setFilename(tempFile.getFilename());
                                file.setPath(tempFile.getPath());
                                file.setDateLoad(tempFile.getDateLoad());
                                file.setComment(tempFile.getComment());
                                file.setId(tempFile.getId());
                            }
                        }
                    }
                } else {
                    log.info("Add file");
                    fileList.add(tempFile);
                }
                nextPage = "/createEditContact.jsp";
            } else if (command.toString().equals("delete")) {

                log.info("Delete file");
                java.io.File file = new java.io.File(fileEditPath.toString());
                file.delete();
                ListIterator<File> iter = fileList.listIterator();
                while (iter.hasNext()) {
                    File fileBuf = iter.next();
                    if (fileBuf.getPath().equals(fileEditPath.toString())) {
                        deletedFiles.append(fileBuf.getId() + ",");
                        iter.remove();
                        break;
                    }
                }

                nextPage = "/createEditContact.jsp";
            } else if (command.toString().equals("savePhoto")) {
                nextPage = "/createEditContact.jsp";
            }

            contact.setAddress(address);
            contact.setListFile(fileList);
            contact.setPhoneNumbers(numberList);
            request.setAttribute("contact", contact);
            request.setAttribute("deletedFiles", deletedFiles.toString());

            forward(nextPage);

        } catch (Exception e) {
            log.error(e);
        }
    }


    private void setValue(String fieldname, String fieldvalue, Contact contact, Address address,
                          List<PhoneNumber> numberList, List<File> fileList, StringBuilder command,
                          File tempFile, StringBuilder fileEditPath, StringBuilder deletedPhones,
                          StringBuilder deletedFiles, FileItem item) {

        log.info("SetValue start:"+fieldname+","+ ","+ contact+","+address+
                ","+numberList+"," +fileList+","+ command+","+
                 tempFile +","+  fileEditPath+","+  deletedPhones+","+
                 deletedFiles+","+ item);

        try {
            switch (fieldname) {
                case "id_contact":
                    if (StringUtils.isNotBlank(fieldvalue)) {
                        contact.setId(Integer.valueOf(fieldvalue.trim()));
                    } else {
                        contact.setId(0);
                    }
                    break;
                case "name":
                    contact.setName(fieldvalue);
                    break;
                case "surname":
                    contact.setSurname(fieldvalue);
                    break;
                case "mName":
                    contact.setMidleName(fieldvalue);
                    break;
                case "birthday":
                    if (StringUtils.isNotBlank(fieldvalue)) {
                        contact.setBirthday(fieldvalue);
                    } else {
                        contact.setBirthday(null);
                    }

                    break;
                case "sex":
                    contact.setSex(fieldvalue);
                    break;
                case "nationality":
                    contact.setNationality(fieldvalue);
                    break;
                case "mrtlStat":
                    contact.setMaritStat(fieldvalue);
                    break;
                case "webSite":
                    contact.setWebSite(fieldvalue);
                    break;
                case "email":
                    contact.setEmail(fieldvalue);
                    break;
                case "workplace":
                    contact.setCurWorkplace(fieldvalue);
                    break;
                case "country":
                    address.setCountry(fieldvalue);
                    break;
                case "city":
                    address.setCity(fieldvalue);
                    break;
                case "address":
                    address.setStreet(AddressParser.getStreet(fieldvalue));
                    address.setHouse(AddressParser.getHouse(fieldvalue));
                    address.setFlat(AddressParser.getFlat(fieldvalue));
                    break;
                case "index":
                    if (StringUtils.isNotBlank(fieldvalue)) {
                        address.setIndex(Integer.valueOf(fieldvalue));
                    } else {
                        address.setId(0);
                    }
                    break;
                case "phone":
                    if (StringUtils.isNotBlank(fieldvalue)) {
                        numberList.add(PhoneNumberParser.getPhoneNumber(fieldvalue));
                    }
                    break;
                case "file":
                    String file = item.getString();
                    if (StringUtils.isNotBlank(file)) {
                        fileList.add(FileParser.getFile(fieldvalue));
                    }
                    break;
                case "id_address":
                    if (StringUtils.isNotBlank(fieldvalue)) {
                        address.setId(Integer.valueOf(fieldvalue.trim()));
                    } else {
                        address.setId(0);
                    }
                    break;
                case "pathToImg":
                    if (StringUtils.isNotBlank(fieldvalue)) {
                        contact.setPathToImg(fieldvalue);
                    }
                    break;
                case "commentFile":
                    tempFile.setComment(fieldvalue);
                    break;
                case "command":
                    command.append(fieldvalue);
                    break;
                case "checkFile":
                    fileEditPath.append(fieldvalue);
                    break;
                case "deletedPhones":
                    if (StringUtils.isNotEmpty(fieldvalue)) {

                        deletedPhones.append(fieldvalue);
                    }
                    break;
                case "deletedFiles":
                    if (StringUtils.isNotEmpty(fieldvalue)) {

                        deletedFiles.append(fieldvalue);
                    }
                    break;
            }
        } catch (Exception e) {
            log.error(e);
        }

    }
}
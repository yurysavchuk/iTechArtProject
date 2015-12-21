package com.yurysavchuk.model;

import com.yurysavchuk.dao.mysql.MySqlContactDaoImpl;
import com.yurysavchuk.domain.Contact;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;


public class DeleteContact extends FrontCommand {

    final Logger log = Logger.getLogger(DeleteContact.class);

    public void process() {

        log.info("Process() start");

        try {
            String[] id_contacts = request.getParameterValues("idContact");
            log.info("Id_contacts:"+id_contacts.toString());
            String action = request.getParameter("submit");
            log.info("Command:"+action);

            if (action.equals("Edit")) {

                log.info("Edit contact");
                Integer id_contact = Integer.valueOf(id_contacts[0]);
                Contact contact = new MySqlContactDaoImpl().getContact(id_contact);
                request.setAttribute("contact", contact);
                forward("/createEditContact.jsp");

            } else if (action.equals("Delete")) {
                log.info("Delete contact");

                List<Contact> contactList = new LinkedList<>();
                if (ArrayUtils.isNotEmpty(id_contacts)) {
                    for (String id : id_contacts) {
                        contactList.add(new MySqlContactDaoImpl().getContact(Integer.valueOf(id)));
                    }
                }
                contactDao.deleteContact(contactList);
            } else {
                log.info("Send email");
                request.setAttribute("id_contacts", id_contacts);
                forward("/createRequestEmail.do");
            }
            forward("/viewAllContacts.do");
        } catch (Exception e) {
            log.error(e);
        }
    }

}

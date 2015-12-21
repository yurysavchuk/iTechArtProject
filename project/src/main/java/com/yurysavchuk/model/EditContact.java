package com.yurysavchuk.model;


import com.yurysavchuk.dao.mysql.MySqlContactDaoImpl;
import com.yurysavchuk.domain.Contact;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;


public class EditContact extends FrontCommand {

    final Logger log = Logger.getLogger(EditContact.class);

    public void process(){
        log.info("Process() start");
        try{
            Integer id = Integer.valueOf(request.getParameter("id"));
            log.info("Id contact:"+id);

            Contact contact = new MySqlContactDaoImpl().getContact(id);
            request.setAttribute("contact", contact);

            forward("/createEditContact.jsp");
        }catch (Exception e){
            log.error(e);
        }
    }
}

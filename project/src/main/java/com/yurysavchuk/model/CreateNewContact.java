package com.yurysavchuk.model;

import org.apache.log4j.Logger;


public class CreateNewContact extends FrontCommand {

    final Logger log = Logger.getLogger(CreateEmailRequest.class);

    public void process() {
        try {
            forward("/createEditContact.jsp");
        } catch (Exception e) {
            log.error(e);
        }
    }
}

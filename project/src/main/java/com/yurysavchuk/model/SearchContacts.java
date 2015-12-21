package com.yurysavchuk.model;


import com.yurysavchuk.dao.mysql.MySqlContactDaoImpl;
import com.yurysavchuk.domain.Contact;
import com.yurysavchuk.utiles.AddressParser;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

public class SearchContacts extends FrontCommand {

    final Logger log = Logger.getLogger(SearchContacts.class);

    public void process() {

        log.info("Method process start");

        try {

            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            String midlename = request.getParameter("mName");
            String sex = request.getParameter("sex");
            String maritStat = request.getParameter("mrtlStat");
            String nation = request.getParameter("national");
            String birthday = request.getParameter("bDay");

            String country = request.getParameter("country");
            String city = request.getParameter("city");
            String address = request.getParameter("address");
            String street = AddressParser.getStreet(address);
            String house = AddressParser.getHouse(address);
            Integer flat = AddressParser.getFlat(address);
            Integer index = 0;
            try {
                index = Integer.valueOf(request.getParameter("index"));
            } catch (Exception e) {
                log.error(e);
            }

            List<Contact> contacts = new MySqlContactDaoImpl().getContactsBySearch(name, surname, midlename, sex, maritStat, nation,
                    birthday, country, city, street, house, flat, index);

            request.setAttribute("currentPage", 1);
            request.setAttribute("contacts", contacts);
            forward("/displayContacts.jsp");
        } catch (Exception e) {
            log.error(e);
        }


    }
}

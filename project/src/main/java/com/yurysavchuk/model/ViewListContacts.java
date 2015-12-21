package com.yurysavchuk.model;


import com.yurysavchuk.dao.mysql.MySqlContactDaoImpl;
import com.yurysavchuk.domain.Contact;
import org.apache.log4j.Logger;


import java.util.List;


public class ViewListContacts extends FrontCommand {

    final Logger log = Logger.getLogger(ViewListContacts.class);

    public void process() {
        try {
            int page = 1;
            int recordsPerPage = 10;
            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
                log.info("Page: " + page);
            }
            MySqlContactDaoImpl contactDao = new MySqlContactDaoImpl();
            List<Contact> list = contactDao.getListOfContacts((page - 1) * recordsPerPage,
                    recordsPerPage);
            int noOfRecords = contactDao.getNoOfRecords();
            log.info("NoOfRecords:" + noOfRecords);
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
            log.info("NoOfPages:" + noOfPages);

            request.setAttribute("contacts", list);
            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", page);
            forward("/displayContacts.jsp");
        } catch (Exception e) {
            log.error(e);
        }


    }
}

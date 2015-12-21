package com.yurysavchuk.dao.mysql;

import com.yurysavchuk.dao.ConnectionManager;
import com.yurysavchuk.domain.Contact;
import com.yurysavchuk.domain.File;
import com.yurysavchuk.domain.PhoneNumber;
import org.apache.log4j.Logger;

import java.sql.Connection;

public class MySqlDaoService {

    final Logger log = Logger.getLogger(MySqlFilesDaoImpl.class);
    private static final MySqlAddressDaoImpl addressDao = new MySqlAddressDaoImpl();
    private static final MySqlContactDaoImpl contactDao = new MySqlContactDaoImpl();
    private static final MySqlNumbersDaoImpl numberDao = new MySqlNumbersDaoImpl();
    private static final MySqlFilesDaoImpl fileDao = new MySqlFilesDaoImpl();

    public void addContact(Contact contact, String deletedPhones,
                           String deletedFiles) throws DaoException {

        log.info("Start method addContact");

        try (Connection connection = ConnectionManager.getConnection()) {

            connection.setAutoCommit(false);
            try {
                int id_address = 0;
                int id_contact = 0;

                if (contact.getAddress().getId() != 0) {
                    addressDao.updateAddress(connection, contact.getAddress());
                } else {
                    id_address = addressDao.addAddress(connection, contact.getAddress());
                    contact.getAddress().setId(id_address);
                }
                if (contact.getId() != 0) {
                    contactDao.updateContact(connection, contact);
                } else {
                    id_contact = contactDao.addContact(connection, contact);
                    contact.setId(id_contact);
                }

                for (PhoneNumber ph : contact.getPhoneNumbers()) {
                    if (ph.getId() != 0) {
                        numberDao.updatePhoneList(connection, ph, contact.getId());
                    } else {
                        numberDao.addPhoneList(connection, ph, contact.getId());
                    }
                }

                for (File file : contact.getListFile()) {
                    if (file.getId() != 0) {
                        fileDao.updateFile(connection, file, contact.getId());
                    } else if (file.getId() == 0) {
                        fileDao.addFile(connection, file, contact.getId());
                    }
                }

                while (!deletedFiles.isEmpty()) {
                    int ind = deletedFiles.indexOf(',');
                    int buf = Integer.valueOf(deletedFiles.substring(0, ind));
                    fileDao.deleteFile(connection, Integer.valueOf(buf));
                    deletedFiles = deletedFiles.substring(ind + 1);
                }

                while (!deletedPhones.isEmpty()) {
                    int ind = deletedPhones.indexOf(',');
                    int buf = Integer.valueOf(deletedPhones.substring(0, ind));
                    numberDao.deleteNumber(connection, Integer.valueOf(buf));
                    deletedPhones = deletedPhones.substring(ind + 1);
                }
                log.info("Commit transaction");
                connection.commit();
            } catch (Exception e) {
                log.error(e);
                log.info("Rollback transaction");
                connection.rollback();
            }
        } catch (Exception e) {
            log.error(e);
            throw new DaoException("cann't add contact", e);
        }
    }
}

package com.yurysavchuk.dao.mysql;

import com.yurysavchuk.dao.ConnectionManager;
import com.yurysavchuk.domain.Address;
import com.yurysavchuk.domain.Contact;
import com.yurysavchuk.domain.File;
import com.yurysavchuk.domain.PhoneNumber;

import com.yurysavchuk.model.EditContact;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;


import java.sql.*;

import java.util.*;


public class MySqlContactDaoImpl {

    final Logger log = Logger.getLogger(MySqlContactDaoImpl.class);

    public void deleteContact(List<Contact> contacts) throws DaoException {

        String DELETE_ADDRESS = "DELETE FROM address WHERE id_address=?";
        String DELETE_CONTACT = "DELETE FROM contacts WHERE id_contact=?";
        String DELETE_FILE = "DELETE FROM attachfiles WHERE id_file=?";
        String DELETE_NUMBER = "DELETE FROM phonenumbers WHERE id_number=?";

        log.info("Method deleteContact(" + contacts + ") start");

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement delete_address = connection.prepareStatement(DELETE_ADDRESS);
             PreparedStatement delete_contact = connection.prepareStatement(DELETE_CONTACT);
             PreparedStatement delete_file = connection.prepareStatement(DELETE_FILE);
             PreparedStatement delete_number = connection.prepareStatement(DELETE_NUMBER)) {


            for (Contact contact : contacts) {

                delete_address.setInt(1, contact.getAddress().getId());
                delete_contact.setInt(1, contact.getId());

                log.info("Set autocommit false");
                connection.setAutoCommit(false);
                try {
                    for (PhoneNumber ph : contact.getPhoneNumbers()) {
                        delete_number.setInt(1, ph.getId());
                        delete_number.executeUpdate();
                    }
                    for (File file : contact.getListFile()) {
                        delete_file.setInt(1, file.getId());
                        delete_file.executeUpdate();
                    }
                    delete_contact.executeUpdate();
                    delete_address.executeUpdate();
                    log.info("Commit transaction");
                    connection.commit();
                } catch (Exception e) {
                    log.error(e);
                    log.info("Rollback transaction");
                    connection.rollback();
                }

            }

        } catch (Exception e) {
            log.error(e);
            throw new DaoException("Cann't deleteContact", e);
        }

    }

    public List<Contact> getContactsList(String[] id_contacts) throws DaoException {
        String get_contact_query = "SELECT * FROM contacts WHERE id_contact=?";
        List<Contact> contacts = new LinkedList<>();
        Contact contact = new Contact();

        log.info("Method getContactsList(" + id_contacts + ") start");

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(get_contact_query)) {

            log.info("Method connected to db");

            for (String id : id_contacts) {
                id = id.trim();
                statement.setInt(1, Integer.valueOf(id));

                log.info("Execute query: " + get_contact_query);
                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        contact.setId(rs.getInt("id_contact"));
                        contact.setName(rs.getString("name"));
                        contact.setSurname(rs.getString("surname"));
                        contact.setMidleName(rs.getString("midleName"));
                        contact.setBirthday(rs.getString("birthday"));
                        contact.setEmail(rs.getString("email"));
                        contact.setWebSite(rs.getString("webSite"));
                        contact.setCurWorkplace(rs.getString("curWorkplace"));
                        contact.setNationality(rs.getString("nationality"));
                        contact.setSex(rs.getString("sex"));
                        contact.setMaritStat(rs.getString("maritStat"));
                        contact.setPathToImg(rs.getString("imgPath"));

                        contacts.add(contact);
                    }
                }
            }

        } catch (Exception e) {
            log.error(e);
            throw new DaoException("Cann't get contactsList", e);
        }

        return contacts;
    }


    public List<Contact> getContactsBirthdayToday() throws DaoException {

        log.info("Method getContactsBirthdayToday() start");


        String get_contacts = "SELECT * FROM contacts WHERE MONTH(birthday)=MONTH(NOW()) AND DAY(birthday)=DAY(NOW())";

        List<Contact> contacts = new LinkedList<>();

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(get_contacts)) {

            log.info("Method conected to db");
            log.info("Execute query: " + get_contacts);
            statement.execute();
            try (ResultSet rs = statement.getResultSet()) {

                while (rs.next()) {
                    Contact contact = new Contact();
                    contact.setId(rs.getInt("id_contact"));
                    contact.setSurname(rs.getString("surname"));
                    contact.setName(rs.getString("name"));
                    contact.setMidleName(rs.getString("midleName"));
                    contact.setMaritStat(rs.getString("maritStat"));
                    contact.setSex(rs.getString("sex"));
                    contact.setEmail(rs.getString("email"));
                    contact.setNationality(rs.getString("nationality"));
                    contact.setCurWorkplace(rs.getString("curWorkplace"));
                    contact.setWebSite(rs.getString("webSite"));
                    contact.setBirthday(rs.getString("birthday"));
                    contact.setPathToImg(rs.getString("imgPath"));

                    contacts.add(contact);
                }
            }

        } catch (Exception e) {
            log.error(e);
            throw new DaoException("Cann't get contact", e);
        }

        return contacts;
    }

    public List<Contact> getListOfContacts(int offset, int noOfRecords) throws DaoException {


        String query = "select SQL_CALC_FOUND_ROWS * from contacts limit ?, ?";

        log.info("Method getListOfContacts(" + offset + "," + noOfRecords + ")");

        List<Contact> contacts = new LinkedList<>();

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setInt(1, offset);
            statement.setInt(2, noOfRecords);
            log.info("Execute query: " + query);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Contact contact = new Contact();

                    contact.setId(rs.getInt("id_contact"));
                    contact.setName(rs.getString("name"));
                    contact.setSurname(rs.getString("surname"));
                    contact.setMidleName(rs.getString("midleName"));
                    contact.setBirthday(rs.getString("birthday"));
                    contact.setSex(rs.getString("sex"));
                    contact.setNationality(rs.getString("nationality"));
                    contact.setMaritStat(rs.getString("maritStat"));
                    contact.setWebSite(rs.getString("webSite"));
                    contact.setEmail(rs.getString("email"));
                    contact.setCurWorkplace(rs.getString("curWorkplace"));
//                     contact.getAddress().setId(rs.getInt("id_address"));

                    Address address = new MySqlAddressDaoImpl().getAddress(contact.getId());
                    contact.setAddress(address);

                    contacts.add(contact);
                }
            }
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException("Cann't get list of contacts");
        }
        return contacts;
    }

    public int getNoOfRecords() {

        log.info("Method getNoOfRecords start");
        int noOfRecords = 0;
        String query = "SELECT COUNT(*) FROM contacts";
        try (Connection connection = ConnectionManager.getConnection();
             Statement statement = connection.createStatement()) {
            log.info("Method connected to db");
            log.info("Execute query: " + query);

            try (ResultSet rs = statement.executeQuery(query)) {
                if (rs.next())
                    noOfRecords = rs.getInt(1);
            }

        } catch (Exception e) {
            log.error(e);
        }
        return noOfRecords;
    }


    public List<Contact> getContactsBySearch(String name, String surname, String midlename, String sex,
                                             String maritStat, String nation, String birthday, String country,
                                             String city, String street, String house, Integer flat, Integer index)
            throws DaoException {

        log.info("Method getContactsBySearch start: ");
        StringBuilder query = new StringBuilder("SELECT * FROM contacts INNER JOIN address " +
                " ON contacts.id_address = address.id_address WHERE ");
        String select_address = "SELECT * FROM address WHERE id_address = ?";

        List<Object> list = new ArrayList<>();

        if (org.apache.commons.lang3.StringUtils.isNotEmpty(name)) {
            query.append(" name Like  ?  AND ");
            list.add(name);
        }
        if (org.apache.commons.lang3.StringUtils.isNotBlank(surname)) {
            query.append(" surname LIKE ? AND ");
            list.add(surname);
        }
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(midlename)) {
            query.append(" midleName LIKE  ?  AND ");
            list.add(midlename);
        }
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(sex)) {
            query.append(" sex LIKE  ?  AND ");
            list.add(sex);
        }
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(nation)) {
            query.append(" nationality LIKE  ?  AND ");
            list.add(nation);
        }
        if (StringUtils.isNotBlank(birthday)) {
            query.append(" birthday LIKE ? AND ");
            list.add(birthday);
        }
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(maritStat)) {
            query.append(" maritStat LIKE  ?  AND ");
            list.add(maritStat);
        }
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(country)) {
            query.append(" country LIKE ? AND ");
            list.add(country);
        }
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(city)) {
            query.append(" city LIKE  ? AND ");
            list.add(city);
        }
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(street) && org.apache.commons.lang3.StringUtils.isNotEmpty(house) && flat != 0) {
            query.append("street LIKE  ?  AND house LIKE  ?  AND  flat = ? AND ");
            list.add(street);
            list.add(house);
            list.add(flat);
        }
        if (index != 0) {
            query.append(" ind=  ? AND");
            list.add(index);
        }

        String result = null;
        int ind = query.lastIndexOf("AND");
        if (ind != -1) {
            result = query.substring(0, ind).toString();

        }
        List<Contact> contacts = new LinkedList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement pst = connection.prepareStatement(result);
             PreparedStatement get_address = connection.prepareStatement(select_address)) {

            log.info("Connected to database");

            for (int i = 0; i < list.size(); i++) {
                String s = list.get(i).getClass().getName();
                if (list.get(i).getClass().getName().equals("java.lang.String")) {
                    pst.setString(i + 1, list.get(i).toString());
                }
                if (list.get(i).getClass().getName().equals("java.lang.Integer")) {
                    pst.setInt(i + 1, (Integer) list.get(i));
                }
            }

            log.info("Execute query: " + query.toString());
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {

                    Contact contact = new Contact();

                    contact.setId(rs.getInt("id_contact"));
                    contact.setName(rs.getString("name"));
                    contact.setSurname(rs.getString("surname"));
                    contact.setMidleName(rs.getString("midleName"));
                    contact.setBirthday(rs.getString("birthday"));
                    contact.setSex(rs.getString("sex"));
                    contact.setNationality(rs.getString("nationality"));
                    contact.setMaritStat(rs.getString("maritStat"));
                    contact.setWebSite(rs.getString("webSite"));
                    contact.setEmail(rs.getString("email"));
                    contact.setCurWorkplace(rs.getString("curWorkplace"));

                    Integer id_address = rs.getInt("id_address");

                    Address address = new Address();
                    get_address.setString(1, id_address.toString());

                    log.info("Execute query: " + select_address);

                    get_address.execute();
                    try (ResultSet resultSet = get_address.getResultSet()) {
                        resultSet.next();
                        address.setId(rs.getInt("id_address"));
                        address.setCountry(rs.getString("country"));
                        address.setCity(rs.getString("city"));
                        address.setStreet(rs.getString("street"));
                        address.setHouse(rs.getString("house"));
                        address.setFlat(rs.getInt("flat"));
                        address.setIndex(rs.getInt("ind"));
                    }

                    contact.setAddress(address);

                    contacts.add(contact);
                }
            }

        } catch (Exception e) {
            log.error(e);
            throw new DaoException("", e);
        }
        return contacts;
    }

    public Contact getContact(Integer id_contact) throws DaoException {

        Contact contact = new Contact();
        String query = "SELECT * FROM contacts WHERE id_contact = ?";

        log.info("Method getContact start: id_contact = " + id_contact);

        log.info("Try get connection to database");

        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement st = con.prepareStatement(query)) {

            st.setString(1, id_contact.toString());

            log.info("Execute query:" + query);

            try (ResultSet rs = st.executeQuery()) {

                rs.next();
                contact.setId(rs.getInt("id_contact"));
                contact.setName(rs.getString("name"));
                contact.setSurname(rs.getString("surname"));
                contact.setMidleName(rs.getString("midleName"));
                contact.setBirthday(rs.getDate("birthday").toString());

                contact.setSex(rs.getString("sex"));
                contact.setNationality(rs.getString("nationality"));
                contact.setMaritStat(rs.getString("maritStat"));
                contact.setWebSite(rs.getString("webSite"));
                contact.setEmail(rs.getString("email"));
                contact.setCurWorkplace(rs.getString("curWorkplace"));

                contact.setPathToImg(rs.getString("imgPath"));

                contact.setAddress(new MySqlAddressDaoImpl().getAddress(id_contact));
                contact.setListFile(new MySqlFilesDaoImpl().getFiles(id_contact));
                contact.setPhoneNumbers(new MySqlNumbersDaoImpl().getPhoneNumbers(id_contact));
            }
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException("Cann't get contact", e);
        }
        return contact;
    }


    public int addContact(Connection connection, Contact contact) throws DaoException {

        String insert_contact_query = "INSERT INTO contacts(name, surname,midleName,birthday,sex," +
                " nationality, maritStat, webSite, email,curWorkplace,id_address,imgPath)" +
                " values(?,?,?,?,?,?,?,?,?,?,?,?)";
        String get_id_query = "SELECT LAST_INSERT_ID()";

        log.info("Method addContact start");

        int id_contact = 0;

        try (PreparedStatement insert_contact = connection.prepareCall(insert_contact_query);
             Statement get_id = connection.createStatement()) {

            insert_contact.setString(1, contact.getName());
            insert_contact.setString(2, contact.getSurname());
            insert_contact.setString(3, contact.getMidleName());
            insert_contact.setString(4, contact.getBirthday());
            insert_contact.setString(5, contact.getSex());
            insert_contact.setString(6, contact.getNationality());
            insert_contact.setString(7, contact.getMaritStat());
            insert_contact.setString(8, contact.getWebSite());
            insert_contact.setString(9, contact.getEmail());
            insert_contact.setString(10, contact.getCurWorkplace());
            insert_contact.setInt(11, contact.getAddress().getId());
            insert_contact.setString(12, contact.getPathToImg());

            log.info("Execute query: " + insert_contact_query);

            insert_contact.executeUpdate();

            log.info(get_id_query);

            get_id.execute(get_id_query);
            try (ResultSet rs = get_id.getResultSet()) {
                rs.next();
                id_contact = rs.getInt(1);
            }

        } catch (Exception e) {
            log.error(e);
            throw new DaoException("cann't add new contact", e);
        }

        return id_contact;
    }


    public void updateContact(Connection connection, Contact contact) throws DaoException {

        String update_contact_query = "UPDATE contacts SET name=?, surname=?,midleName=?,birthday=?,sex=?," +
                " nationality=?, maritStat=?, webSite=?,email=?,curWorkplace=?, id_address=?" +
                ", imgPath=? WHERE id_contact = ?";

        log.info("Method updateContact start");

        try (PreparedStatement update_contact = connection.prepareCall(update_contact_query)) {

            update_contact.setString(1, contact.getName());
            update_contact.setString(2, contact.getSurname());
            update_contact.setString(3, contact.getMidleName());
            update_contact.setString(4, contact.getBirthday());
            update_contact.setString(5, contact.getSex());
            update_contact.setString(6, contact.getNationality());
            update_contact.setString(7, contact.getMaritStat());
            update_contact.setString(8, contact.getWebSite());
            update_contact.setString(9, contact.getEmail());
            update_contact.setString(10, contact.getCurWorkplace());
            update_contact.setInt(11, contact.getAddress().getId());
            update_contact.setInt(13, contact.getId());
            update_contact.setString(12, contact.getPathToImg());

            log.info("Execute query: " + update_contact_query);

            update_contact.executeUpdate();

        } catch (Exception e) {
            log.error(e);
            throw new DaoException("cann't update contact", e);
        }


    }


}

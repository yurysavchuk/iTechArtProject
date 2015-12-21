package com.yurysavchuk.dao.mysql;

import com.yurysavchuk.dao.ConnectionManager;
import com.yurysavchuk.domain.PhoneNumber;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class MySqlNumbersDaoImpl {

    final Logger log = Logger.getLogger(MySqlNumbersDaoImpl.class);

    public List<PhoneNumber> getPhoneNumbers(Integer id) throws DaoException {
        List<PhoneNumber> numbers = new LinkedList<>();
        String query = "SELECT * FROM phonenumbers WHERE id_contact = ?";

        log.info("Method getPhoneNumbers start");

        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement st = con.prepareStatement(query)) {

            st.setString(1, id.toString());

            log.info("Execute query: " + query);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    PhoneNumber number = new PhoneNumber();
                    number.setId(rs.getInt("id_number"));
                    number.setCountryCode(rs.getInt("countryCode"));
                    number.setOperCode(rs.getInt("operCode"));
                    number.setNumber(rs.getInt("number"));
                    number.setType(rs.getString("type"));
                    number.setComment(rs.getString("comment"));

                    numbers.add(number);
                }
            }
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException("Cann't get phoneNumbers", e);
        }
        return numbers;
    }

    public void addPhoneList(Connection connection, PhoneNumber number,
                             int id_contact) throws DaoException {

        String insert_query = "INSERT INTO phonenumbers(countryCode,operCode,number," +
                " type, comment,id_contact) VALUES(?,?,?,?,?,?)";

        log.info("Method addPhoneList start");

        try (PreparedStatement insert_number = connection.prepareCall(insert_query)) {

            insert_number.setInt(1, number.getCountryCode());
            insert_number.setInt(2, number.getOperCode());
            insert_number.setInt(3, number.getNumber());
            insert_number.setString(4, number.getType());
            insert_number.setString(5, number.getComment());
            insert_number.setInt(6, id_contact);

            log.info(insert_query);

            insert_number.executeUpdate();

        } catch (Exception e) {
            log.error(e);
            throw new DaoException("Cann't add new phonenumber", e);
        }
    }


    public void updatePhoneList(Connection connection, PhoneNumber number,
                                int id_contact) throws DaoException {

        String update_query = "UPDATE phonenumbers SET countryCode=?, operCode=?,number=?,type=?," +
                " comment=?,id_contact=? WHERE id_number = ?";

        log.info("Method updatePhoneList start");

        try (PreparedStatement update_number = connection.prepareCall(update_query)) {

            update_number.setInt(1, number.getCountryCode());
            update_number.setInt(2, number.getOperCode());
            update_number.setInt(3, number.getNumber());
            update_number.setString(4, number.getType().trim());
            update_number.setString(5, number.getComment());
            update_number.setInt(6, id_contact);
            update_number.setInt(7, number.getId());

            log.info("Execute query: " + update_query);

            update_number.executeUpdate();

        } catch (Exception e) {
            log.error(e);
            throw new DaoException("cann't update this number", e);
        }
    }

    public void deleteNumber(Connection connection, int id_number) throws DaoException {

        String delete_query = "DELETE FROM phonenumbers WHERE id_number=? ";

        log.info("Method deleteNumber start");

        try (PreparedStatement delete_number = connection.prepareCall(delete_query)) {

            delete_number.setInt(1, id_number);

            log.info("Execute query: " + delete_query);
            delete_number.execute();

        } catch (Exception e) {
            log.error(e);
            throw new DaoException("Cann't delete this number", e);
        }
    }
}

package com.yurysavchuk.dao.mysql;

import com.yurysavchuk.dao.ConnectionManager;
import com.yurysavchuk.domain.Address;
import org.apache.log4j.Logger;

import java.sql.*;


public class MySqlAddressDaoImpl {

    final Logger log = Logger.getLogger(MySqlAddressDaoImpl.class);

    public Address getAddress(Integer id_contact) {

        Address address = new Address();
        String query1 = "SELECT * FROM address,contacts WHERE contacts.id_contact = ?" +
                "AND contacts.id_address = address.id_address";

        log.info("getAddress ( " + id_contact + ") start");

        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement stat = con.prepareStatement(query1)) {

            log.info("Connection to database finished successfully");

            stat.setString(1, id_contact.toString());
            log.info("Execute query: " + query1);
            try (ResultSet rs = stat.executeQuery()) {

                rs.next();
                address.setCountry(rs.getString("country"));
                address.setCity(rs.getString("city"));
                address.setStreet(rs.getString("street"));
                address.setHouse(rs.getString("house"));
                address.setFlat(rs.getInt("flat"));
                address.setIndex(rs.getInt("ind"));
                address.setId(rs.getInt("id_address"));
            }

        } catch (SQLException e) {
            log.error(e);
        }
        return address;
    }


    public int addAddress(Connection connection, Address address) throws DaoException {

        log.info("Method addAddress start(" + connection + "," + address + ")");

        String insert_addr = "INSERT INTO address(country,city," +
                "street,house,flat,ind) values(?,?,?,?,?,?)";
        String get_id_query = "SELECT LAST_INSERT_ID()";

        try (PreparedStatement insert_address = connection.prepareCall(insert_addr);
             Statement get_id = connection.createStatement()) {

            insert_address.setString(1, address.getCountry());
            insert_address.setString(2, address.getCity());
            insert_address.setString(3, address.getStreet());
            insert_address.setString(4, address.getHouse());
            insert_address.setInt(5, address.getFlat());
            insert_address.setInt(6, address.getIndex());

            log.info("Execute query: " + insert_addr);
            insert_address.executeUpdate();

            log.info("Execute query: " + get_id);
            get_id.execute(get_id_query);
            try (ResultSet rs = get_id.getResultSet()) {
                rs.next();
                address.setId(rs.getInt(1));
            }
        } catch (Exception e) {
            log.error(e);
            throw new DaoException("cann't addAddress", e);
        }
        return address.getId();
    }


    public void updateAddress(Connection connection, Address address) throws DaoException {

        String update_adr_query = "UPDATE address SET country = ?,city=?,street=?,house=?,flat=?,ind=? " +
                " WHERE id_address = ?";

        log.info("Method updateAddress start (" + connection + "," + address + ")");

        try (PreparedStatement update_address = connection.prepareCall(update_adr_query)) {

            update_address.setString(1, address.getCountry());
            update_address.setString(2, address.getCity());
            update_address.setString(3, address.getStreet());
            update_address.setString(4, address.getHouse());
            update_address.setInt(5, address.getFlat());
            update_address.setInt(6, address.getIndex());
            update_address.setInt(7, address.getId());

            log.info("Execute query: " + update_adr_query);
            update_address.executeUpdate();

        } catch (Exception e) {
            log.error(e);
            throw new DaoException("cann't update address", e);
        }
    }
}

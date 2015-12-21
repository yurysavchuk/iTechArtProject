package com.yurysavchuk.dao.mysql;

import com.yurysavchuk.dao.ConnectionManager;
import com.yurysavchuk.domain.File;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MySqlFilesDaoImpl {

    final Logger log = Logger.getLogger(MySqlFilesDaoImpl.class);


    public List<File> getFiles(Integer id) throws DaoException {

        List<File> files = new LinkedList<>();
        String query = "SELECT * FROM attachfiles WHERE id_contact = ?";

        log.info("Method getFiles start");

        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {

            statement.setString(1, id.toString());

            log.info("Execute query: " + query);

            try (ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {
                    File file = new File();
                    file.setId(rs.getInt("id_file"));
                    file.setFilename(rs.getString("fileName"));
                    file.setPath(rs.getString("path"));
                    file.setDateLoad(rs.getDate("dataLoad").toString());
                    file.setComment(rs.getString("comment"));
                    files.add(file);
                }
            }
        } catch (SQLException e) {
            log.error(e);
        }
        return files;
    }


    public void addFile(Connection connection, File file, int id_contact) throws DaoException {

        String insert_file_query = "INSERT INTO attachfiles(fileName, path, dataLoad, comment, id_contact)" +
                " VALUES(?,?,?,?,?)";

        log.info("Method addFile start");

        try (PreparedStatement insert_file = connection.prepareCall(insert_file_query)) {

            insert_file.setString(1, file.getFilename());
            insert_file.setString(2, file.getPath());
            insert_file.setString(3, file.getDateLoad());
            insert_file.setString(4, file.getComment());
            insert_file.setInt(5, id_contact);

            log.info("Execute update: " + insert_file_query);

            insert_file.executeUpdate();

        } catch (Exception e) {
            log.error(e);
            throw new DaoException("Cann't insert new file", e);
        }
    }

    public void updateFile(Connection connection, File file, int id_contact) throws DaoException {

        String update_query = "UPDATE attachfiles SET filename=?, path=?, dataLoad=?, comment=?," +
                " id_contact=? WHERE id_file =?";

        log.info("Method updateFile start");

        try (PreparedStatement update_file = connection.prepareCall(update_query)) {

            update_file.setString(1, file.getFilename());
            update_file.setString(2, file.getPath());
            Date curTime = new Date();
            DateFormat dtfrm = DateFormat.getDateInstance();
            String dateTime = dtfrm.format(curTime);
            update_file.setString(3, dateTime);
            update_file.setString(4, file.getComment());
            update_file.setInt(5, id_contact);
            update_file.setInt(6, file.getId());

            log.info("Execute update: " + update_query);

            update_file.executeUpdate();

        } catch (Exception e) {
            log.error(e);
            throw new DaoException("Cann't update this file", e);
        }
    }

    public void deleteFile(Connection connection, int id_file) throws DaoException {

        String delete_query = "DELETE FROM attachfiles WHERE id_file=?";

        log.info("Method deleteFile start");

        try (PreparedStatement delete_file = connection.prepareCall(delete_query)) {

            delete_file.setInt(1, id_file);

            log.error("Execute query: " + delete_query);
            delete_file.execute();

        } catch (Exception e) {
            log.error(e);
            throw new DaoException("File cann't be deleted", e);
        }
    }
}

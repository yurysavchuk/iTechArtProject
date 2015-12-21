package com.yurysavchuk.dao;

import org.apache.log4j.Logger;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Created by HP on 16.11.2015.
 */
public class ConnectionManager {
    final Logger log = Logger.getLogger(ConnectionManager.class);

    private static final String driver = "java:comp/env/jdbc/yury_savchuk";
    private static InitialContext ic;
    private static DataSource ds;

    static {
        try {
            ic = new InitialContext();
            ds = (DataSource) ic.lookup(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Connection getConnection() {

        Connection conn = null;
        try {
            conn = ds.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}

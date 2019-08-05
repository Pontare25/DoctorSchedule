package Schedule.Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DoctorDBHandler {

    private static final String url = "jdbc:sqlite:/Users/PontusN/IdeaProjects/DoctorSchedule/src/DoctorScheduleDB.db";



    public /*static*/ void Testconnection() {
        Connection conn = null;
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }


    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}

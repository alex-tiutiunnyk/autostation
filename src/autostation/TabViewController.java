package autostation;

import autostation.Drivers;
import autostation.DriversController;
import autostation.TicketsController;
import autostation.TimetableController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Date;
import java.util.ResourceBundle;

public class TabViewController implements Initializable {

    public AnchorPane Tickets;
    public AnchorPane Timetable;
    public AnchorPane Drivers;
    @FXML
    private Tab tabTimetable;
    @FXML
    private Tab tabDrivers;
    @FXML
    private Tab tabTickets;
    @FXML
    private TimetableController timetableController;
    @FXML
    private DriversController driversController;
    @FXML
    private TicketsController ticketsController;

    public static Connection getConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/autostation", "root", "34mavima");
            return conn;
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            return null;
        }
    }

    public static void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /*public void initialize() {

        timetableController.initialize(location, resources);
        });*/
        /*driversController.create();
        ticketsController.create();*/



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}

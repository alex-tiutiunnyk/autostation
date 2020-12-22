package autostation;

import autostation.Drivers;
import autostation.DriversController;
import autostation.TicketsController;
import autostation.TimetableController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class TabViewController implements Initializable {

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

    /*public void create() {
        timetableController.create();
        driversController.create();
        ticketsController.create();
    }*/



    @FXML
    private TableView tviewTimetable;
    @FXML
    private TableView<Drivers> tviewDrivers;
    @FXML
    private TableColumn<Drivers, Integer> colIdDriver;
    @FXML
    private TableColumn<Drivers, String> colNameDriver;
    @FXML
    private TableColumn<Drivers, String> colSurnameDriver;
    @FXML
    private TableColumn<Drivers, Date> colAgeDriver;
    @FXML
    private TableColumn<Drivers, String> colLicenseDriver;
    @FXML
    private TableView tviewTickets;
    @FXML
    private TextField tfIdWay;
    @FXML
    private TextField tfDepartureWay;
    @FXML
    private TextField tfArrivalWay;
    @FXML
    private TextField tfDepTimeWay;
    @FXML
    private TextField tfArrTimeWay;
    @FXML
    private TextField tfPriceWay;
    @FXML
    private TextField tfSearchWay;
    @FXML
    private TextField tfNameDriver;
    @FXML
    private TextField tfSurnameDriver;
    @FXML
    private TextField tfAgeDriver;
    @FXML
    private TextField tfLicenseDriver;
    @FXML
    private TextField tfSearchDriver;
    @FXML
    private TextField tfNameTicket;
    @FXML
    private TextField tfSurnameTicket;
    @FXML
    private TextField tfAgeTicket;
    @FXML
    private TextField tfSearchTicket;
    @FXML
    private Button btnAddTimetable;
    @FXML
    private Button btnUpdateTimetable;
    @FXML
    private Button btnDeleteTimetable;
    @FXML
    private Button btnSearchTimetable;
    @FXML
    private Button btnBuyTicket;
    @FXML
    private Button btnAddDrivers;
    @FXML
    private Button btnUpdateDrivers;
    @FXML
    private Button btnDeleteDrivers;
    @FXML
    private Button btnSearchDrivers;
    @FXML
    private Button btnUpdateTickets;
    @FXML
    private Button btnDeleteTickets;
    @FXML
    private Button btnSearchTickets;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

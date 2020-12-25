package autostation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.*;
/*import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;*/
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import static autostation.TabViewController.executeQuery;
import static autostation.TabViewController.getConnection;

public class TimetableController implements Initializable {
    private Integer currentId;
    final ObservableList<Timetable> timetableList = FXCollections.observableArrayList();

    @FXML
    private TableView<Timetable> tviewTimetable;
    @FXML
    private TableColumn<Timetable, Integer> colIdWay;
    @FXML
    private TableColumn<Timetable, String> colDepartureWay;
    @FXML
    private TableColumn<Timetable, String> colArrivalWay;
    @FXML
    private TableColumn<Timetable, Date> colDepTimeWay;
    @FXML
    private TableColumn<Timetable, Date> colArrTimeWay;
    @FXML
    private TableColumn<Timetable, Double> colPriceWay;
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
    private Button btnAddWay;
    @FXML
    private Button btnUpdateWay;
    @FXML
    private Button btnDeleteWay;
    @FXML
    private Button btnSearchWay;
    //todo relization with a new dialog
    @FXML
    private Button btnBuyTicketWay;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showTimetable();
    }

    public ObservableList<Timetable> getTimetableList() {
        Connection conn = getConnection();
        String query = "SELECT * FROM way";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Timetable timetable;
            while (rs.next()) {
//                String t = rs.getString("departure_time");

                timetable = new Timetable(rs.getInt("id"),
                        rs.getString("departure_station"),
                        rs.getString("arrival_station"),
                        rs.getDate("departure_time"),
                        rs.getDate("arrival_time"),
                        rs.getDouble("price"));
                timetableList.add(timetable);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return timetableList;
    }

    public void showTimetable() {
        ObservableList<Timetable> list = getTimetableList();
        colIdWay.setCellValueFactory(new PropertyValueFactory<>("idWay"));
        colDepartureWay.setCellValueFactory(new PropertyValueFactory<>("departureWay"));
        colArrivalWay.setCellValueFactory(new PropertyValueFactory<>("arrivalWay"));
        colDepTimeWay.setCellValueFactory(new PropertyValueFactory<>("depTimeWay"));
        colArrTimeWay.setCellValueFactory(new PropertyValueFactory<>("arrTimeWay"));
        colPriceWay.setCellValueFactory(new PropertyValueFactory<>("priceWay"));

        tviewTimetable.setItems(list);
    }

    private void searchButton() {
        FilteredList<Timetable> filteredData = new FilteredList<>(timetableList, e -> true);
        filteredData.setPredicate((Predicate<? super Timetable>) timetable -> {
            if (tfSearchWay == null || tfSearchWay.getText().isEmpty()) {
                return true;
            }
            String lowerCaseFilter = tfSearchWay.getText().toLowerCase();
            if (timetable.getIdTimetable().toString().contains(lowerCaseFilter)) {
                return true;
            } else if (timetable.getDepartureWay().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (timetable.getArrivalWay().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (timetable.getDepTimeWay().toString().contains(lowerCaseFilter)) {
                return true;
            } else if (timetable.getArrTimeWay().toString().contains(lowerCaseFilter)) {
                return true;
            } else if (timetable.getPriceWay().toString().contains(lowerCaseFilter)) {
                return true;
            }
            return false;
        });
        SortedList<Timetable> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tviewTimetable.comparatorProperty());
        tviewTimetable.setItems(sortedData);
    }

    private void insertRecord() {
        String query = "INSERT INTO way(departure_station, arrival_station, departure_time, arrival_time, price) " +
                "VALUES ('" + tfDepartureWay.getText() + "','" + tfArrivalWay.getText() + "','"
                + tfDepTimeWay.getText() + "','" + tfArrTimeWay.getText() + "'," +
                tfPriceWay.getText() + ")";
        executeQuery(query);
        showTimetable();
    }

    private void updateRecord() {
        String query = "UPDATE way SET departure_station = '" + tfDepartureWay.getText() + "', arrival_station = '" +
                tfArrivalWay.getText() + "', departure_time = '" + tfDepTimeWay.getText() + "', arrival_time = '" +
                tfArrTimeWay.getText() + "', price = " + tfPriceWay.getText() +
                " WHERE id = " + currentId + "";
        executeQuery(query);
        showTimetable();
    }

    private void deleteButton() {
        String query = "DELETE FROM way WHERE id = " + currentId + " ";
        executeQuery(query);
        showTimetable();
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnAddWay) {
            insertRecord();
        } else if (event.getSource() == btnUpdateWay) {
            updateRecord();
        } else if (event.getSource() == btnDeleteWay) {
            deleteButton();
        } else if (event.getSource() == btnSearchWay) {
            searchButton();
        }
    }

    @FXML
    private void handleMouseAction(MouseEvent event) {
        Timetable timetable = tviewTimetable.getSelectionModel().getSelectedItem();
        if (timetable == null) return;
        currentId = timetable.getIdTimetable();
        tfDepartureWay.setText(timetable.getDepartureWay());
        tfArrivalWay.setText(timetable.getArrivalWay());
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
//        String strDate = dateFormat.format(timetable.getDepTimeTimetable());
        tfDepTimeWay.setText("" + timetable.getDepTimeWay());
        tfArrTimeWay.setText("" + timetable.getArrTimeWay());
        tfPriceWay.setText("" + timetable.getPriceWay());
    }
}

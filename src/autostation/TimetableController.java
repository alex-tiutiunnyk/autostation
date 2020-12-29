package autostation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import static autostation.TabViewController.executeQuery;
import static autostation.TabViewController.getConnection;

public class TimetableController implements Initializable {
    private Integer currentId;

    @FXML
    private TableView<Timetable> tviewTimetable;
    @FXML
    private TableColumn<Timetable, Integer> colIdWay;
    @FXML
    private TableColumn<Timetable, String> colDepartureWay;
    @FXML
    private TableColumn<Timetable, String> colArrivalWay;
    @FXML
    private TableColumn<Timetable, Date> colDepDateWay;
    @FXML
    private TableColumn<Timetable, Date> colArrDateWay;
    @FXML
    private TableColumn<Timetable, Double> colPriceWay;
    @FXML
    private TextField tfDepartureWay;
    @FXML
    private TextField tfArrivalWay;
    @FXML
    private TextField tfDepDateWay;
    @FXML
    private TextField tfArrDateWay;
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
    @FXML
    private Button btnBuyTicketWay;
    @FXML
    private Label tfError;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showTimetable();
    }

    public static ObservableList<Timetable> getTimetableList() {
        ObservableList<Timetable> timetableList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM way";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Timetable timetable;
            while (rs.next()) {

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
        colDepDateWay.setCellValueFactory(new PropertyValueFactory<>("depTimeWay"));
        colArrDateWay.setCellValueFactory(new PropertyValueFactory<>("arrTimeWay"));
        colPriceWay.setCellValueFactory(new PropertyValueFactory<>("priceWay"));

        tviewTimetable.setItems(list);
    }

    private void searchButton() {
        FilteredList<Timetable> filteredData = new FilteredList<>(getTimetableList(), e -> true);
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

    private void buyTicket() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("NewTicket.fxml"));
        Parent root = loader.load();
        NewTicketController newTicketController = loader.getController();
        newTicketController.saveInformation(currentId);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void insertRecord() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse(tfDepDateWay.getText());
        Date endDate = sdf.parse(tfArrDateWay.getText());
        String query = "INSERT INTO way(departure_station, arrival_station, departure_time, arrival_time, price) " +
                "VALUES ('" + tfDepartureWay.getText() + "','" + tfArrivalWay.getText() + "','"
                + tfDepDateWay.getText() + "','" + tfArrDateWay.getText() + "'," +
                tfPriceWay.getText() + ")";
        if (startDate.after(endDate)) {
            tfError.setVisible(true);
        } else {
            tfError.setVisible(false);
            executeQuery(query);
            showTimetable();
        }
    }

    private void updateRecord() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse(tfDepDateWay.getText());
        Date endDate = sdf.parse(tfArrDateWay.getText());
        String query = "UPDATE way SET departure_station = '" + tfDepartureWay.getText() + "', arrival_station = '" +
                tfArrivalWay.getText() + "', departure_time = '" + tfDepDateWay.getText() + "', arrival_time = '" +
                tfArrDateWay.getText() + "', price = " + tfPriceWay.getText() +
                " WHERE id = " + currentId + "";
        if (startDate.after(endDate)) {
            tfError.setVisible(true);
        } else {
            tfError.setVisible(false);
            executeQuery(query);
            showTimetable();
        }
    }

    private void deleteButton() {
        String query = "DELETE FROM way WHERE id = " + currentId + " ";
        executeQuery(query);
        showTimetable();
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception {
        if (event.getSource() == btnAddWay) {
            insertRecord();
        } else if (event.getSource() == btnUpdateWay) {
            updateRecord();
        } else if (event.getSource() == btnDeleteWay) {
            deleteButton();
        } else if (event.getSource() == btnSearchWay) {
            searchButton();
        } else if (event.getSource() == btnBuyTicketWay) {
            buyTicket();
        }
    }

    @FXML
    private void handleMouseAction(MouseEvent event) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date localDate = sdf.parse("2020-12-27");
        Timetable timetable = tviewTimetable.getSelectionModel().getSelectedItem();
        if (timetable == null) return;
        currentId = timetable.getIdTimetable();
        tfDepartureWay.setText(timetable.getDepartureWay());
        tfArrivalWay.setText(timetable.getArrivalWay());
        tfDepDateWay.setText("" + timetable.getDepTimeWay());
        tfArrDateWay.setText("" + timetable.getArrTimeWay());
        tfPriceWay.setText("" + timetable.getPriceWay());
        Date startDate = sdf.parse(tfDepDateWay.getText());
        btnBuyTicketWay.setDisable(startDate.before(localDate));
    }
}

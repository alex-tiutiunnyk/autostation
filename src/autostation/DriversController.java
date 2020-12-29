package autostation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import static autostation.TabViewController.executeQuery;
import static autostation.TabViewController.getConnection;

public class DriversController implements Initializable {
    private Integer currentId;

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
    private Button btnAddDriver;
    @FXML
    private Button btnUpdateDriver;
    @FXML
    private Button btnDeleteDriver;
    @FXML
    private Button btnSearchDriver;
    @FXML
    private Label tfErrorDriver;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showDrivers();
    }

    public ObservableList<Drivers> getDriversList() {
        ObservableList<Drivers> driverList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM driver ";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Drivers drivers;
            while (rs.next()) {
                drivers = new Drivers(rs.getInt("id"),
                        rs.getString("driver_name"),
                        rs.getString("driver_surname"),
                        rs.getDate("age"),
                        rs.getInt("driver_license"));
                driverList.add(drivers);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return driverList;
    }

    public void showDrivers() {
        ObservableList<Drivers> list = getDriversList();
        colIdDriver.setCellValueFactory(new PropertyValueFactory<>("idDriver"));
        colNameDriver.setCellValueFactory(new PropertyValueFactory<>("nameDriver"));
        colSurnameDriver.setCellValueFactory(new PropertyValueFactory<>("surnameDriver"));
        colAgeDriver.setCellValueFactory(new PropertyValueFactory<>("ageDriver"));
        colLicenseDriver.setCellValueFactory(new PropertyValueFactory<>("licenseDriver"));

        tviewDrivers.setItems(list);
    }

    private void searchButton() {
        FilteredList<Drivers> filteredData = new FilteredList<>(getDriversList(), e -> true);
        filteredData.setPredicate((Predicate<? super Drivers>) drivers -> {
            if (tfSearchDriver == null || tfSearchDriver.getText().isEmpty()) {
                return true;
            }
            String lowerCaseFilter = tfSearchDriver.getText().toLowerCase();
            if (drivers.getIdDriver().toString().contains(lowerCaseFilter)) {
                return true;
            } else if (drivers.getNameDriver().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (drivers.getSurnameDriver().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (drivers.getAgeDriver() != null && drivers.getAgeDriver().toString().contains(lowerCaseFilter)) {
                return true;
            } else if (drivers.getLicenseDriver().toString().contains(lowerCaseFilter)) {
                return true;
            }
            return false;
        });

        SortedList<Drivers> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tviewDrivers.comparatorProperty());
        tviewDrivers.setItems(sortedData);
    }

    private void insertRecord() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = sdf.parse(tfAgeDriver.getText());
        Date date = sdf.parse("2002-01-01");
        String query = "INSERT INTO driver(driver_name, driver_surname, age, driver_license) " +
                "VALUES ('" + tfNameDriver.getText() + "','" + tfSurnameDriver.getText() + "',"
                + (tfAgeDriver.toString().isEmpty() ? null : "'" + tfAgeDriver.getText() + "'") +
                "," + tfLicenseDriver.getText() + ")";
        if (birthDate.after(date)) {
            tfErrorDriver.setVisible(true);
        } else {
            tfErrorDriver.setVisible(false);
            executeQuery(query);
            showDrivers();
        }
    }

    private void updateRecord() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = sdf.parse(tfAgeDriver.getText());
        Date date = sdf.parse("2002-01-01");
        String query = "UPDATE driver SET driver_name = '" + tfNameDriver.getText() + "', driver_surname = '" +
                tfSurnameDriver.getText() + "', age = " + (tfAgeDriver.toString().isEmpty() ? null : "'" +
                tfAgeDriver.getText() + "'") + ", driver_license = " + tfLicenseDriver.getText() +
                " WHERE id = " + currentId + "";
        if (birthDate.after(date)) {
            tfErrorDriver.setVisible(true);
        } else {
            tfErrorDriver.setVisible(false);
            executeQuery(query);
            showDrivers();
        }
    }

    private void deleteButton() {
        String query = "DELETE FROM driver WHERE id = " + currentId + " ";
        executeQuery(query);
        showDrivers();
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws ParseException {
        if (event.getSource() == btnAddDriver) {
            insertRecord();
        } else if (event.getSource() == btnUpdateDriver) {
            updateRecord();
        } else if (event.getSource() == btnDeleteDriver) {
            deleteButton();
        } else if (event.getSource() == btnSearchDriver) {
            searchButton();
        }
    }

    @FXML
    private void handleMouseAction(MouseEvent event) {
        Drivers driver = tviewDrivers.getSelectionModel().getSelectedItem();
        if (driver == null) return;
        currentId = driver.getIdDriver();
        tfNameDriver.setText(driver.getNameDriver());
        tfSurnameDriver.setText(driver.getSurnameDriver());
        tfAgeDriver.setText("" + driver.getAgeDriver());
        tfLicenseDriver.setText("" + driver.getLicenseDriver());
    }
}
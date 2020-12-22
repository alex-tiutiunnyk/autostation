package autostation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.ResourceBundle;

public class DriversController implements Initializable {

   /* public void create(){

    }*/

    @FXML
    private TextField tfIdDriver;
    @FXML
    private TextField tfNameDriver;
    @FXML
    private TextField tfSurnameDriver;
    @FXML
    private TextField tfAgeDriver;
    @FXML
    private TextField tfLicenseDriver;
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
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnAddDriver) {
            insertRecord();
        } else if (event.getSource() == btnUpdateDriver) {
            updateRecord();
        } else if (event.getSource() == btnDeleteDriver) {
            deleteButton();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showDrivers();
    }

    public Connection getConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/autostation", "root", "34mavima");
            return conn;
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            return null;
        }
    }

    public ObservableList<Drivers> getDriversList() {
        ObservableList<Drivers> driverList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM driver";
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
        colIdDriver.setCellValueFactory(new PropertyValueFactory<Drivers, Integer>("idDriver"));
        colNameDriver.setCellValueFactory(new PropertyValueFactory<Drivers, String>("nameDriver"));
        colSurnameDriver.setCellValueFactory(new PropertyValueFactory<Drivers, String>("surnameDriver"));
        colAgeDriver.setCellValueFactory(new PropertyValueFactory<Drivers, Date>("ageDriver"));
        colLicenseDriver.setCellValueFactory(new PropertyValueFactory<Drivers, String>("licenseDriver"));

        tviewDrivers.setItems(list);
    }

    private void insertRecord() {
        String query = "INSERT INTO driver(driver_name, driver_surname, age, driver_license) " +
                "VALUES ('" + tfNameDriver.getText() + "','" + tfSurnameDriver.getText() + "',"
                + (tfAgeDriver.getText().isEmpty() ? null : "'" + tfAgeDriver.getText() + "'") +
                "," + tfLicenseDriver.getText() + ")";
        executeQuery(query);
        showDrivers();
    }

    private void updateRecord() {
        String query = "UPDATE driver SET driver_name = '" + tfNameDriver.getText() + "', driver_surname = '" +
                tfSurnameDriver.getText() + "', age = " + (tfAgeDriver.getText().isEmpty() ? null : "'" +
                tfAgeDriver.getText() + "'") + ", driver_license = " + tfLicenseDriver.getText() +
                " WHERE id = " + tfIdDriver.getText() + "";
        executeQuery(query);
        showDrivers();
    }

    private void deleteButton() {
        String query = "DELETE FROM driver WHERE id = " + tfIdDriver.getText() + " ";
        executeQuery(query);
        showDrivers();
    }

    private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleMouseAction(MouseEvent event){
        Drivers driver = tviewDrivers.getSelectionModel().getSelectedItem();
        tfIdDriver.setText("" + driver.getIdDriver());
        tfNameDriver.setText(driver.getNameDriver());
        tfSurnameDriver.setText(driver.getSurnameDriver());
        tfAgeDriver.setText("" + driver.getAgeDriver());
        tfLicenseDriver.setText("" + driver.getLicenseDriver());
    }
}

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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import static autostation.TabViewController.executeQuery;
import static autostation.TabViewController.getConnection;

public class TicketsController implements Initializable {
    private Integer currentId;
    final ObservableList<Tickets> ticketsList = FXCollections.observableArrayList();

    @FXML
    private TableView<Tickets> tviewTickets;
    @FXML
    private TableColumn<Tickets, Integer> colIdTicket;
    @FXML
    private TableColumn<Tickets, String> colNameTicket;
    @FXML
    private TableColumn<Tickets, String> colSurnameTicket;
    @FXML
    private TableColumn<Tickets, Date> colAgeTicket;
    @FXML
    private TableColumn<Tickets, String> colDepartureTicket;
    @FXML
    private TableColumn<Tickets, String> colArrivalTicket;
    @FXML
    private TableColumn<Tickets, Date> colDepTimeTicket;
    @FXML
    private TableColumn<Tickets, Date> colArrTimeTicket;
    @FXML
    private TableColumn<Tickets, Double> colPriceTicket;
    @FXML
    private TextField tfNameTicket;
    @FXML
    private TextField tfSurnameTicket;
    @FXML
    private TextField tfAgeTicket;
    @FXML
    private TextField tfSearchTicket;
    @FXML
    private Button btnUpdateTicket;
    @FXML
    private Button btnDeleteTicket;
    //todo relization
    @FXML
    private Button btnSearchTicket;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showTickets();
    }

    public ObservableList<Tickets> getTicketsList() {
        Connection conn = getConnection();
        String query = "SELECT ticket.id, passenger_name, passenger_surname, passenger_age, departure_station, " +
                "arrival_station, departure_time, arrival_time, price FROM ticket, way WHERE way.id = ticket.way_id";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Tickets tickets;
            while (rs.next()) {
                tickets = new Tickets(rs.getInt("id"),
                        rs.getString("passenger_name"),
                        rs.getString("passenger_surname"),
                        rs.getDate("passenger_age"),
                        rs.getString("departure_station"),
                        rs.getString("arrival_station"),
                        rs.getDate("departure_time"),
                        rs.getDate("arrival_time"),
                        rs.getDouble("price"));
                ticketsList.add(tickets);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ticketsList;
    }

    public void showTickets() {
        ObservableList<Tickets> list = getTicketsList();
        colIdTicket.setCellValueFactory(new PropertyValueFactory<>("idTicket"));
        colNameTicket.setCellValueFactory(new PropertyValueFactory<>("nameTicket"));
        colSurnameTicket.setCellValueFactory(new PropertyValueFactory<>("surnameTicket"));
        colAgeTicket.setCellValueFactory(new PropertyValueFactory<>("ageTicket"));
        colDepartureTicket.setCellValueFactory(new PropertyValueFactory<>("departureTicket"));
        colArrivalTicket.setCellValueFactory(new PropertyValueFactory<>("arrivalTicket"));
        colDepTimeTicket.setCellValueFactory(new PropertyValueFactory<>("depTimeTicket"));
        colArrTimeTicket.setCellValueFactory(new PropertyValueFactory<>("arrTimeTicket"));
        colPriceTicket.setCellValueFactory(new PropertyValueFactory<>("priceTicket"));

        tviewTickets.setItems(list);
    }

    private void searchButton() {
        FilteredList<Tickets> filteredData = new FilteredList<>(ticketsList, e -> true);
        filteredData.setPredicate((Predicate<? super Tickets>) tickets -> {
            if (tfSearchTicket == null || tfSearchTicket.getText().isEmpty()) {
                return true;
            }
            String lowerCaseFilter = tfSearchTicket.getText().toLowerCase();
            if (tickets.getIdTicket().toString().contains(lowerCaseFilter)) {
                return true;
            } else if (tickets.getNameTicket().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (tickets.getSurnameTicket().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            }  else if (tickets.getAgeTicket().toString().contains(lowerCaseFilter)) {
                return true;
            } else if (tickets.getDepartureTicket().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            }  else if (tickets.getArrivalTicket().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (tickets.getDepTimeTicket().toString().contains(lowerCaseFilter)) {
                return true;
            } else if (tickets.getArrTimeTicket().toString().contains(lowerCaseFilter)) {
                return true;
            } else if (tickets.getPriceTicket().toString().contains(lowerCaseFilter)) {
                return true;
            }
            return false;
        });
        SortedList<Tickets> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tviewTickets.comparatorProperty());
        tviewTickets.setItems(sortedData);
    }

    private void updateRecord() {
        String query = "UPDATE ticket SET passenger_name = '" + tfNameTicket.getText() + "', passenger_surname = '" +
                tfSurnameTicket.getText() + "', passenger_age = '" + tfAgeTicket.getText() + "' WHERE id = " + currentId + "";
        executeQuery(query);
        showTickets();
    }

    private void deleteButton() {
        String query = "DELETE FROM ticket WHERE id = " + currentId + " ";
        executeQuery(query);
        showTickets();
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnUpdateTicket) {
            updateRecord();
        } else if (event.getSource() == btnDeleteTicket) {
            deleteButton();
        } else if (event.getSource() == btnSearchTicket) {
            searchButton();
        }
    }

    @FXML
    private void handleMouseAction(MouseEvent event) {
        Tickets tickets = tviewTickets.getSelectionModel().getSelectedItem();
        if (tickets == null) return;
        currentId = tickets.getIdTicket();
        tfNameTicket.setText(tickets.getNameTicket());
        tfSurnameTicket.setText(tickets.getSurnameTicket());
        tfAgeTicket.setText("" + tickets.getAgeTicket());
    }

}

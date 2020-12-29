package autostation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import static autostation.TabViewController.executeQuery;

public class NewTicketController implements Initializable {
    private Integer currentWayId;
    @FXML
    private TextField tfNameBuy;
    @FXML
    private TextField tfSurnameBuy;
    @FXML
    private TextField tfAgeBuy;
    @FXML
    private Button btnCancelTicket;
    @FXML
    private Button btnAdmitTicket;
    @FXML
    private Label tfErrorAge;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void saveInformation(Integer currentId) {
        currentWayId = currentId;
    }

    private void cancelTicket() {
        Stage stage = (Stage) btnCancelTicket.getScene().getWindow();
        stage.close();
    }

    private void admitTicket() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = sdf.parse(tfAgeBuy.getText());
        Date date = sdf.parse("2002-01-01");
        String query = "INSERT INTO ticket(way_id, passenger_name, passenger_surname, passenger_age) " +
                "VALUES (" + currentWayId + ",'" + tfNameBuy.getText() + "','"
                + tfSurnameBuy.getText() + "','" + tfAgeBuy.getText() + "')";
        if (birthDate.after(date)) {
            tfErrorAge.setVisible(true);
        } else {
            tfErrorAge.setVisible(false);
            executeQuery(query);
            Stage stage = (Stage) btnAdmitTicket.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception {
        if (event.getSource() == btnAdmitTicket) {
            admitTicket();
        } else if (event.getSource() == btnCancelTicket) {
            cancelTicket();
        }
    }
}

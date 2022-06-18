package ams;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Performance;
import model.User;
import services.IServiceAMS;
import services.ServiceException;

public class BuyTicketControllerAMS {
    private IServiceAMS server;
    private User user;
    private Performance performance;
    @FXML
    private TextField nameField, numberField;
    @FXML
    private Button buyButton;
    @FXML
    private Label errorLabel, successLabel;

    public void setServer(IServiceAMS server, User user, Performance performance) {
        this.server = server;
        this.user = user;
        this.performance = performance;
    }

    public void handleBuyButton(ActionEvent actionEvent) {
        try {
            this.server.saveTicket(performance.getID(), performance.getDate(), performance.getPlace(),
                    performance.getNoOfAvailableSeats(), performance.getNoOfSoldSeats(), performance.getArtist(),
                    nameField.getText(), Integer.parseInt(numberField.getText()));
            successLabel.setVisible(true);
            Stage stage = (Stage) buyButton.getScene().getWindow();
            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished(event -> stage.close());
            delay.play();
        } catch (ServiceException e) {
            errorLabel.setVisible(true);
            errorLabel.setText(e.getMessage());
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(event -> errorLabel.setVisible(false));
            delay.play();
        } catch (NumberFormatException e) {
            errorLabel.setVisible(true);
            errorLabel.setText("The second field must represent a number!");
        }
    }
}

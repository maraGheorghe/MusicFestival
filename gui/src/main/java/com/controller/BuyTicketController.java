package com.controller;

import com.StartApp;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Performance;
import model.Ticket;
import service.SuperService;
import validator.ValidationException;

import java.io.IOException;
import java.text.ParseException;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class BuyTicketController {
    private SuperService superService;
    private Performance performance;
    @FXML
    private TextField nameField, numberField;
    @FXML
    private Button buyButton;
    @FXML
    private Label errorLabel, successLabel;

    public void setSuperService(SuperService superService) {
        this.superService = superService;
    }

    public void setPerformance(Performance performance) {
        this.performance = performance;
    }

    public void handleBuyButton(ActionEvent actionEvent) throws IOException {
        try {
            Optional<Ticket> optionalTicket = this.superService.serviceTicket.save(performance.getID(), performance.getDate(), performance.getPlace(),
                    performance.getNoOfAvailableSeats(), performance.getNoOfSoldSeats(), performance.getArtist(),
                    nameField.getText(), Integer.parseInt(numberField.getText()));
            optionalTicket.ifPresent(ticket -> {
                    successLabel.setVisible(true);
                Stage stage = (Stage) buyButton.getScene().getWindow();
                PauseTransition delay = new PauseTransition(Duration.seconds(1));
                delay.setOnFinished( event -> stage.close() );
                delay.play();
                });
            if (optionalTicket.isEmpty()) {
                errorLabel.setVisible(true);
                errorLabel.setText("Invalid name or number of tickets!\n Please try again!");
            }
        }
        catch (ValidationException exception) {
            errorLabel.setVisible(true);
            errorLabel.setText(exception.getMessage());
        }
        catch (NumberFormatException exception) {
            errorLabel.setVisible(true);
            errorLabel.setText("The second field must represent a number!");
        }
    }

}

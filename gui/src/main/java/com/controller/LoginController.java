package com.controller;

import com.StartApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import service.SuperService;
import validator.ValidationException;

import java.io.IOException;
import java.util.Optional;

public class LoginController {
    private SuperService superService;
    @FXML
    private Button loginButton;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label errorLabel;

    public void setSuperService(SuperService superService) {
        this.superService = superService;
    }

    public void handleLoginButton(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StartApp.class.getResource("main_view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Optional<User> optionalUser = superService.serviceUser.login(usernameField.getText(), passwordField.getText());
            optionalUser.ifPresent(user -> {
                this.superService.setUser(user);
                fxmlLoader.<MainController>getController().setSuperService(this.superService);
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(scene);
            });
           errorLabel.setVisible(true);
           errorLabel.setText("Wrong username or password!\n Please try again!");
        }
        catch (ValidationException exception) {
            errorLabel.setVisible(true);
            errorLabel.setText(exception.getMessage());
        }
    }
}

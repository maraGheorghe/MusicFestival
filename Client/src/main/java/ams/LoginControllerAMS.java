package ams;

import gui.MainController;
import gui.StartRpcClient;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.User;
import services.IServiceAMS;
import services.ServiceException;

import java.io.IOException;

public class LoginControllerAMS {
    private IServiceAMS server;
    @FXML
    private Button loginButton;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label errorLabel;
    MainControllerAMS mainControllerAMS;

    public void setServer(IServiceAMS server) {
        this.server = server;
    }

    public void handleLoginButton(ActionEvent actionEvent) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        User user = new User(username, password);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StartAMSRpcClient.class.getResource("main_view.fxml"));
            fxmlLoader.setController(mainControllerAMS);
            Scene scene = new Scene(fxmlLoader.load());
            this.server.login(username, password);
            fxmlLoader.<ams.MainControllerAMS>getController().setServer(this.server, user);
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    fxmlLoader.<MainControllerAMS>getController().logout();
                    Platform.exit();
                    System.exit(0);
                }
            });
        } catch (ServiceException e) {
            errorLabel.setVisible(true);
            errorLabel.setText(e.getMessage());
        }
    }
}

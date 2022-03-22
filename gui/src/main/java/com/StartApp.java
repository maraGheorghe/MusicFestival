package com;

import com.controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import repository.RepositoryPerformance;
import repository.RepositoryTicket;
import repository.RepositoryUser;
import service.ServicePerformance;
import service.ServiceTicket;
import service.ServiceUser;
import service.SuperService;
import validator.PerformanceValidator;
import validator.TicketValidator;
import validator.UserValidator;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class StartApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("src/main/resources/bd.config"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        RepositoryUser repositoryUser = new RepositoryUser(properties);
        RepositoryPerformance repositoryPerformance = new RepositoryPerformance(properties);
        RepositoryTicket repositoryTicket = new RepositoryTicket(properties);
        ServiceUser serviceUser = new ServiceUser(repositoryUser, new UserValidator());
        ServicePerformance servicePerformance = new ServicePerformance(repositoryPerformance, new PerformanceValidator());
        ServiceTicket serviceTicket = new ServiceTicket(repositoryTicket, new TicketValidator(), new PerformanceValidator());

        FXMLLoader fxmlLoader = new FXMLLoader(StartApp.class.getResource("login.fxml"));
        AnchorPane root = fxmlLoader.load();
        fxmlLoader.<LoginController>getController().setSuperService(new SuperService(serviceUser, servicePerformance, serviceTicket, null));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Music Festival");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

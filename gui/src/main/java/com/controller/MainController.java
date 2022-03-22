package com.controller;

import com.StartApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.SuperService;
import model.Performance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MainController {
    private SuperService superService;
    @FXML
    private Button logoutButton;
    @FXML
    private DatePicker datePicker;
    @FXML
    AnchorPane rootPane;
    @FXML
    private ListView<Performance> allShowsList;
    @FXML
    private ListView<PerformanceDTO> dateShowsList;
    ObservableList<Performance> allModel = FXCollections.observableArrayList();
    ObservableList<PerformanceDTO> dateModel = FXCollections.observableArrayList();

    public void setSuperService(SuperService superService) {
        this.superService = superService;
        initModelAllShows();
        allShowsList.setItems(allModel);
        dateShowsList.setItems(dateModel);
    }

    public void handleBuyTicket(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount() == 2) {
            FXMLLoader fxmlLoader = new FXMLLoader(StartApp.class.getResource("buy_ticket_view.fxml"));
            AnchorPane root = fxmlLoader.load();
            Optional<Performance> performance = Optional.ofNullable(allShowsList.getSelectionModel().getSelectedItem());
            if (performance.isPresent()) {
                fxmlLoader.<BuyTicketController>getController().setPerformance(performance.get());
                fxmlLoader.<BuyTicketController>getController().setSuperService(this.superService);
            }
            Scene scene = new Scene(root);
            Stage secondStage = new Stage();
            secondStage.setTitle("Buy a ticket!");
            secondStage.setScene(scene);
            secondStage.show();
        }
    }


    public void handleBuyDateTicket(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount() == 2) {
            FXMLLoader fxmlLoader = new FXMLLoader(StartApp.class.getResource("buy_ticket_view.fxml"));
            AnchorPane root = fxmlLoader.load();
            Optional<PerformanceDTO> optionalPerformance = Optional.ofNullable(dateShowsList.getSelectionModel().getSelectedItem());
            if (optionalPerformance.isPresent()) {
                Performance performance = optionalPerformance.get().getPerformance();
                fxmlLoader.<BuyTicketController>getController().setPerformance(performance);
                fxmlLoader.<BuyTicketController>getController().setSuperService(this.superService);
            }
            Scene scene = new Scene(root);
            Stage secondStage = new Stage();
            secondStage.setTitle("Buy a ticket!");
            secondStage.setScene(scene);
            secondStage.show();
        }
    }

    public void initModelAllShows() {
        List<Performance> allShows = superService.servicePerformance.findAll();
        allModel.setAll(allShows);
    }

    public void initModelDateShows() {
        List<Performance> dateShows = superService.servicePerformance.findAllForADay(datePicker.getValue());
        List<PerformanceDTO> performancesDTOS = new ArrayList<>();
        dateShows.forEach(show -> {
            PerformanceDTO performanceDTO = new PerformanceDTO(show.getID(), show.getDate(), show.getPlace(),
                    show.getNoOfAvailableSeats(), show.getNoOfSoldSeats(), show.getArtist());
            performancesDTOS.add(performanceDTO);
        });
        dateModel.setAll(performancesDTOS);
    }

    public void handleLogoutButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(StartApp.class.getResource("login.fxml"));
        AnchorPane root = fxmlLoader.load();
        superService.setUser(null);
        fxmlLoader.<LoginController>getController().setSuperService(superService);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    public void handleSetDateLists(ActionEvent actionEvent) {
        initModelDateShows();
    }
}

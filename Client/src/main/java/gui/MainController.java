package gui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Performance;
import model.Ticket;
import model.User;
import services.ObserverInterface;
import services.ServiceException;
import services.ServiceInterface;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.Optional;

public class MainController implements ObserverInterface {
    private ServiceInterface server;
    private User user;
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

    public void setServer(ServiceInterface server, User user) {
        this.server = server;
        this.user = user;
        this.datePicker.setValue(LocalDate.now());
        initModelAllShows();
        setShowAllList();
        dateShowsList.setItems(dateModel);
    }

    private void setShowAllList() {
        allShowsList.setItems(allModel);
        allShowsList.setCellFactory(param -> new ListCell<Performance>() {
            @Override
            protected void updateItem(Performance item, boolean empty) {
                super.updateItem(item, empty);
                if(item != null) {
                    setText(item.toString());
                    if ( item.getNoOfAvailableSeats() == 0)
                        setStyle("-fx-background-color: red;");
                }
            }
        });
    }

    public void handleBuyTicket(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount() == 2) {
            FXMLLoader fxmlLoader = new FXMLLoader(StartRpcClient.class.getResource("buy_ticket_view.fxml"));
            AnchorPane root = fxmlLoader.load();
            Optional<Performance> performance = Optional.ofNullable(allShowsList.getSelectionModel().getSelectedItem());
            performance.ifPresent(value -> fxmlLoader.<BuyTicketController>getController().setServer(this.server, this.user, value));
            Scene scene = new Scene(root);
            Stage secondStage = new Stage();
            secondStage.setTitle("Buy a ticket!");
            secondStage.setScene(scene);
            secondStage.show();
        }
    }


    public void handleBuyDateTicket(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount() == 2) {
            FXMLLoader fxmlLoader = new FXMLLoader(StartRpcClient.class.getResource("buy_ticket_view.fxml"));
            AnchorPane root = fxmlLoader.load();
            Optional<PerformanceDTO> optionalPerformance = Optional.ofNullable(dateShowsList.getSelectionModel().getSelectedItem());
            optionalPerformance.ifPresent(performanceDTO -> fxmlLoader.<BuyTicketController>getController().setServer(this.server, this.user, performanceDTO.getPerformance()));
            Scene scene = new Scene(root);
            Stage secondStage = new Stage();
            secondStage.setTitle("Buy a ticket!");
            secondStage.setScene(scene);
            secondStage.show();
        }
    }

    private void initModelAllShows() {
        List<Performance> performances = null;
        try {
            performances = server.findAllPerformances();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        allModel.setAll(performances);
    }

    private void initModelDateShows() {
        List<Performance> dateShows = null;
        LocalDate date = datePicker.getValue();
        try {
            dateShows = server.findAllPerformancesForADay(date);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        List<PerformanceDTO> performancesDTOS = new ArrayList<>();
        dateShows.forEach(show -> {
            PerformanceDTO performanceDTO = new PerformanceDTO(show.getID(), show.getDate(), show.getPlace(),
                    show.getNoOfAvailableSeats(), show.getNoOfSoldSeats(), show.getArtist());
            performancesDTOS.add(performanceDTO);
        });
        dateModel.setAll(performancesDTOS);
    }

    public void logout() {
        try {
            server.logout(this.user, this);
        } catch (ServiceException e) {
            System.out.println("Error logging out: " + e);
        }
    }

    public void handleLogoutButton(ActionEvent actionEvent) throws IOException {
        logout();
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(StartRpcClient.class.getResource("login.fxml"));
        AnchorPane root = fxmlLoader.load();
        fxmlLoader.<LoginController>getController().setServer(server);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    public void handleSetDateLists(ActionEvent actionEvent) {
        initModelDateShows();
    }

    @Override
    public void ticketBought(Ticket ticket) throws ServiceException {
        Platform.runLater(() -> {
            initModelAllShows();
            initModelDateShows();
        });
    }
}

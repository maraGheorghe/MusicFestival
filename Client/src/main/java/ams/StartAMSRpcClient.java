package ams;

import gui.StartRpcClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import network.rpc_protocol.ams.ServerAMSRpcProxy;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import services.IServiceAMS;

public class StartAMSRpcClient extends Application {
    public void start(Stage primaryStage) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-client.xml");
        MainControllerAMS ctrl = context.getBean("mainController", MainControllerAMS.class);
        FXMLLoader fxmlLoader = new FXMLLoader(StartAMSRpcClient.class.getResource("login.fxml"));
        AnchorPane root = fxmlLoader.load();
        IServiceAMS server = context.getBean("service", ServerAMSRpcProxy.class);
        fxmlLoader.<LoginControllerAMS>getController().setServer(server);
        fxmlLoader.<LoginControllerAMS>getController().mainControllerAMS = ctrl;
        Scene scene = new Scene(root);
        primaryStage.setTitle("Music Festival");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

package gui;
;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import network.rpc_protocol.ServiceRpcProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.ServiceInterface;

import java.io.IOException;
import java.util.Properties;


public class StartRpcClient extends Application {
    private static int defaultPort = 22126;
    private static String defaultServer = "localhost";
    private static final Logger logger = LogManager.getLogger();

    public void start(Stage primaryStage) throws Exception {
        logger.info("Starting client...");
        System.out.println("Starting client...");
        Properties properties = new Properties();
        try {
            properties.load(StartRpcClient.class.getResourceAsStream("/client.properties"));
            System.out.println("Client properties set.");
            logger.info("Client properties set.");
            properties.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find client.properties: " + e);
            logger.error(e);
            return;
        }
        String serverIP = properties.getProperty("server.host", defaultServer);
        int serverPort = defaultPort;
        try {
            serverPort = Integer.parseInt(properties.getProperty("server.port"));
        } catch (NumberFormatException ex) {
            logger.error(ex.getMessage());
            System.err.println("Wrong port number: " + ex.getMessage());
            System.out.println("Using default port: " + defaultPort);
        }
        logger.info("Using server IP: {} and server port: {}.", serverIP, serverPort);
        System.out.println("Using server IP: " + serverIP);
        System.out.println("Using server port: " + serverPort);
        ServiceInterface server = new ServiceRpcProxy(serverIP, serverPort);

        FXMLLoader fxmlLoader = new FXMLLoader(StartRpcClient.class.getResource("login.fxml"));
        AnchorPane root = fxmlLoader.load();
        fxmlLoader.<LoginController>getController().setServer(server);
        Scene scene = new Scene(root);
        primaryStage.setTitle("Music Festival");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}



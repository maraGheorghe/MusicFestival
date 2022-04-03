import network.utils.AbstractServer;
import network.utils.RpcConcurrentServer;
import network.utils.ServerException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.RepositoryPerformance;
import repository.RepositoryTicket;
import repository.RepositoryUser;
import repository.interfaces.RepositoryInterfacePerformance;
import repository.interfaces.RepositoryInterfaceTicket;
import repository.interfaces.RepositoryInterfaceUser;
import server.ServiceImplementation;
import services.ServiceInterface;

import java.io.IOException;
import java.util.Properties;

public class StartRpcServer {
    private static int defaultPort = 22126;
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            properties.load(StartRpcServer.class.getResourceAsStream("/server.properties"));
            logger.info("Server properties set.");
            System.out.println("Server properties set.");
            properties.list(System.out);
        } catch (IOException e) {
            logger.error(e);
            System.err.println("Cannot find server.properties: " + e);
            return;
        }
        RepositoryInterfaceUser repositoryUser = new RepositoryUser(properties);
        RepositoryInterfacePerformance repositoryPerformance = new RepositoryPerformance(properties);
        RepositoryInterfaceTicket repositoryTicket = new RepositoryTicket(properties);
        ServiceInterface serverImplementation = new ServiceImplementation(repositoryUser, repositoryPerformance, repositoryTicket);
        int serverPort = defaultPort;
        try {
            serverPort = Integer.parseInt(properties.getProperty("server.port"));
        } catch (NumberFormatException nef){
            logger.error(nef);
            System.err.println("Wrong  Port Number: " + nef.getMessage());
            System.err.println("Using default port: "+ defaultPort);
        }
        System.out.println("Starting server on port: " + serverPort);
        AbstractServer server = new RpcConcurrentServer(serverPort, serverImplementation);
        try {
            logger.info("Starting server...");
            server.start();
        } catch (ServerException e) {
            logger.error(e);
            System.err.println("Error starting the server: " + e.getMessage());
        } finally {
            try {
                server.stop();
            } catch(ServerException e){
                logger.error(e);
                System.err.println("Error stopping server: " + e.getMessage());
            }
        }
    }
}

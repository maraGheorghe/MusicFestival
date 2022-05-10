import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.jdbc.RepositoryUser;
import repository.jdbc.SessionFactoryUtil;
import repository.hibernateRepositories.RepositoryPerformanceHibernate;
import repository.hibernateRepositories.RepositoryTicketHibernate;
import repository.interfaces.RepositoryInterfacePerformance;
import repository.interfaces.RepositoryInterfaceTicket;
import repository.interfaces.RepositoryInterfaceUser;

import java.io.IOException;
import java.util.Properties;

public class StartRpcServer {
    private static int defaultPort = 26122;
    private Server server;
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
        RepositoryInterfacePerformance repositoryPerformance = new RepositoryPerformanceHibernate();
        RepositoryInterfaceTicket repositoryTicket = new RepositoryTicketHibernate();
        ServiceImplementation service = new ServiceImplementation(repositoryUser, repositoryPerformance, repositoryTicket);
        try {
            defaultPort = Integer.parseInt(properties.getProperty("server.port"));
        } catch (NumberFormatException nef){
            logger.error(nef);
            System.err.println("Wrong  Port Number: " + nef.getMessage());
            System.err.println("Using default port: "+ defaultPort);
        }
        System.out.println("Starting server on port: " + defaultPort);
        StartRpcServer server = new StartRpcServer();
        try {
            logger.info("Starting server...");
            server.start(service);
            server.blockUntilShutdown();
            SessionFactoryUtil.close();
        } catch (IOException | InterruptedException e) {
            logger.error(e);
            e.printStackTrace();
            SessionFactoryUtil.close();
        }
    }

    public void start(ServiceImplementation service) throws IOException {
        server = ServerBuilder
                .forPort(defaultPort)
                .addService(service)
                .build()
                .start();
    }

    public void blockUntilShutdown() throws InterruptedException {
        if(server == null)
            return;
        server.awaitTermination();
    }
}

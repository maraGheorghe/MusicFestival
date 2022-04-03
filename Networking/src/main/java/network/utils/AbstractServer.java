package network.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class AbstractServer {
    private int port;
    private ServerSocket server = null;
    private static final Logger logger = LogManager.getLogger();

    public AbstractServer(int port){
        logger.info("Initializing AbstractServer.");
        this.port = port;
    }

    public void start() throws ServerException {
        logger.traceEntry("Starting AbstractServer.");
        try{
            server = new ServerSocket(port);
            while(true){
                logger.info("Waiting for clients...");
                System.out.println("Waiting for clients...");
                Socket client = server.accept();
                logger.info("Client connected...");
                System.out.println("Client connected...");
                processRequest(client);
            }
        } catch (IOException e) {
            logger.error(e);
            throw new ServerException("Starting server error: ", e);
        } finally {
            stop();
        }
    }

    protected abstract void processRequest(Socket client);

    public void stop() throws ServerException {
        logger.traceEntry("Stopping AbstractServer.");
        try {
            server.close();
            logger.traceExit();
        } catch (IOException e) {
            logger.error(e);
            throw new ServerException("Closing server error: ", e);
        }
    }
}

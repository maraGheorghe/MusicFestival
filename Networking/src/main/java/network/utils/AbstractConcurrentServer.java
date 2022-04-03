package network.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.Socket;

public abstract class AbstractConcurrentServer extends AbstractServer {

    private static final Logger logger = LogManager.getLogger();

    public AbstractConcurrentServer(int port) {
        super(port);
        logger.info("Initializing AbstractConcurrentServer.");
        System.out.println("Initializing AbstractConcurrentServer.");
    }

    protected abstract Thread createWorker(Socket client);

    @Override
    protected void processRequest(Socket client) {
        logger.traceEntry("Processing request.");
        Thread thread = createWorker(client);
        thread.start();
        logger.traceExit();
    }
}

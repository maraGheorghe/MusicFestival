package network.utils;

import network.rpc_protocol.ClientRpcWorker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.ServiceInterface;

import java.net.Socket;

public class RpcConcurrentServer extends AbstractConcurrentServer {
    private ServiceInterface server;
    private static final Logger logger = LogManager.getLogger();

    public RpcConcurrentServer(int port, ServiceInterface server) {
        super(port);
        logger.info("Initializing RpcConcurrentServer.");
        this.server = server;
    }

    @Override
    protected Thread createWorker(Socket client) {
        logger.traceEntry("Creating worker.");
        ClientRpcWorker worker = new ClientRpcWorker(server, client);
        logger.traceExit();
        return new Thread(worker);
    }

    @Override
    public void stop() {
        logger.traceEntry("Stopping services...");
        System.out.println("Stopping services...");
    }
}

package network.utils;

import network.rpc_protocol.ams.ClientAMSRpcWorker;
import services.IServiceAMS;

import java.net.Socket;

public class RpcAMSConcurrentServer extends AbstractConcurrentServer{
    private IServiceAMS server;

    public RpcAMSConcurrentServer(int port, IServiceAMS server) {
        super(port);
        this.server = server;
        System.out.println("RpcAMSConcurrentServer port: "+port);
    }

    @Override
    protected Thread createWorker(Socket client) {
        ClientAMSRpcWorker worker = new ClientAMSRpcWorker(server, client);
        Thread tw=new Thread(worker);
        return tw;
    }
}

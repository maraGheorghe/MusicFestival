import network.utils.AbstractServer;
import network.utils.RpcAMSConcurrentServer;
import network.utils.ServerException;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;

public class StartAMSRpcServer {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-server.xml");
        AbstractServer server = context.getBean("TCPServer", RpcAMSConcurrentServer.class);
        try {
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server: " + e.getMessage());
        }
    }
}

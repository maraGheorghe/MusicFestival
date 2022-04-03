package network.rpc_protocol;

import model.Performance;
import model.Ticket;
import model.User;
import services.ObserverInterface;
import services.ServiceException;
import services.ServiceInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.time.LocalDate;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ClientRpcWorker implements Runnable, ObserverInterface {
   private ServiceInterface server;
   private Socket connection;
   private ObjectInputStream input;
   private ObjectOutputStream output;
   private volatile boolean connected;
    private static final Logger logger = LogManager.getLogger();

    public ClientRpcWorker(ServiceInterface server, Socket connection) {
        logger.info("Initializing ClientRpcWorker.");
        this.server = server;
        this.connection = connection;
        try {
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(connected) {
            try {
                Object request = input.readObject();
                Response response = handleRequest((Request) request);
                if(response != null)
                    sendResponse(response);
            } catch (IOException | ClassNotFoundException e) {
                logger.error(e);
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error(e);
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            logger.error(e);
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void ticketBought(Ticket ticket) throws ServiceException {
        logger.traceEntry("Ticket bought: " + ticket);
        Response response = new Response.Builder().type(ResponseType.TICKET_BOUGHT).data(ticket).build();
        try {
            sendResponse(response);
            logger.traceExit();
        } catch (IOException e) {
            logger.error(e);
            throw new ServiceException("Error: " + e);
        }
    }

    private static Response okResponse = new Response.Builder().type(ResponseType.OK).build();

    private Response handleRequest(Request request){
        Response response = null;
        String handlerName = "handle" + (request).getType();
        logger.info("HandlerName: " + handlerName);
        System.out.println("Handler name: " + handlerName);
        try {
            Method method = this.getClass().getDeclaredMethod(handlerName, Request.class);
            response = (Response) method.invoke(this, request);
            System.out.println("Method " + handlerName + " invoked.");
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void sendResponse(Response response) throws IOException {
        logger.traceEntry("Sending response " + response);
        output.writeObject(response);
        output.flush();
        logger.traceExit();
    }

    private Response handleLOGIN(Request request) {
        logger.traceEntry("Login request... " + request.getType());
        User user = (User)request.getData();
        try {
            server.login(user.getUsername(), user.getPassword(), this);
            logger.traceExit();
            return okResponse;
        } catch (ServiceException e) {
            connected = false;
            logger.error(e);
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleLOGOUT(Request request) {
        logger.traceEntry("Logout request... " + request.getType());
        User user = (User)request.getData();
        try {
            server.logout(user, this);
            connected = false;
            logger.traceExit();
            return okResponse;
        } catch (ServiceException e) {
            logger.error(e);
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleGET_PERFORMANCES(Request request) {
        logger.traceEntry("Getting all performances... " + request.getType());
        try {
            List<Performance> performances = server.findAllPerformances();
            logger.traceExit();
            return new Response.Builder().type(ResponseType.GET_PERFORMANCES).data(performances).build();
        } catch (ServiceException e) {
            logger.error(e);
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleGET_PERFORMANCES_BY_DATE(Request request) {
        logger.traceEntry("Getting performances from one day... " + request.getType());
        LocalDate date = (LocalDate) request.getData();
        try {
            List<Performance> performances = server.findAllPerformancesForADay(date);
            logger.traceExit();
            return new Response.Builder().type(ResponseType.GET_PERFORMANCES_BY_DATE).data(performances).build();
        } catch (ServiceException e) {
            logger.error(e);
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleBUY_TICKET(Request request) {
        logger.traceEntry("Buying tickets... " + request.getType());
        Ticket ticket = (Ticket) request.getData();
        try {
            server.saveTicket(ticket.getPerformance().getID(), ticket.getPerformance().getDate(), ticket.getPerformance().getPlace(),
            ticket.getPerformance().getNoOfAvailableSeats(), ticket.getPerformance().getNoOfSoldSeats(), ticket.getPerformance().getArtist(),
                    ticket.getOwnerName(), ticket.getNoOfSeats());
            logger.traceExit();
            return okResponse;
        } catch (ServiceException e) {
            logger.error(e);
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }
}

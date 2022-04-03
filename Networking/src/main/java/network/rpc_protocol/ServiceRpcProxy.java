package network.rpc_protocol;

import model.Performance;
import model.Ticket;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.ObserverInterface;
import services.ServiceException;
import services.ServiceInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServiceRpcProxy implements ServiceInterface {
    private String host;
    private int port;
    private ObserverInterface client;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;
    private BlockingQueue<Response> responses;
    private volatile boolean finished;
    private static final Logger logger = LogManager.getLogger();

    public ServiceRpcProxy(String host, int port) {
        logger.info("Initializing ServiceRpcProxy.");
        this.host = host;
        this.port = port;
        responses = new LinkedBlockingQueue<Response>();
    }

    private void initializeConnection() throws ServiceException {
        logger.traceEntry("Initializing connection.");
        try {
            connection = new Socket(host, port);
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
            logger.traceExit();
        } catch (IOException e) {
            logger.error(e);
        }
    }

    private void closeConnection() {
        logger.traceEntry("Closing connection.");
        finished = true;
        try {
            input.close();
            output.close();
            connection.close();
            client = null;
            logger.traceExit();
        } catch (IOException e) {
            logger.error(e);
        }

    }

    private void sendRequest(Request request) throws ServiceException {
        logger.traceEntry("Sending request: {}.", request);
        try {
            output.writeObject(request);
            output.flush();
            logger.traceExit();
        } catch (IOException e) {
            logger.error(e);
            throw new ServiceException("Error: " + e);
        }
    }

    private Response readResponse() throws ServiceException {
        logger.traceEntry("Reading response.");
        Response response = null;
        try{
            response = responses.take();
        } catch (InterruptedException e) {
            logger.error(e);
            e.printStackTrace();
        }
        logger.traceExit();
        return response;
    }

    private void startReader() {
        logger.traceEntry("Starting reading.");
        Thread thread = new Thread(new ReaderThread());
        thread.start();
        logger.traceExit();
    }

    private void handleUpdate(Response response){
        logger.traceEntry("Handling update to the response: {}.", response);
        if (response.getType() == ResponseType.TICKET_BOUGHT){
            Ticket ticket = (Ticket) response.getData();
            logger.info("Ticket bought: {}.", ticket);
            try {
                client.ticketBought(ticket);
                logger.traceExit();
            } catch (ServiceException e) {
                logger.error(e);
            }
        }
    }

    private boolean isUpdate(Response response) {
        return response.getType() == ResponseType.TICKET_BOUGHT;
    }

    @Override
    public void login(String username, String password, ObserverInterface client) throws ServiceException {
        logger.traceEntry("Logging in user: {}", username);
        initializeConnection();
        Request request = new Request.Builder().type(RequestType.LOGIN).data(new User(username, password)).build();
        sendRequest(request);
        Response response = readResponse();
        if (response.getType() == ResponseType.OK) {
            this.client = client;
            logger.traceExit();
            return;
        }
        if (response.getType() == ResponseType.ERROR){
            String error = response.getData().toString();
            closeConnection();
            logger.error(error);
            throw new ServiceException(error);
        }
    }

    @Override
    public void logout(User user, ObserverInterface client) throws ServiceException {
        logger.traceEntry("Logging out user: {}", user.getUsername());
        Request request = new Request.Builder().type(RequestType.LOGOUT).data(user).build();
        sendRequest(request);
        Response response = readResponse();
        closeConnection();
        if (response.getType() == ResponseType.ERROR){
            String error = response.getData().toString();
            logger.error(error);
            throw new ServiceException(error);
        }
        logger.traceExit();
    }

    @Override
    public List<Performance> findAllPerformances() throws ServiceException {
        logger.traceEntry("Finding all performances.");
        Request request = new Request.Builder().type(RequestType.GET_PERFORMANCES).build();
        return getPerformances(request);
    }

    @Override
    public List<Performance> findAllPerformancesForADay(LocalDate date) throws ServiceException {
        logger.traceEntry("Finding all performances for day {}.", date);
        Request request = new Request.Builder().type(RequestType.GET_PERFORMANCES_BY_DATE).data(date).build();
        return getPerformances(request);
    }

    private List<Performance> getPerformances(Request request) throws ServiceException {
        sendRequest(request);
        Response response = readResponse();
        if(response.getType() == ResponseType.ERROR) {
            String error = response.getData().toString();
            logger.error(error);
            throw new ServiceException(error);
        }
        List<Performance> performances = (List<Performance>)response.getData();
        return performances;
    }

    @Override
    public void saveTicket(Long performanceID, LocalDateTime date, String place,
                           Integer noOfAvailableSeats, Integer noOfSoldSeats, String artist, String owner,
                           int noOfSeats) throws ServiceException {
        logger.traceEntry("Saving ticket...");
        Performance performance = new Performance(performanceID, date, place, noOfAvailableSeats, noOfSoldSeats, artist);
        Ticket ticket = new Ticket(performance, owner, noOfSeats);
        Request request = new Request.Builder().type(RequestType.BUY_TICKET).data(ticket).build();
        sendRequest(request);
        Response response = readResponse();
        if(response.getType() == ResponseType.TICKET_BOUGHT) {
            logger.traceExit();
            return;
        }
        if(response.getType() == ResponseType.ERROR) {
            String error = response.getData().toString();
            logger.error(error);
            throw new ServiceException(error);
        }
    }

    private class ReaderThread implements Runnable {
        public void run() {
            while (!finished) {
                try {
                    Object response = input.readObject();
                    System.out.println("Response received " + response);
                    if (isUpdate((Response) response)) {
                        handleUpdate((Response) response);
                    } else try {
                            responses.put((Response) response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Reading error " + e);
                    logger.error(e);
                }
            }
        }
    }
}

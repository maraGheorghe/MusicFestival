package network.rpc_protocol.ams;

import model.Performance;
import model.Ticket;
import model.User;
import network.rpc_protocol.*;
import services.IClient;
import services.IServiceAMS;
import services.ObserverInterface;
import services.ServiceException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerAMSRpcProxy implements IServiceAMS {
    private String host;
    private int port;
    private IClient client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;
    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    public ServerAMSRpcProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses=new LinkedBlockingQueue<Response>();
    }

    private void initializeConnection() throws ServiceException {
        try {
            connection = new Socket(host, port);
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        finished = true;
        try {
            input.close();
            output.close();
            connection.close();
            client = null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendRequest(Request request) throws ServiceException {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new ServiceException("Error: " + e);
        }
    }

    private Response readResponse() throws ServiceException {
        Response response = null;
        try{
            response = qresponses.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void startReader() {
        Thread thread = new Thread(new ReaderThread());
        thread.start();
    }

    private boolean isUpdate(Response response) {
        return response.getType() == ResponseType.TICKET_BOUGHT;
    }

    @Override
    public void login(String username, String password) throws ServiceException {
        initializeConnection();
        Request request = new Request.Builder().type(RequestType.LOGIN).data(new User(username, password)).build();
        sendRequest(request);
        Response response = readResponse();
        if (response.getType() == ResponseType.OK) {
            return;
        }
        if (response.getType() == ResponseType.ERROR){
            String error = response.getData().toString();
            closeConnection();
            throw new ServiceException(error);
        }
    }

    @Override
    public void logout(User user) throws ServiceException {
        Request request = new Request.Builder().type(RequestType.LOGOUT).data(user).build();
        sendRequest(request);
        Response response = readResponse();
        closeConnection();
        if (response.getType() == ResponseType.ERROR){
            String error = response.getData().toString();
            throw new ServiceException(error);
        }
    }

    @Override
    public List<Performance> findAllPerformances() throws ServiceException {
        Request request = new Request.Builder().type(RequestType.GET_PERFORMANCES).build();
        return getPerformances(request);
    }

    @Override
    public List<Performance> findAllPerformancesForADay(LocalDate date) throws ServiceException {
        Request request = new Request.Builder().type(RequestType.GET_PERFORMANCES_BY_DATE).data(date).build();
        return getPerformances(request);
    }

    private List<Performance> getPerformances(Request request) throws ServiceException {
        sendRequest(request);
        Response response = readResponse();
        if(response.getType() == ResponseType.ERROR) {
            String error = response.getData().toString();
            throw new ServiceException(error);
        }
        List<Performance> performances = (List<Performance>)response.getData();
        return performances;
    }

    @Override
    public void saveTicket(Long performanceID, LocalDateTime date, String place,
                           Integer noOfAvailableSeats, Integer noOfSoldSeats, String artist, String owner,
                           int noOfSeats) throws ServiceException {
        Performance performance = new Performance(performanceID, date, place, noOfAvailableSeats, noOfSoldSeats, artist);
        Ticket ticket = new Ticket(performance, owner, noOfSeats);
        Request request = new Request.Builder().type(RequestType.BUY_TICKET).data(ticket).build();
        sendRequest(request);
        Response response = readResponse();
        if(response.getType() == ResponseType.TICKET_BOUGHT) {
            return;
        }
        if(response.getType() == ResponseType.ERROR) {
            String error = response.getData().toString();
            throw new ServiceException(error);
        }
    }

    private class ReaderThread implements Runnable {
        public void run() {
            while (!finished) {
                try {
                    Object response = input.readObject();
                    System.out.println("Response received " + response);
                    if (!isUpdate((Response) response)) {
                        try {
                            qresponses.put((Response) response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Reading error " + e);
                }
            }
        }
    }
}

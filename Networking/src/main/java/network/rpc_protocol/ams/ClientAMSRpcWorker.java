package network.rpc_protocol.ams;


import model.Performance;
import model.Ticket;
import model.User;
import network.rpc_protocol.Request;
import network.rpc_protocol.Response;
import network.rpc_protocol.ResponseType;
import services.IServiceAMS;
import services.ServiceException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.time.LocalDate;
import java.util.List;

public class ClientAMSRpcWorker implements Runnable {
    private IServiceAMS server;
    private Socket connection;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;
    public ClientAMSRpcWorker(IServiceAMS server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try{
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            connected=true;
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
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static Response okResponse = new Response.Builder().type(ResponseType.OK).build();

    private Response handleRequest(Request request){
        Response response = null;
        String handlerName = "handle" + (request).getType();
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
        output.writeObject(response);
        output.flush();
    }

    private Response handleLOGIN(Request request) {
        User user = (User)request.getData();
        try {
            server.login(user.getUsername(), user.getPassword());
            return okResponse;
        } catch (ServiceException e) {
            connected = false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleLOGOUT(Request request) {
        User user = (User)request.getData();
        try {
            server.logout(user);
            connected = false;
            return okResponse;
        } catch (ServiceException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleGET_PERFORMANCES(Request request) {
        try {
            List<Performance> performances = server.findAllPerformances();
            return new Response.Builder().type(ResponseType.GET_PERFORMANCES).data(performances).build();
        } catch (ServiceException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleGET_PERFORMANCES_BY_DATE(Request request) {
        LocalDate date = (LocalDate) request.getData();
        try {
            List<Performance> performances = server.findAllPerformancesForADay(date);
            return new Response.Builder().type(ResponseType.GET_PERFORMANCES_BY_DATE).data(performances).build();
        } catch (ServiceException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleBUY_TICKET(Request request) {
        Ticket ticket = (Ticket) request.getData();
        try {
            server.saveTicket(ticket.getPerformance().getID(), ticket.getPerformance().getDate(), ticket.getPerformance().getPlace(),
                    ticket.getPerformance().getNoOfAvailableSeats(), ticket.getPerformance().getNoOfSoldSeats(), ticket.getPerformance().getArtist(),
                    ticket.getOwnerName(), ticket.getNoOfSeats());
            return okResponse;
        } catch (ServiceException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }
}

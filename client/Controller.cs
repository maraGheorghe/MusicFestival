using System;
using System.Collections.Generic;
using model;
using networking;
using service;

namespace client;

public class Controller: IObserver
{
    private readonly IService server;
    private User currentUser;
    public event EventHandler<Ticket> updateEvent;

    public Controller(IService server)
    {
        this.server = server;
        currentUser = null;
    }
    
    public void Login(String username, String password)
    {
        User user = new User(username,password);
        server.Login(username, password,this);
        Console.WriteLine("Login succeeded ....");
        currentUser = user;
        Console.WriteLine("Current user: {0}.", user);
    }
    
    public void Logout()
    {
        Console.WriteLine("Controller logout.");
        server.Logout(currentUser, this);
        currentUser = null;
    }

    public void BuyTicket(Performance performance, String ownerName, int numberOfSeats)
    {
        server.SaveTicket(performance.Id, performance.Date, performance.Place, performance.NoOfAvailableSeats,
            performance.NoOfSoldSeats, performance.Artist, ownerName, numberOfSeats);
    }

    public List<Performance> GetAllPerformances()
    {
        List<Performance> performances = server.FindAllPerformances();
        return performances;
    }
    
    public List<Performance> GetAllPerformancesByDate(DateTime date)
    {
        List<Performance> performances = server.FindAllPerformancesForADay(date);
        return performances;
    }
    public void TicketBought(Ticket ticket)
    {
        updateEvent?.Invoke(this, ticket);
    }
}
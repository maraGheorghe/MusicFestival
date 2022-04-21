using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using Grpc.Core;
using model;
using service;

namespace client;

public class Controller: IObserver
{
    private readonly MusicFestivalService.MusicFestivalServiceClient server;
    private User currentUser;
    private AsyncDuplexStreamingCall<BuyTicketRequest, TicketBoughtMessageFromService> stream;
    public event EventHandler<Ticket> updateEvent;

    public Controller(MusicFestivalService.MusicFestivalServiceClient server)
    {
        this.server = server;
        currentUser = null;
    }
    
    public void Login(String username, String password)
    {
        User user = new User(username,password);
        var response = server.login(new LoginRequest() {Username = username, Password = password});
        if (response.Type == 0)
             currentUser = user;
        else throw new ServiceException(response.Error);
        Console.WriteLine("Current user: {0}.", user);
        stream = server.ticketBought();
        Task.Run(async () =>
        {
            while (await stream.ResponseStream.MoveNext(CancellationToken.None))
            {
                var ticketMessage = stream.ResponseStream.Current.Ticket;
                var dateMessage = ticketMessage.Performance.Date;
                var date = new DateTime(dateMessage.Year, dateMessage.Month, dateMessage.Day, dateMessage.Hour,
                    dateMessage.Minute, 0);
                Performance performance = new Performance(ticketMessage.Performance.Id, date, ticketMessage.Performance.Place,
                    ticketMessage.Performance.NoOfAvailableSeats, ticketMessage.Performance.NoOfSoldSeats,
                    ticketMessage.Performance.Artist);
                var ticket = new Ticket(performance, ticketMessage.Owner, ticketMessage.NumberOfSeats);
                TicketBought(ticket);
            }
        });
    }
    
    public async void Logout()
    {
        server.logout(new LogoutRequest() {Username = currentUser.Username, Password = currentUser.Password});
        currentUser = null;
        await this.stream.RequestStream.CompleteAsync();
        stream.Dispose();
    }

    public void BuyTicket(Performance performance, String ownerName, int numberOfSeats)
    {
        DateTime date = performance.Date;
        DateTimeMessage dateTimeMessage = new DateTimeMessage() { Year = date.Year, Month = date.Month, Day = date.Day, Hour = date.Hour, Minute = date.Minute };
        PerformanceMessage performanceMessage = new PerformanceMessage()
        {
            Id = performance.Id, Date = dateTimeMessage, Place = performance.Place,
            NoOfAvailableSeats = performance.NoOfAvailableSeats, NoOfSoldSeats = performance.NoOfSoldSeats,
            Artist = performance.Artist
        };
        var response = server.buyTicket(new BuyTicketRequest()
            {Owner = ownerName, NumberOfSeats = numberOfSeats, Performance = performanceMessage});
        if (response.Type == 1)
            throw new ServiceException(response.Error);
    }

    public List<Performance> GetAllPerformances()
    {
        var performances = server.findAllPerformances(new GetPerformancesRequest(){})
            .Performances
            .Select(p =>
            {
                DateTime date = new DateTime(p.Date.Year, p.Date.Month, p.Date.Day, p.Date.Hour, p.Date.Minute, 0);
                return new Performance(p.Id, date, p.Place, p.NoOfAvailableSeats, p.NoOfSoldSeats,
                        p.Artist);
            }).ToList();
        return performances;
    }
    
    public List<Performance> GetAllPerformancesByDate(DateTime date)
    {
        DateTimeMessage dateTimeMessage = new DateTimeMessage() { Year = date.Year, Month = date.Month, Day = date.Day, Hour = date.Hour, Minute = date.Minute};
        var performances = server.findAllPerformancesByDate(new GetPerformancesByDateRequest(){ Date = dateTimeMessage })
            .Performances
            .Select(p => new Performance(p.Id, date, p.Place, p.NoOfAvailableSeats, p.NoOfSoldSeats, p.Artist)).ToList();
        return performances;
    }

    public void TicketBought(Ticket ticket)
    {
        updateEvent?.Invoke(this, ticket);
    }
}
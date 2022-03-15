using System;
using MusicFestivalMPP.model;
using NUnit.Framework;

namespace Tests.model;

public class TicketTest
{
    private Ticket _ticket; 
    
    [SetUp]
    public void Setup()
    {
        _ticket = new Ticket(
            1, new Performance(1, new DateTime(2022, 8, 26), "CJ", 100, 200, "artist"), "owner", 3
        );
    }

    [Test]
    public void GetPerformance()
    {
        Assert.AreEqual(_ticket.Performance.Id, 1);
    }

    [Test]
    public void SetPerformance()
    {
        _ticket.Performance.Id = 2;
        Assert.AreEqual(_ticket.Performance.Id, 2);
    }
    
    [Test]
    public void GetOwnerName()
    {
        Assert.AreEqual(_ticket.OwnerName, "owner");
    }

    [Test]
    public void SetOwnerName()
    {
        _ticket.OwnerName = "name";
        Assert.AreEqual(_ticket.OwnerName, "name");
    }
    
    [Test]
    public void GetNoOfSeats()
    {
        Assert.AreEqual(_ticket.NoOfSeats, 3);
    }

    [Test]
    public void SetNoOfSeats()
    {
        _ticket.NoOfSeats = 2;
        Assert.AreEqual(_ticket.NoOfSeats, 2);
    }
}
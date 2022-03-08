using System;
using System.Collections.Generic;
using MusicFestivalMPP.model;
using NUnit.Framework;

namespace Tests.model;

public class PerformanceTest
{
    private Performance _performance;
    private Performance _performance0; 
    
    [SetUp]
    public void Setup()
    {
        Artist artist = new Artist("21P");
        DateOnly date = new DateOnly(2022, 8, 26);
        _performance = new Performance(1, new DateOnly(2022, 8, 26), "CJ", 100, 200, artist);
        _performance0 = new Performance(date, "CJ", 100, 200, artist);
    }

    [Test]
    public void GetDate()
    {
        Assert.AreEqual(_performance.Date, new DateOnly(2022, 8, 26));
        Assert.AreEqual(_performance0.Date, new DateOnly(2022, 8, 26));
    }
    
    [Test]
    public void SetDate()
    {
        _performance.Date = new DateOnly(2022, 3, 22);
        Assert.AreEqual(_performance.Date, new DateOnly(2022, 3, 22));
        Assert.AreEqual(_performance0.Date, new DateOnly(2022, 8, 26));
    }
    
    [Test]
    public void GetPlace()
    {
        Assert.AreEqual(_performance.Place, "CJ");
    }
    
    [Test]
    public void SetPlace()
    {
        _performance.Place = "Cluj-Napoca";
        Assert.AreEqual(_performance.Place, "Cluj-Napoca");
    }
    
    [Test]
    public void GetAvailable()
    {
        Assert.AreEqual(_performance.NoOfAvailableTickets, 100);
    }
    
    [Test]
    public void SetAvailable()
    {
        _performance.NoOfAvailableTickets = 105;
        Assert.AreEqual(_performance.NoOfAvailableTickets, 105);
    }
    
    [Test]
    public void GetSold()
    {
        Assert.AreEqual(_performance.NoOfSoldTickets, 200);
    }
    
    [Test]
    public void SetSold()
    {
        _performance.NoOfSoldTickets = 195;
        Assert.AreEqual(_performance.NoOfSoldTickets, 195);
    }
    
    [Test]
    public void GetArtists()
    {
        Artist artist = new Artist("21P");
        Assert.AreEqual(_performance.Artist, artist);
    }
    
    [Test]
    public void SetArtists()
    {
        Artist artist = new Artist("Kygo");
        _performance.Artist = artist;
        Assert.AreEqual(_performance.Artist, artist);
    }
}

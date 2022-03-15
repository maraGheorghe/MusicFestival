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
        DateTime date = new DateTime(2022, 8, 26);
        _performance = new Performance(1, new DateTime(2022, 8, 26), "CJ", 100, 200, "21P");
        _performance0 = new Performance(date, "CJ", 100, 200, "21P");
    }

    [Test]
    public void GetDate()
    {
        Assert.AreEqual(_performance.Date, new DateTime(2022, 8, 26));
        Assert.AreEqual(_performance0.Date, new DateTime(2022, 8, 26));
    }
    
    [Test]
    public void SetDate()
    {
        _performance.Date = new DateTime(2022, 3, 22);
        Assert.AreEqual(_performance.Date, new DateTime(2022, 3, 22));
        Assert.AreEqual(_performance0.Date, new DateTime(2022, 8, 26));
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
        Assert.AreEqual(_performance.NoOfAvailableSeats, 100);
    }
    
    [Test]
    public void SetAvailable()
    {
        _performance.NoOfAvailableSeats = 105;
        Assert.AreEqual(_performance.NoOfAvailableSeats, 105);
    }
    
    [Test]
    public void GetSold()
    {
        Assert.AreEqual(_performance.NoOfSoldSeats, 200);
    }
    
    [Test]
    public void SetSold()
    {
        _performance.NoOfSoldSeats = 195;
        Assert.AreEqual(_performance.NoOfSoldSeats, 195);
    }
    
    [Test]
    public void GetArtists()
    {
        Assert.AreEqual(_performance.Artist, "21P");
    }
    
    [Test]
    public void SetArtists()
    {
        _performance.Artist = "Kygo";
        Assert.AreEqual(_performance.Artist, "Kygo");
    }
}

using MusicFestivalMPP.model;
using NUnit.Framework;

namespace Tests.model;

public class ArtistTest
{
    private Artist _artist;
    private Artist _artist0; 
    
    [SetUp]
    public void Setup()
    {
        _artist = new Artist(2, "stefan");
        _artist0 = new Artist("mara");
    }

    [Test]
    public void GetName()
    {
        Assert.AreEqual(_artist.Name, "stefan");
        Assert.AreEqual(_artist0.Name, "mara");
    }
    
    [Test]
    public void SetName()
    {
        _artist.Name = "Stefan";
        Assert.AreEqual(_artist.Name, "Stefan");
        Assert.AreEqual(_artist0.Name, "mara");
    }
}
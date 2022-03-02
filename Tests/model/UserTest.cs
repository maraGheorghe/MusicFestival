using MusicFestivalMPP.model;
using NUnit.Framework;

namespace Tests.model;

public class UserTest
{
    private User _performance;
    private User _user0; 
    
    [SetUp]
    public void Setup()
    {
        _performance = new User(2, "stefan", "parola");
        _user0 = new User("mara", "parola");
    }

    [Test]
    public void GetUsername()
    {
        Assert.AreEqual(_performance.Username, "stefan");
        Assert.AreEqual(_user0.Username, "mara");
    }
    
    [Test]
    public void SetUsername()
    {
        _performance.Username = "oficiul lui Stefan";
        Assert.AreEqual(_performance.Username, "oficiul lui Stefan");
        Assert.AreEqual(_user0.Username, "mara");
    }
    
    [Test]
    public void GetPassword()
    {
        Assert.AreEqual(_performance.Password, "parola");
        Assert.AreEqual(_user0.Password, "parola");
    }
    
    [Test]
    public void SetPassword()
    {
        _user0.Password = "parola00";
        Assert.AreEqual(_performance.Password, "parola");
        Assert.AreEqual(_user0.Password, "parola00");
    }
}
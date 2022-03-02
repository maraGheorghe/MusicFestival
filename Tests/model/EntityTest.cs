using MusicFestivalMPP.model;
using NUnit.Framework;

namespace Tests.model;

public class EntityTest
{
    private Entity _entity;
    private Entity _entity0; 
    
    [SetUp]
    public void Setup()
    {
        _entity = new Entity
        {
            Id = 3
        };
        _entity0 = new Entity();
        _entity0.Id = 3;
    }

    [Test]
    public void GetId()
    {
        Assert.AreEqual(_entity.Id, 3);
    }
    
    [Test]
    public void SetId()
    {
        _entity.Id = 2;
        Assert.AreEqual(_entity.Id, 2);
    }
    
    [Test]
    public void Equal()
    {
        Assert.True(_entity.Equals(_entity0));
        Assert.True(_entity == _entity0);
        _entity0.Id = 2;
        Assert.True(_entity != _entity0);
    }
    
    [Test]
    public void GetHashCode()
    {
        Assert.AreEqual(_entity.GetHashCode(), _entity0.GetHashCode());
    }
}
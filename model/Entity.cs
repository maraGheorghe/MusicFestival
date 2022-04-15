using System;

namespace model;

[Serializable] 
public class Entity
{
    public long Id { get; set; }

    public Entity(long id)
    {
        this.Id = id;
    }

    public Entity()
    {
        
    }

    public override bool Equals(object? obj)
    {
        if (obj == null)
        {
            return false;
        }

        if (!(obj is Entity))
        {
            return false;
        }

        return (this.Id == ((Entity) obj).Id);
    }
    
    public static bool operator ==(Entity entity0, Entity entity1)
    {
        return entity0.Equals(entity1);
    }

    public static bool operator !=(Entity entity0, Entity entity1)
    {
        return !(entity0 == entity1);
    }

    public override int GetHashCode()
    {
        return Id.GetHashCode();
    }
}
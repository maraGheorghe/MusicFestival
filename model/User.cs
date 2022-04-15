using System;

namespace model;
[Serializable]
public class User: Entity
{
    public virtual String Username { get; set; }
    public virtual String Password { get; set; }
    
    public User(string username, string password)
    {
        Username = username;
        Password = password;
    }

    public User(long id, string username, string password) : base(id)
    {
        Username = username;
        Password = password;
    }
}
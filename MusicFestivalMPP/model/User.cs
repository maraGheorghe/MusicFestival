namespace MusicFestivalMPP.model;

public class User: Entity
{
    public String Username { get; set; }
    public String Password { get; set; }
    
    public User(string username, string password)
    {
        this.Username = username;
        this.Password = password;
    }

    public User(long id, string username, string password) : base(id)
    {
        Username = username;
        Password = password;
    }
}
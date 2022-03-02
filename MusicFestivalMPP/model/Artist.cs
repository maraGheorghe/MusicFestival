namespace MusicFestivalMPP.model;

public class Artist: Entity
{
    public String Name { get; set; }

    public Artist(long id, string name) : base(id)
    {
        Name = name;
    }

    public Artist(string name)
    {
        Name = name;
    }
}
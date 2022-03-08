namespace MusicFestivalMPP.model;

public class Performance: Entity
{
    public DateOnly Date { get; set; }
    public String Place { get; set; }
    public int NoOfAvailableTickets { get; set; }
    public int NoOfSoldTickets { get; set; }
    public Artist Artist { get; set; }

    public Performance(long id, DateOnly date, string place, int noOfAvailableTickets, int noOfSoldTickets, Artist artist) : base(id)
    {
        Date = date;
        Place = place;
        NoOfAvailableTickets = noOfAvailableTickets;
        NoOfSoldTickets = noOfSoldTickets;
        Artist = artist;
    }

    public Performance(DateOnly date, string place, int noOfAvailableTickets, int noOfSoldTickets, Artist artist)
    {
        Date = date;
        Place = place;
        NoOfAvailableTickets = noOfAvailableTickets;
        NoOfSoldTickets = noOfSoldTickets;
        Artist = artist;
    }
}
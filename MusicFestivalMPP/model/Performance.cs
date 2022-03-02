namespace MusicFestivalMPP.model;

public class Performance: Entity
{
    public DateOnly Date { get; set; }
    public String Place { get; set; }
    public int NoOfAvailableTickets { get; set; }
    public int NoOfSoldTickets { get; set; }
    public List<Artist> Artists { get; set; }

    public Performance(long id, DateOnly date, string place, int noOfAvailableTickets, int noOfSoldTickets, List<Artist> artists) : base(id)
    {
        Date = date;
        Place = place;
        NoOfAvailableTickets = noOfAvailableTickets;
        NoOfSoldTickets = noOfSoldTickets;
        Artists = artists;
    }

    public Performance(DateOnly date, string place, int noOfAvailableTickets, int noOfSoldTickets, List<Artist> artists)
    {
        Date = date;
        Place = place;
        NoOfAvailableTickets = noOfAvailableTickets;
        NoOfSoldTickets = noOfSoldTickets;
        Artists = artists;
    }
}
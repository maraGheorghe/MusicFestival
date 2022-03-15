using System;

namespace MusicFestivalMPP.model;

public class Ticket: Entity
{
    public Performance Performance { get; set; }
    
    public String OwnerName { get; set; }
    
    public int NoOfSeats { get; set; }

    public Ticket(long id, Performance performance, String ownerName, int noOfSeats) : base(id)
    {
        Performance = performance;
        OwnerName = ownerName;
        NoOfSeats = noOfSeats;
    }

    public Ticket(Performance performance, String ownerName, int noOfSeats)
    {
        Performance = performance;
        OwnerName = ownerName;
        NoOfSeats = noOfSeats;
    }
}

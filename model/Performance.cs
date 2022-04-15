﻿using System;

namespace model;
[Serializable]
public class Performance: Entity
{
    public DateTime Date { get; set; }
    public String Place { get; set; }
    public int NoOfAvailableSeats { get; set; }
    public int NoOfSoldSeats { get; set; }
    public String Artist { get; set; }

    public Performance(long id, DateTime date, string place, int noOfAvailableSeats, int noOfSoldSeats, String artist) : base(id)
    {
        Date = date;
        Place = place;
        NoOfAvailableSeats = noOfAvailableSeats;
        NoOfSoldSeats = noOfSoldSeats;
        Artist = artist;
    }

    public Performance(DateTime date, string place, int noOfAvailableSeats, int noOfSoldSeats, String artist)
    {
        Date = date;
        Place = place;
        NoOfAvailableSeats = noOfAvailableSeats;
        NoOfSoldSeats = noOfSoldSeats;
        Artist = artist;
    }

    public override string ToString()
    {
        return  Artist +
                " live at " + Date.ToString("dd/MM/yyyy HH:mm") +
                " in " + Place +
                ", available seats: " + NoOfAvailableSeats +
                " sold seats: " + NoOfSoldSeats;
    }
}
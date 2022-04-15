using System;
using model;

namespace networking
{
    [Serializable]
    public class PerformanceDto
    {
            public long Id { get; set; }
            public DateTime Date { get; set; }
            public String Place { get; set; }
            public int NoOfAvailableSeats { get; set; }
            public int NoOfSoldSeats { get; set; }
            public String Artist { get; set; }


            public PerformanceDto(long performanceId, DateTime date, String place, int noOfAvailableSeats, int noOfSoldSeats, String artist) {
            Id = performanceId;
            Date = date;
            Place = place;
            NoOfAvailableSeats = noOfAvailableSeats;
            NoOfSoldSeats = noOfSoldSeats;
            Artist = artist;
        }
            
        public Performance GetPerformance() {
            return new Performance(Id, Date, Place, NoOfAvailableSeats, NoOfSoldSeats, Artist);
        }
        
        public override String ToString() {
            return  Artist +
                    " live at " + Date.ToString("dd/MM/yyyy HH:mm") +
                    " in " + Place +
                    ", available seats: " + NoOfAvailableSeats;
        }
    }
}
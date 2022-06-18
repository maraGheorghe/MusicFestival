package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Performance extends Entity {
    private LocalDateTime date;
    private String place;
    private Integer noOfAvailableSeats;
    private Integer noOfSoldSeats;
    private String artist;

    public Performance(LocalDateTime date, String place, Integer noOfAvailableSeats, Integer noOfSoldSeats, String artist) {
        this.date = date;
        this.place = place;
        this.noOfAvailableSeats = noOfAvailableSeats;
        this.noOfSoldSeats = noOfSoldSeats;
        this.artist = artist;
    }

    public Performance(Long performanceID, LocalDateTime date, String place, Integer noOfAvailableSeats, Integer noOfSoldSeats, String artist) {
        setID(performanceID);
        this.date = date;
        this.place = place;
        this.noOfAvailableSeats = noOfAvailableSeats;
        this.noOfSoldSeats = noOfSoldSeats;
        this.artist = artist;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getNoOfAvailableSeats() {
        return noOfAvailableSeats;
    }

    public void setNoOfAvailableSeats(Integer noOfAvailableSeats) {
        this.noOfAvailableSeats = noOfAvailableSeats;
    }

    public Integer getNoOfSoldSeats() {
        return noOfSoldSeats;
    }

    public void setNoOfSoldSeats(Integer noOfSoldSeats) {
        this.noOfSoldSeats = noOfSoldSeats;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtists(String artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return  artist +
                " live at " + date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) +
                " in " + place +
                ", available seats: " + noOfAvailableSeats +
                " sold seats: " + noOfSoldSeats +
                " (ID: " + super.getID() + ")";
    }
}

package model;

import java.time.LocalDate;

public class Performance extends Entity {
    private LocalDate date;
    private String place;
    private Integer noOfAvailableSeats;
    private Integer noOfSoldSeats;
    private String artist;

    public Performance(LocalDate date, String place, Integer noOfAvailableSeats, Integer noOfSoldSeats, String artist) {
        this.date = date;
        this.place = place;
        this.noOfAvailableSeats = noOfAvailableSeats;
        this.noOfSoldSeats = noOfSoldSeats;
        this.artist = artist;
    }

    public Performance(Long performanceID, LocalDate date, String place, Integer noOfAvailableSeats, Integer noOfSoldSeats, String artist) {
        setID(performanceID);
        this.date = date;
        this.place = place;
        this.noOfAvailableSeats = noOfAvailableSeats;
        this.noOfSoldSeats = noOfSoldSeats;
        this.artist = artist;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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
}

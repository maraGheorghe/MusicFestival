package model;

import java.time.LocalDate;
import java.util.List;

public class Performance extends Entity {
    private LocalDate date;
    private String place;
    private Integer noOfAvailableTickets;
    private Integer noOfSoldTickets;
    private List<Artist> artists;

    public Performance(LocalDate date, String place, Integer noOfAvailableTickets, Integer noOfSoldTickets, List<Artist> artists) {
        this.date = date;
        this.place = place;
        this.noOfAvailableTickets = noOfAvailableTickets;
        this.noOfSoldTickets = noOfSoldTickets;
        this.artists = artists;
    }

    public Performance(Long performanceID, LocalDate date, String place, Integer noOfAvailableTickets, Integer noOfSoldTickets, List<Artist> artists) {
        setID(performanceID);
        this.date = date;
        this.place = place;
        this.noOfAvailableTickets = noOfAvailableTickets;
        this.noOfSoldTickets = noOfSoldTickets;
        this.artists = artists;
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

    public Integer getNoOfAvailableTickets() {
        return noOfAvailableTickets;
    }

    public void setNoOfAvailableTickets(Integer noOfAvailableTickets) {
        this.noOfAvailableTickets = noOfAvailableTickets;
    }

    public Integer getNoOfSoldTickets() {
        return noOfSoldTickets;
    }

    public void setNoOfSoldTickets(Integer noOfSoldTickets) {
        this.noOfSoldTickets = noOfSoldTickets;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }
}

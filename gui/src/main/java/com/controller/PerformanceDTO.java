package com.controller;

import model.Performance;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PerformanceDTO {
    private Long ID;
    private LocalDateTime date;
    private String place;
    private Integer noOfAvailableSeats;
    private Integer noOfSoldSeats;
    private String artist;


    public PerformanceDTO(Long performanceID, LocalDateTime date, String place, Integer noOfAvailableSeats, Integer noOfSoldSeats, String artist) {
        this.ID = performanceID;
        this.date = date;
        this.place = place;
        this.noOfAvailableSeats = noOfAvailableSeats;
        this.noOfSoldSeats = noOfSoldSeats;
        this.artist = artist;
    }

    public Long getID() {
        return ID;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getPlace() {
        return place;
    }

    public Integer getNoOfAvailableSeats() {
        return noOfAvailableSeats;
    }

    public Integer getNoOfSoldSeats() {
        return noOfSoldSeats;
    }

    public String getArtist() {
        return artist;
    }

    public Performance getPerformance() {
        return new Performance(this.ID, this.date, this.place, this.noOfAvailableSeats, this.noOfSoldSeats, this.artist);
    }


    @Override
    public String toString() {
        return  artist +
                " live at " + date.format(DateTimeFormatter.ofPattern("HH:mm")) +
                " in " + place +
                ", available seats: " + noOfAvailableSeats;
    }
}

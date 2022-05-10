package model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import org.hibernate.annotations.GenericGenerator;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@javax.persistence.Entity
@Table(name = "performances")
public class Performance extends Entity {
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
    private String place;
    private Integer noOfAvailableSeats;
    private Integer noOfSoldSeats;
    private String artist;
    private Integer noOfSeats;
    private Set<Ticket> tickets;

    public Performance() {}

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

    @Id
    @Column(name = "performance_id")
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Override
    public Long getID() {
        return super.getID();
    }

    @Column(name = "performance_date")
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

    @Transient
    public Integer getNoOfAvailableSeats() {
        return noOfAvailableSeats;
    }

    public void setNoOfAvailableSeats(Integer noOfAvailableSeats) {
        this.noOfAvailableSeats = noOfAvailableSeats;
    }

    @Transient
    public Integer getNoOfSoldSeats() {
        return noOfSoldSeats;
    }

    public void setNoOfSoldSeats(Integer noOfSoldSeats) {
        this.noOfSoldSeats = noOfSoldSeats;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @JsonIgnore
    @Column(name = "no_of_seats")
    public Integer getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(Integer noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    @JsonIgnore
    @OneToMany(mappedBy="performance")
    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        noOfSoldSeats = tickets.stream().map(Ticket::getNoOfSeats).reduce(Integer::sum).orElse(0);
        this.noOfAvailableSeats = noOfSeats - noOfSoldSeats;
        this.tickets = tickets;
    }

    @Override
    public String toString() {
        return  artist +
                " live at " + date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) +
                " in " + place +
                ", available seats: " + noOfAvailableSeats +
                " sold seats: " + noOfSoldSeats;
    }
}

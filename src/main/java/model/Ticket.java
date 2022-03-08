package model;

public class Ticket extends Entity {
    private Performance performance;
    private String ownerName;
    private Integer noOfSeats;

    public Ticket(Performance performance, String ownerName, Integer noOfSeats) {
        this.performance = performance;
        this.ownerName = ownerName;
        this.noOfSeats = noOfSeats;
    }

    public Ticket(Long ticketID, Performance performance, String ownerName, Integer noOfSeats) {
        setID(ticketID);
        this.performance = performance;
        this.ownerName = ownerName;
        this.noOfSeats = noOfSeats;
    }

    public Performance getPerformance() {
        return performance;
    }

    public void setPerformance(Performance performance) {
        this.performance = performance;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Integer getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(Integer noOfSeats) {
        this.noOfSeats = noOfSeats;
    }
}

package model;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "tickets")
public class Ticket extends Entity {
    private Performance performance;
    private String ownerName;
    private Integer noOfSeats;

    @Override
    public String toString() {
        return "\n\n\nTicket{ " +
                "id=" + getID() +
                ", performanceID=" + performance.getID() +
                ", ownerName='" + ownerName + '\'' +
                ", noOfSeats=" + noOfSeats +
                " }\n\n\n";
    }

    public Ticket() {}

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

    @Id
    @Column(name = "ticket_id")
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Override
    public Long getID() {
        return super.getID();
    }

    @ManyToOne
    @JoinColumn(name = "performance_id")
    public Performance getPerformance() {
        return performance;
    }

    public void setPerformance(Performance performance) {
        this.performance = performance;
    }

    @Column(name = "owner")
    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @Column(name = "seats")
    public Integer getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(Integer noOfSeats) {
        this.noOfSeats = noOfSeats;
    }
}

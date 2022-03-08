package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TicketTest {
    private Ticket ticket;

    @BeforeEach
    void setUp() {
        ticket = new Ticket(new Performance(1L, LocalDate.now(), "Cluj-Napoca", 206, 1678, new Artist("Kygo")), "owner", 3);
    }

    @Test
    void getPerformance() {
        assertEquals(ticket.getPerformance().getID(), 1L);
    }

    @Test
    void setPerformance() {
        ticket.setPerformance(new Performance(2L, LocalDate.now(), "Cluj-Napoca", 206, 1678, new Artist("Kygo")));
        assertEquals(ticket.getPerformance().getID(), 2L);
    }

    @Test
    void getOwnerName() {
        assertEquals(ticket.getOwnerName(), "owner");
    }

    @Test
    void setOwnerName() {
        ticket.setOwnerName("name");
        assertEquals(ticket.getOwnerName(), "name");
    }

    @Test
    void getNoOfSeats() {
        assertEquals(ticket.getNoOfSeats(), 3);
    }

    @Test
    void setNoOfSeats() {
        ticket.setNoOfSeats(2);
        assertEquals(ticket.getNoOfSeats(), 2);
    }
}
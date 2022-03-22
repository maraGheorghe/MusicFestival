package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PerformanceTest {
    Performance performance;
    Performance performance1;

    @BeforeEach
    void setUp() {
        performance = new Performance(LocalDateTime.of(2022, 8, 26, 12, 0), "Cluj_Napoca", 206, 1678, "Kygo");
        performance = new Performance(1L, LocalDateTime.of(2022, 8, 26, 12, 0), "Cluj-Napoca", 206, 1678, "Kygo");
    }

    @Test
    void getDate() {
        assertEquals(performance.getDate(), LocalDateTime.of(2022, 8, 26, 12, 0));
    }

    @Test
    void setDate() {
        performance.setDate(LocalDateTime.of(2022, 8, 26, 12, 0, 0));
        assertEquals(performance.getDate(), LocalDateTime.of(2022, 8, 26, 12, 0, 0));
    }

    @Test
    void getPlace() {
        assertEquals(performance.getPlace(), "Cluj-Napoca");
    }

    @Test
    void setPlace() {
        performance.setPlace("Bontida");
        assertEquals(performance.getPlace(), "Bontida");
    }

    @Test
    void getNoOfAvailableTickets() {
        assertEquals(performance.getNoOfAvailableSeats(), 206);
    }

    @Test
    void setNoOfAvailableTickets() {
        performance.setNoOfAvailableSeats(205);
        assertEquals(performance.getNoOfAvailableSeats(), 205);
    }

    @Test
    void getNoOfSoldTickets() {
        assertEquals(performance.getNoOfSoldSeats(), 1678);
    }

    @Test
    void setNoOfSoldTickets() {
        performance.setNoOfSoldSeats(1679);
        assertEquals(performance.getNoOfSoldSeats(), 1679);
    }

    @Test
    void getArtists() {
        assertEquals(performance.getArtist(), "Kygo");
    }

    @Test
    void setArtists() {
        performance.setArtists("21P");
        assertEquals(performance.getArtist(), "21P");
    }
}
package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PerformanceTest {
    Performance performance;
    Performance performance1;

    @BeforeEach
    void setUp() {
        performance = new Performance(LocalDate.now(), "Cluj_Napoca", 206, 1678, "Kygo");
        performance = new Performance(1L, LocalDate.now(), "Cluj-Napoca", 206, 1678, "Kygo");
    }

    @Test
    void getDate() {
        assertEquals(performance.getDate(), LocalDate.now());
    }

    @Test
    void setDate() {
        performance.setDate(LocalDate.of(2022, 8, 26));
        assertEquals(performance.getDate(), LocalDate.of(2022, 8, 26));
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
        assertEquals(performance.getNoOfAvailableTickets(), 206);
    }

    @Test
    void setNoOfAvailableTickets() {
        performance.setNoOfAvailableTickets(205);
        assertEquals(performance.getNoOfAvailableTickets(), 205);
    }

    @Test
    void getNoOfSoldTickets() {
        assertEquals(performance.getNoOfSoldTickets(), 1678);
    }

    @Test
    void setNoOfSoldTickets() {
        performance.setNoOfSoldTickets(1679);
        assertEquals(performance.getNoOfSoldTickets(), 1679);
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
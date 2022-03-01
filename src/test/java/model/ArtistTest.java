package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArtistTest {
    private Artist artist;
    private Artist artist1;

    @BeforeEach
    void setUp() {
        artist = new Artist(2L, "Kygo");
        artist1 = new Artist("Kygo");
    }

    @Test
    void getName() {
        assertEquals(artist.getName(), "Kygo");
        assertEquals(artist1.getName(), "Kygo");
    }

    @Test
    void setName() {
        artist.setName("Twenty One Pilots");
        artist1.setName("Twenty One Pilots");
        assertEquals(artist.getName(), "Twenty One Pilots");
        assertEquals(artist1.getName(), "Twenty One Pilots");
    }
}
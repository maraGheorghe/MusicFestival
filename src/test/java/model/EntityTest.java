package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntityTest {
    private Entity entity;

    @BeforeEach
    void setUp() {
        entity = new Entity();
        entity.setID(6L);
    }

    @Test
    void getID() {
        assertEquals(entity.getID(), 6L);
    }

    @Test
    void setID() {
        entity.setID(2L);
        assertEquals(entity.getID(), 2L);
    }

    @Test
    void testEquals() {
        Entity entity1 = new Entity();
        entity1.setID(6L);
        assertEquals(entity, entity1);
    }

    @Test
    void testHashCode() {
        Entity entity1 = new Entity();
        entity1.setID(6L);
        assertEquals(entity.hashCode(), entity1.hashCode());
    }
}
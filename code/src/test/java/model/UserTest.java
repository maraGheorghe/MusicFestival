package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {
    User user;
    User user1;

    @BeforeEach
    void setUp() {
        user = new User(2L, "oficiul01", "parola");
        user1 = new User("oficiul01", "parola");
    }

    @Test
    void getUsername() {
        assertEquals(user.getUsername(), "oficiul01");
        assertEquals(user1.getUsername(), "oficiul01");
    }

    @Test
    void setUsername() {
        user.setUsername("oficiul02");
        user1.setUsername("oficiul03");
        assertEquals(user.getUsername(), "oficiul02");
        assertEquals(user1.getUsername(), "oficiul03");
    }

    @Test
    void getPassword() {
        assertEquals(user.getPassword(), "parola");
        assertEquals(user1.getPassword(), "parola");
    }

    @Test
    void setPassword() {
        user.setPassword("parola00");
        user1.setPassword("pArOlA");
        assertEquals(user.getPassword(), "parola00");
        assertEquals(user1.getPassword(), "pArOlA");
    }
}
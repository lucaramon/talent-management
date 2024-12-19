package com.example.talent_api.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ManagerTests {

    @Test
    void testConstructor() {
        User user = new User();
        Manager manager = new Manager(user, "Jane Doe", "jdoe@example.com", "HR", "555-1234");

        assertEquals(user, manager.getUser());
        assertEquals("Jane Doe", manager.getFullName());
        assertEquals("jdoe@example.com", manager.getEmail());
        assertEquals("HR", manager.getDepartment());
        assertEquals("555-1234", manager.getPhone());
    }

    @Test
    public void testGettersAndSetters() {
        Manager manager = new Manager();
        User user = new User();
        
        manager.setUser(user);
        manager.setFullName("Jane Doe");
        manager.setEmail("jdoe@example.com");
        manager.setDepartment("HR");
        manager.setPhone("555-1234");

        assertEquals(user, manager.getUser());
        assertEquals("Jane Doe", manager.getFullName());
        assertEquals("jdoe@example.com", manager.getEmail());
        assertEquals("HR", manager.getDepartment());
        assertEquals("555-1234", manager.getPhone());
    }
    
}

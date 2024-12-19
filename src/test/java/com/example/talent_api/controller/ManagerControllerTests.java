package com.example.talent_api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.example.talent_api.domain.Manager;
import com.example.talent_api.domain.User;
import com.example.talent_api.domain.UserType;
import com.example.talent_api.service.ManagerServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ManagerControllerTests {

    @InjectMocks
    private ManagerController managerController;

    @Mock
    private ManagerServiceImpl managerService;

    private Manager manager;
    private User user;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
        user = new User("jdoe", "pwd", UserType.MANAGER);
        manager = new Manager(user, "Jane Doe", "jdoe@example.com", "Human Resources", "555-2222");
    }

    @Test
    void testGetAllManagers() {
        when(managerService.getAllManagers()).thenReturn(Collections.singletonList(manager));
        var managers = managerController.getAllManagers();
        assertNotNull(managers);
        assertEquals(1, managers.size());
        assertEquals("Jane Doe", managers.get(0).getFullName());
        verify(managerService).getAllManagers();
    }

    @Test
    void testGetManagerById() {
        when(managerService.getManagerById(1L)).thenReturn(ResponseEntity.ok(manager));
        ResponseEntity<Manager> response = managerController.getManagerById(1L);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Jane Doe", Objects.requireNonNull(response.getBody()).getFullName());
        verify(managerService).getManagerById(1L);
    }

    @Test
    void testGetManagerById_NotFound() {
        when(managerService.getManagerById(999L)).thenReturn(ResponseEntity.notFound().build());
        ResponseEntity<Manager> response = managerController.getManagerById(999L);
        assertEquals(404, response.getStatusCode().value());
        verify(managerService).getManagerById(999L);
    }

    @Test
    void testAddManager() throws Exception {
        String jsonInput = "{ \"user\": { \"id\": 1 }, \"manager\": { \"fullName\": \"Jane Doe\", \"email\": \"jdoe@example.com\" } }";
        JsonNode requestBody = objectMapper.readTree(jsonInput);

        when(managerService.addManager(requestBody)).thenReturn(ResponseEntity.status(201).body(manager));
        ResponseEntity<Manager> response = managerController.addManager(requestBody);
        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Jane Doe", Objects.requireNonNull(response.getBody()).getFullName());
        verify(managerService).addManager(requestBody);
    }

    @Test
    void testUpdateManager() {
        Manager updatedManager = new Manager();
        updatedManager.setFullName("Jane Smith"); 

        when(managerService.updateManager(any(), eq(1L))).thenReturn(ResponseEntity.ok(updatedManager));
        ResponseEntity<Manager> response = managerController.updateManager(updatedManager, 1L);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(Objects.requireNonNull(response.getBody()));
        assertEquals("Jane Smith", Objects.requireNonNull(response.getBody()).getFullName());
        verify(managerService).updateManager(any(), eq(1L));
    }

    @Test
    void testUpdateManager_NotFound() {
        Manager updatedManager = new Manager();
        updatedManager.setFullName("Jane Smith");

        when(managerService.updateManager(any(), eq(999L)))
            .thenReturn(ResponseEntity.notFound().build()); 
        ResponseEntity<Manager> response = managerController.updateManager(updatedManager, 999L);
        assertEquals(404, response.getStatusCode().value()); 
        verify(managerService).updateManager(any(), eq(999L)); 
    }

    @Test
    void testDeleteManager() {
        when(managerService.deleteManager(1L)).thenReturn(ResponseEntity.noContent().build());
        ResponseEntity<?> response = managerController.deleteManager(1L);
        assertEquals(204, response.getStatusCode().value());
        verify(managerService).deleteManager(1L);
    }

    @Test
    void testDeleteManager_NotFound() {
        when(managerService.deleteManager(999L)).thenReturn(ResponseEntity.notFound().build());
        ResponseEntity<?> response = managerController.deleteManager(999L);
        assertEquals(404, response.getStatusCode().value());
        verify(managerService).deleteManager(999L);
    }
    
}

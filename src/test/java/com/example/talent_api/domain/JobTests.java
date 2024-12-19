package com.example.talent_api.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.example.talent_api.utils.DateFunctions.getDate;
import static com.example.talent_api.utils.DateFunctions.getDateString;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JobTests {

    private Job job;
    private Manager manager;

    @BeforeEach
    public void setUp() {
        User user = new User();
        manager = new Manager(user, "Jane Doe", "jdoe@example.com", "Engineering", "555-1234");
        job = new Job(manager,
                "Software Engineer",
                getDate("2024-01-01 09:00:00"),
                getDate("2024-02-01 12:00:00"),
                "Full Stack Developer",
                "Responsible for developing full-stack applications.",
                "Must have experience with Java and Angular.",
                "Closed"
        );
    }

    @Test
    void testJobCreation(){
        assertNotNull(job);
        assertEquals(manager, job.getManager());
        assertEquals("Engineering", job.getManager().getDepartment());
        assertEquals("Software Engineer", job.getListingTitle());
        assertEquals("2024-01-01 09:00:00", getDateString(job.getDateListed()));
        assertEquals("2024-02-01 12:00:00", getDateString(job.getDateClosed()));
        assertEquals("Full Stack Developer", job.getJobTitle());
        assertEquals("Responsible for developing full-stack applications.", job.getJobDescription());
        assertEquals("Must have experience with Java and Angular.", job.getAdditionalInformation());
        assertEquals("Closed", job.getListingStatus());
    }

    @Test
    void testSetters(){
        User secondUser = new User();
        Manager secondManager = new Manager(secondUser, "John Smith", "jsmith@example.com", "Product Development", "555-4321");

        job.setManager(secondManager);
        assertEquals(secondManager, job.getManager());
        assertEquals("Product Development", job.getManager().getDepartment());
        job.setListingTitle("listingTitle");
        assertEquals("listingTitle", job.getListingTitle());
        job.setJobTitle("job title");
        assertEquals("job title", job.getJobTitle());
        job.setJobDescription("description");
        assertEquals("description", job.getJobDescription());
        job.setAdditionalInformation("additionalInformation");
        assertEquals("additionalInformation", job.getAdditionalInformation());
        job.setListingStatus("listingStatus");
        assertEquals("listingStatus", job.getListingStatus());
        job.setDateListed(getDate("2025-01-01 09:00:00"));
        assertEquals("2025-01-01 09:00:00", getDateString(job.getDateListed()));
        job.setDateClosed(getDate("2025-01-04 09:00:00"));
        assertEquals("2025-01-04 09:00:00", getDateString(job.getDateClosed()));
    }
}

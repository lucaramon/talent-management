package com.example.talent_api.controller;

import com.example.talent_api.domain.Job;
import com.example.talent_api.domain.Manager;
import com.example.talent_api.domain.User;
import com.example.talent_api.domain.UserType;
import com.example.talent_api.repository.JobRepository;
import com.example.talent_api.repository.ManagerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.*;

import static com.example.talent_api.utils.DateFunctions.getDate;

@WebMvcTest(JobController.class)
public class JobControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobRepository jobRepository;

    @MockBean
    private ManagerRepository managerRepository;

     Job job1;
     Job job2;

     Manager manager1;
     Manager manager2;

    @BeforeEach
    void setUp(){
        User user1 = new User("JohnDoe", "password", UserType.MANAGER);
        manager1 = new Manager();
        manager1.setId(1L);
        manager1.setUser(user1);
        manager1.setFullName("John Doe");
        manager1.setEmail("john@example.com");
        manager1.setDepartment("Engineering");
        manager1.setPhone("5555-1234");
        job1 = new Job(manager1, "Software Engineer", getDate("2024-01-01 09:00:00"), getDate("2024-02-01 12:00:00"), "Full Stack Developer", "Responsible for developing full-stack applications.", "Must have experience with Java and Angular.", "Closed");

        User user2 = new User("HelenDoe", "password", UserType.MANAGER);
        manager2 = new Manager();
        manager2.setId(2L);
        manager2.setUser(user2);
        manager2.setFullName("Helen Doe");
        manager2.setEmail("helen@example.com");
        manager2.setDepartment("Human Resources");
        manager2.setPhone("5555-2345");
        job2 = new Job(manager2, "HR Manager", getDate("2024-03-01 09:00:00"), getDate("2024-07-01 09:00:00"), "HR Manager", "Oversee HR functions and staff management.", "Strong leadership skills required.", "Closed");
    }

    @Test
    void shouldGetAllJobs() throws Exception {
        when(jobRepository.findAll()).thenReturn(
                new ArrayList<>(Arrays.asList(job1, job2))
        );

        this.mockMvc.perform(get("/jobs")).andExpect(status().isOk())
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$[0].manager.id").value(1))
                .andExpect(jsonPath("$[0].manager.fullName").value("John Doe"))
                .andExpect(jsonPath("$[0].manager.department").value("Engineering"))
                .andExpect(jsonPath("$[0].listingTitle").value("Software Engineer"))
                .andExpect(jsonPath("$[0].dateListed").value("2024-01-01T09:00:00.000+00:00"))
                .andExpect(jsonPath("$[1].manager.id").value(2))
                .andExpect(jsonPath("$[1].manager.fullName").value("Helen Doe"))
                .andExpect(jsonPath("$[1].manager.department").value("Human Resources"))
                .andExpect(jsonPath("$[1].listingTitle").value("HR Manager"))
                .andExpect(jsonPath("$[1].dateListed").value("2024-03-01T09:00:00.000+00:00"));
    }

    @Test
    void shouldGetJobById() throws Exception {
        when(jobRepository.findById(isA(Long.class))).thenReturn(Optional.of(job1));

        this.mockMvc.perform(get("/jobs/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.manager.id").value(1))
                .andExpect(jsonPath("$.manager.fullName").value("John Doe"))
                .andExpect(jsonPath("$.manager.department").value("Engineering"))
                .andExpect(jsonPath("$.listingTitle").value("Software Engineer"))
                .andExpect(jsonPath("$.dateListed").value("2024-01-01T09:00:00.000+00:00"))
                .andExpect(jsonPath("$.dateClosed").value("2024-02-01T12:00:00.000+00:00"))
                .andExpect(jsonPath("$.jobTitle").value("Full Stack Developer"))
                .andExpect(jsonPath("$.jobDescription").value("Responsible for developing full-stack applications."))
                .andExpect(jsonPath("$.additionalInformation").value("Must have experience with Java and Angular."))
                .andExpect(jsonPath("$.listingStatus").value("Closed"));
    }

    // get jobs by manager id
    @Test
    void shouldReturnJobsByManagerId() throws Exception {
        // Given
        when(managerRepository.findById(1L)).thenReturn(Optional.of(manager1));

        // Return an ArrayList with job1
        ArrayList<Job> jobList = new ArrayList<>();
        jobList.add(job1);

        when(jobRepository.findByManagerId(1L)).thenReturn(jobList);

        // When & Then
        this.mockMvc.perform(get("/jobs?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].manager.id").value(manager1.getId()))
                .andExpect(jsonPath("$[0].manager.fullName").value("John Doe"))
                .andExpect(jsonPath("$[0].manager.department").value("Engineering"))
                .andExpect(jsonPath("$[0].listingTitle").value("Software Engineer"))
                .andExpect(jsonPath("$[0].dateListed").value("2024-01-01T09:00:00.000+00:00"))
                .andExpect(jsonPath("$[0].dateClosed").value("2024-02-01T12:00:00.000+00:00"))
                .andExpect(jsonPath("$[0].jobTitle").value("Full Stack Developer"))
                .andExpect(jsonPath("$[0].jobDescription").value("Responsible for developing full-stack applications."))
                .andExpect(jsonPath("$[0].additionalInformation").value("Must have experience with Java and Angular."))
                .andExpect(jsonPath("$[0].listingStatus").value("Closed"));
    }

    // post job
    @Test
    void shouldReturnCreatedJob() throws Exception {
        when(managerRepository.findById(1L)).thenReturn(Optional.of(manager1));
        when(jobRepository.save(isA(Job.class))).thenReturn(job1);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/jobs")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"manager\":{\"id\":1}, " +
                                        "\"job\":{\"listingTitle\":\"" + job1.getListingTitle() + "\", " +
                                        "\"dateListed\":\"" + job1.getDateListed() + "\", " +
                                        "\"dateClosed\":\"" + job1.getDateClosed() + "\", " +
                                        "\"jobTitle\":\"" + job1.getJobTitle() + "\", " +
                                        "\"jobDescription\":\"" + job1.getJobDescription() + "\", " +
                                        "\"additionalInformation\":\"" + job1.getAdditionalInformation() + "\", " +
                                        "\"listingStatus\":\"" + job1.getListingStatus() + "\"}}"
                                )).andExpect(status().isCreated())
                .andExpect(jsonPath("$.manager.id").value(manager1.getId()))
                .andExpect(jsonPath("$.listingTitle").value(job1.getListingTitle()))
                .andExpect(jsonPath("$.dateListed").value("2024-01-01T09:00:00.000+00:00"))
                .andExpect(jsonPath("$.dateClosed").value("2024-02-01T12:00:00.000+00:00"))
                .andExpect(jsonPath("$.jobTitle").value(job1.getJobTitle()))
                .andExpect(jsonPath("$.jobDescription").value(job1.getJobDescription()))
                .andExpect(jsonPath("$.additionalInformation").value(job1.getAdditionalInformation()))
                .andExpect(jsonPath("$.listingStatus").value(job1.getListingStatus()));
    }

    // put job
    @Test
    void shouldReturnJobOnPut() throws Exception {
        when(managerRepository.findById(1L)).thenReturn(Optional.of(manager1));
        when(jobRepository.findById(1L)).thenReturn(Optional.of(job1));

        Job updatedJob = new Job(manager1, "Senior Software Engineer", getDate("2024-01-01 09:00:00"), getDate("2024-02-01 12:00:00"), "Full Stack Developer", "Responsible for developing full-stack applications.", "Must have experience with Java and Angular.", "Closed");
        when(jobRepository.save(any(Job.class))).thenReturn(updatedJob);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/jobs/1") // Assuming the ID is part of the URL
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"manager\":{\"id\":1}, " +
                                        "\"job\":{\"listingTitle\":\"Senior Software Engineer\", " + // Updated title
                                        "\"dateListed\":\"2024-01-01T09:00:00.000+00:00\", " +
                                        "\"dateClosed\":\"2024-02-01T12:00:00.000+00:00\", " + // Use existing dates
                                        "\"jobTitle\":\"Full Stack Developer\", " + // Keep as in existingJob
                                        "\"jobDescription\":\"Responsible for developing full-stack applications.\", " + // Keep as in existingJob
                                        "\"additionalInformation\":\"Must have experience with Java and Angular.\", " + // Keep as in existingJob
                                        "\"listingStatus\":\"Closed\"}}"
                                )).andExpect(status().isOk())
                .andExpect(jsonPath("$.manager.id").value(1L))
                .andExpect(jsonPath("$.manager.id").value(manager1.getId()))
                .andExpect(jsonPath("$.listingTitle").value(updatedJob.getListingTitle()))
                .andExpect(jsonPath("$.dateListed").value("2024-01-01T09:00:00.000+00:00"))
                .andExpect(jsonPath("$.dateClosed").value("2024-02-01T12:00:00.000+00:00"))
                .andExpect(jsonPath("$.jobTitle").value(updatedJob.getJobTitle()))
                .andExpect(jsonPath("$.jobDescription").value(updatedJob.getJobDescription()))
                .andExpect(jsonPath("$.additionalInformation").value(updatedJob.getAdditionalInformation()))
                .andExpect(jsonPath("$.listingStatus").value(updatedJob.getListingStatus()));
    }

    @Test
    void shouldReturnNotFoundOnPutWhenJobDoesNotExist() throws Exception {
        when(managerRepository.findById(1L)).thenReturn(Optional.of(manager1));
        when(jobRepository.findById(1L)).thenReturn(Optional.empty());

        this.mockMvc.perform(
                MockMvcRequestBuilders.put("/jobs/1") // Assuming the ID is part of the URL
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"manager\":{\"id\":1}, " +
                                "\"job\":{\"listingTitle\":\"Senior Software Engineer\", " + // Updated title
                                "\"dateListed\":\"2024-01-01T09:00:00.000+00:00\", " +
                                "\"dateClosed\":\"2024-02-01T12:00:00.000+00:00\", " + // Use existing dates
                                "\"jobTitle\":\"Full Stack Developer\", " + // Keep as in existingJob
                                "\"jobDescription\":\"Responsible for developing full-stack applications.\", " + // Keep as in existingJob
                                "\"additionalInformation\":\"Must have experience with Java and Angular.\", " + // Keep as in existingJob
                                "\"listingStatus\":\"Closed\"}}"
                        )).andExpect(status().isNotFound());
    }

    // delete job
    @Test
    void shouldReturnOkOnDelete() throws Exception {
        when(jobRepository.existsById(isA(Long.class))).thenReturn(true);

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/jobs/1"))
                .andExpect(status().isOk());
    }
}

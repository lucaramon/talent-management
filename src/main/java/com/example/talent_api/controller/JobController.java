package com.example.talent_api.controller;

import com.example.talent_api.domain.Job;
import com.example.talent_api.domain.Manager;
import com.example.talent_api.repository.JobRepository;
import com.example.talent_api.repository.ManagerRepository;
import com.example.talent_api.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static com.example.talent_api.utils.DateFunctions.formatDate;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobRepository jobRepository;
    private final ManagerRepository managerRepository;

    @Autowired
    public JobController(JobRepository jobRepository, ManagerRepository managerRepository) {
        this.jobRepository = jobRepository;
        this.managerRepository = managerRepository;
    }

    public static Date formatDate(String nodeDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        Date dateListed = formatter.parse(nodeDate);
        return dateListed;
    }

    // Get all jobs or jobs by manager ID
    // url is either /jobs or /jobs?id=1
    @GetMapping
    public ArrayList<Job> getJobs(@RequestParam(required = false) Long id) {
        if (id != null) {
            return jobRepository.findByManagerId(id);
        } else {
            return jobRepository.findAll();
        }
    }

    // get job by id
    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable long id) {
        Optional<Job> job = jobRepository.findById(id);
        return job.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // create new job
    @PostMapping
    public ResponseEntity<?> createJob(@RequestBody JsonNode node) {
        JsonNode managerNode = node.get("manager");
        if (managerNode == null || !managerNode.has("id")) {
            return ResponseEntity.badRequest().body("Manager ID is missing.");
        }

        Long managerId = managerNode.get("id").asLong();
        Optional<Manager> optionalManager = managerRepository.findById(managerId);
        if (!optionalManager.isPresent()) {
            return ResponseEntity.badRequest().body("Manager with id " + managerId + " not found.");
        }
        Manager manager = optionalManager.get();

        JsonNode jobNode = node.get("job");

        if(jobNode == null || !jobNode.has("listingTitle") || !jobNode.has("dateListed") || !jobNode.has("dateClosed") || !jobNode.has("jobTitle") || !jobNode.has("jobDescription") || !jobNode.has("additionalInformation") || !jobNode.has("listingStatus")) {
            return ResponseEntity.badRequest().body("Fields are missing from job!");
        }

        Job newJob = new Job();
        newJob.setManager(manager);

        newJob.setListingTitle(jobNode.get("listingTitle").asText());
        newJob.setJobDescription(jobNode.get("jobDescription").asText());
        newJob.setJobTitle(jobNode.get("jobTitle").asText());
        newJob.setAdditionalInformation(jobNode.get("additionalInformation").asText());
        newJob.setListingStatus(jobNode.get("listingStatus").asText());

        try{
            newJob.setDateListed(formatDate(jobNode.get("dateListed").asText()));
        }catch (ParseException e){
            System.out.println("Error parsing date" + jobNode.get("dateListed").asText());
        }

        try{
            newJob.setDateClosed(formatDate(jobNode.get("dateClosed").asText()));
        }catch (ParseException e){
            System.out.println("Error parsing date" + jobNode.get("dateListed").asText());
        }

        Job savedJob = jobRepository.save(newJob);
        return ResponseEntity.status(201).body(savedJob);
    };

    // update job
    @PutMapping("/{id}")
    public ResponseEntity<?> updateJob(@PathVariable long id, @RequestBody JsonNode node) {
        JsonNode managerNode = node.get("manager");
        if (managerNode == null || !managerNode.has("id")) {
            return ResponseEntity.badRequest().body("Manager ID is missing.");
        }

        Long managerId = managerNode.get("id").asLong();
        Optional<Manager> optionalManager = managerRepository.findById(managerId);
        if (!optionalManager.isPresent()) {
            return ResponseEntity.badRequest().body("Manager with id " + managerId + " not found.");
        }
        Manager manager = optionalManager.get();
        JsonNode jobNode = node.get("job");
        if(jobNode == null || !jobNode.has("listingTitle") || !jobNode.has("dateListed") || !jobNode.has("dateClosed") || !jobNode.has("jobTitle") || !jobNode.has("jobDescription") || !jobNode.has("additionalInformation") || !jobNode.has("listingStatus")) {
            return ResponseEntity.badRequest().body("Fields are missing from job!");
        }

        Optional<Job> jobOptional = jobRepository.findById(id);
        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();
            job.setManager(manager);
            job.setListingTitle(jobNode.get("listingTitle").asText());
            job.setListingStatus(jobNode.get("listingStatus").asText());
            job.setAdditionalInformation(jobNode.get("additionalInformation").asText());
            job.setJobTitle(jobNode.get("jobTitle").asText());
            job.setJobDescription(jobNode.get("jobDescription").asText());

            try{
                job.setDateListed(formatDate(jobNode.get("dateListed").asText()));
            }catch (ParseException e){
                System.out.println("Error parsing date" + jobNode.get("dateListed").asText());
            }

            try{
                job.setDateClosed(formatDate(jobNode.get("dateClosed").asText()));
            }catch (ParseException e){
                System.out.println("Error parsing date" + jobNode.get("dateListed").asText());
            }

            jobRepository.save(job);
            return ResponseEntity.ok(job);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // delete job
    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteJobById(@PathVariable long id) {
        if (jobRepository.existsById(id)) {
            jobRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

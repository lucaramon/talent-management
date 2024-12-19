package com.example.talent_api.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    @JsonBackReference
    private Candidate candidate;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "date_applied", nullable = false)
    private Timestamp dateApplied;

    @Column(name = "cover_letter")
    private String coverLetter;

    @Column(name = "custom_resume")
    private String customResume;

    @Column(name = "application_status")
    private String applicationStatus; // Status of the application

    // Constructeur par défaut
    public Application() {
    }

    // Constructeur avec tous les paramètres
    public Application(Long id, Job job, Long userId, Timestamp dateApplied,
                       String coverLetter, String customResume, String applicationStatus) {
        this.id = id;
        this.job = job;
        this.userId = userId;
        this.dateApplied = dateApplied;
        this.coverLetter = coverLetter;
        this.customResume = customResume;
        this.applicationStatus = applicationStatus;
    }

    // Getters et Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Long getUserId() { // Getter for userId
        return userId;
    }

    public void setUserId(Long userId) { // Setter for userId
        this.userId = userId;
    }

    public Timestamp getDateApplied() { // Getter for dateApplied
        return dateApplied;
    }

    public void setDateApplied(Timestamp dateApplied) { // Setter for dateApplied
        this.dateApplied = dateApplied;
    }

    public String getCoverLetter() { // Getter for coverLetter
        return coverLetter;
    }

    public void setCoverLetter(String coverLetter) { // Setter for coverLetter
        this.coverLetter = coverLetter;
    }

    public String getCustomResume() { // Getter for customResume
        return customResume;
    }

    public void setCustomResume(String customResume) { // Setter for customResume
        this.customResume = customResume;
    }

    public String getApplicationStatus() { // Getter for applicationStatus
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) { // Setter for applicationStatus
        this.applicationStatus = applicationStatus;
    }
}
package com.example.talent_api.domain;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name="job")
public class Job {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name="manager_id")
    private Manager manager;

    @Column(name="listing_title")
    private String listingTitle;

    @Column(name="date_listed")
    private Date dateListed;

    @Column(name="date_closed")
    private Date dateClosed;

    @Column(name="job_title")
    private String jobTitle;

    @Column(name="job_description")
    private String jobDescription;

    @Column(name="additional_information")
    private String additionalInformation;

    @Column(name="listing_status")
    private String listingStatus;


    public Job(){}

    public Job( Manager manager, String listingTitle, Date dateListed, Date dateClosed, String jobTitle, String jobDescription, String additionalInformation, String listingStatus) {
        this.manager = manager;
        this.listingTitle = listingTitle;
        this.dateListed = dateListed;
        this.dateClosed = dateClosed;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.additionalInformation = additionalInformation;
        this.listingStatus = listingStatus;
    }

    public Manager getManager() {
        return this.manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Date getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(Date dateClosed) {
        this.dateClosed = dateClosed;
    }

    public Date getDateListed() {
        return dateListed;
    }

    public void setDateListed(Date dateListed) {
        this.dateListed = dateListed;
    }

    public String getListingTitle() {
        return listingTitle;
    }

    public void setListingTitle(String listingTitle) {
        this.listingTitle = listingTitle;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getListingStatus() {
        return listingStatus;
    }

    public void setListingStatus(String listingStatus) {
        this.listingStatus = listingStatus;
    }
}

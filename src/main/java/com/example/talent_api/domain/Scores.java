package com.example.talent_api.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "scores")
public class Scores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="application_id", referencedColumnName = "id")
    private Application application;

    @Column(name="requirement_fit_score")
    private Integer requirementFitScore;

    @Column(name="qualifications_score")
    private Integer qualificationsScore;

    @Column(name="soft_skills_score")
    private Integer softSkillsScore;

    @Column(name="clarity_score")
    private Integer clarityScore;

    public Scores() {}

    public Scores(Application application) {
        this.application = application;
    }

    public Long getId() { return id; }

    public Application getApplication() { return application; }

    public Integer getRequirementFitScore() { return requirementFitScore; }

    public Integer getQualificationsScore() { return qualificationsScore; }

    public Integer getSoftSkillsScore() { return softSkillsScore; }

    public Integer getClarityScore() { return clarityScore; }

    public void setRequirementFitScore(Integer requirementFitScore) {
        this.requirementFitScore = requirementFitScore;
    }

    public void setQualificationsScore(Integer qualificationsScore) {
        this.qualificationsScore = qualificationsScore;
    }

    public void setSoftSkillsScore(Integer softSkillsScore) {
        this.softSkillsScore = softSkillsScore;
    }

    public void setClarityScore(Integer clarityScore) {
        this.clarityScore = clarityScore;
    }
}

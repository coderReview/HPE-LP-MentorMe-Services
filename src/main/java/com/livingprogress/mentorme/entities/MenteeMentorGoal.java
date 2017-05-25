package com.livingprogress.mentorme.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * The mentee mentor goal.
 */
@Getter
@Setter
@Entity
public class MenteeMentorGoal extends IdentifiableEntity {
    /**
     * The goal.
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "goal_id")
    private Goal goal;

    /**
     * The completed flag.
     */
    private boolean completed;

    /**
     * The completed date.
     */
    @Temporal(TIMESTAMP)
    private Date completedOn;

    /**
     * The goal's tasks.
     */
    @OneToMany(mappedBy = "menteeMentorGoalId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenteeMentorTask> tasks;

    /**
     * The mentee-mentor program id.
     */
    @Column(name = "mentee_mentor_program_id", insertable = false, updatable = false)
    private long menteeMentorProgramId;

    /**
     * The mentee mentor program.
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "mentee_mentor_program_id")
    private MenteeMentorProgram menteeMentorProgram;

    /**
     * The useful links.
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "mentee_mentor_goal_useful_link", joinColumns = {
        @JoinColumn(name = "mentee_mentor_goal_id")}, inverseJoinColumns = {@JoinColumn(name = "useful_link_id")})
    private List<UsefulLink> usefulLinks;

    /**
     * The documents
     */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Transient
    private List<Document> documents;

    public void setDocuments(List<Document> documents) {
        // nothing
    }

    public List<Document> getDocuments() {
        return this.goal == null ? new ArrayList<Document>() : this.goal.getDocuments();
    }

}

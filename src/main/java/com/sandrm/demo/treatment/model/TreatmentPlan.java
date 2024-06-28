package com.sandrm.demo.treatment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Entity
@Table(name="treatment_plan")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreatmentPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    //TODO use enum
    private String action;
    //TODO use ref
    private String subjectPatient;

    private Timestamp startTime;

    private Timestamp endTime;
    //TODO clarify pattern
    private String recurrencePattern;
}

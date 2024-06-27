package com.sandrm.demo.treatment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name="treatment_task")
@Data
@AllArgsConstructor
public class TreatmentTask {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //TODO use enum
    private String action;

    //TODO use ref
    private String subjectPatient;

    private Timestamp startTime;

    private boolean isActive;
}

package com.sandrm.demo.treatment.service;

import com.sandrm.demo.treatment.model.TreatmentPlan;
import com.sandrm.demo.treatment.model.TreatmentTask;
import com.sandrm.demo.treatment.repository.TreatmentPlanRepository;
import com.sandrm.demo.treatment.repository.TreatmentTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


@RequiredArgsConstructor
@Service
public class TaskGeneratorImpl implements TaskGenerator {
    @Autowired
    TreatmentPlanRepository planRepository;
    @Autowired
    TreatmentTaskRepository taskRepository;


    /**
     * Suggestion - we generate Tasks for one next day - the day when tasks will be running
     * After could be used some period, like several days
     */
    @Override
    public void execute() {
        LocalDate todayDate = LocalDate.now();
        LocalDate treatmentDay = todayDate.plusDays(1);

        Date treatmentDate = Date.from(treatmentDay.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        List<TreatmentPlan> result = planRepository.getPlanForDate(treatmentDate);

        //Create Tasks
        //TODO apply Parse Reccurence using Parser
        for (TreatmentPlan plan : result) {
            TreatmentTask treatmentTask = new TreatmentTask();

            treatmentTask.setAction(plan.getAction());
            //TODO get Date time from Recurrence pattern field
            treatmentTask.setStartTime(Timestamp.valueOf(LocalDateTime.now()));
            treatmentTask.setSubjectPatient(plan.getSubjectPatient());
            treatmentTask.setActive(true);

            taskRepository.save(treatmentTask);
        }

    }
}

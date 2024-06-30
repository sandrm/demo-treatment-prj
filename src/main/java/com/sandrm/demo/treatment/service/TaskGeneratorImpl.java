package com.sandrm.demo.treatment.service;

import com.sandrm.demo.treatment.model.TreatmentPlan;
import com.sandrm.demo.treatment.model.TreatmentTask;
import com.sandrm.demo.treatment.repository.TreatmentPlanRepository;
import com.sandrm.demo.treatment.repository.TreatmentTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static com.sandrm.demo.treatment.service.RecurrencePatternParser.*;


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
    public void execute(Date treatmentDate) {
        //TODO delete all tasks for treatmentDate

        List<TreatmentPlan> plans = planRepository.getPlanForDate(treatmentDate);

        String dayName = getDayName(treatmentDate);
        String firstPart = MAP_DAYS_OF_WEEK.get(dayName);

        //Generate Tasks
        for (TreatmentPlan plan : plans) {
            String recurrencePattern = plan.getRecurrencePattern();
            if (recurrencePattern.contains(firstPart)) {
                generateTask(treatmentDate, plan, recurrencePattern, firstPart);
            }
            if (recurrencePattern.contains(EVERY_DAY_AT)) {
                generateTask(treatmentDate, plan, recurrencePattern, EVERY_DAY_AT);
            }
        }
    }

    private void generateTask(Date treatmentDate, TreatmentPlan plan,
                              String recurrencePattern, String firstPartOfPattern) {

        Date taskStartTime = setTimeTimeForTreatmentDate(
                recurrencePattern,
                firstPartOfPattern,
                treatmentDate);

        TreatmentTask treatmentTask = new TreatmentTask();

        treatmentTask.setAction(plan.getAction());
        treatmentTask.setStartTime(new Timestamp(taskStartTime.getTime()));
        treatmentTask.setSubjectPatient(plan.getSubjectPatient());
        treatmentTask.setActive(true);

        taskRepository.save(treatmentTask);
    }
}

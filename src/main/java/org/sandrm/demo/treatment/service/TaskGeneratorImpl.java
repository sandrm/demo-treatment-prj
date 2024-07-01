package org.sandrm.demo.treatment.service;

import org.sandrm.demo.treatment.model.TreatmentPlan;
import org.sandrm.demo.treatment.model.TreatmentTask;
import org.sandrm.demo.treatment.repository.TreatmentPlanRepository;
import org.sandrm.demo.treatment.repository.TreatmentTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
    public void execute(Date treatmentDate) {
        SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dmy = dmyFormat.format(treatmentDate);
        taskRepository.deleteForDate(dmy);

        List<TreatmentPlan> plans = planRepository.getPlanForDate(treatmentDate);

        String dayName = RecurrencePatternParser.getDayName(treatmentDate);
        String firstPart = RecurrencePatternParser.MAP_DAYS_OF_WEEK.get(dayName);

        //Generate Tasks
        for (TreatmentPlan plan : plans) {
            String recurrencePattern = plan.getRecurrencePattern();
            if (recurrencePattern.contains(firstPart)) {
                generateTask(treatmentDate, plan, recurrencePattern, firstPart);
            }
            if (recurrencePattern.contains(RecurrencePatternParser.EVERY_DAY_AT)) {
                generateTask(treatmentDate, plan, recurrencePattern, RecurrencePatternParser.EVERY_DAY_AT);
            }
        }
    }

    private void generateTask(Date treatmentDate, TreatmentPlan plan,
                              String recurrencePattern, String firstPartOfPattern) {

        Date taskStartTime = RecurrencePatternParser.setTimeTimeForTreatmentDate(
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

package org.sandrm.demo.treatment.service;

import lombok.RequiredArgsConstructor;
import org.sandrm.demo.treatment.model.TreatmentPlan;
import org.sandrm.demo.treatment.model.TreatmentTask;
import org.sandrm.demo.treatment.repository.TreatmentPlanRepository;
import org.sandrm.demo.treatment.repository.TreatmentTaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;

import static org.sandrm.demo.treatment.service.RecurrencePatternParser.*;


@RequiredArgsConstructor
@Service
public class TaskGeneratorImpl implements TaskGenerator {
    private static final Logger logger = LoggerFactory.getLogger(TaskGeneratorImpl.class);

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
        logger.info("Processing Plans for date: " + treatmentDate);

        SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dmy = dmyFormat.format(treatmentDate);
        taskRepository.deleteForDate(dmy);

        List<TreatmentPlan> plans = planRepository.getPlanForDate(treatmentDate);

        //Generate Tasks
        for (TreatmentPlan plan : plans) {
            String recurrencePattern = plan.getRecurrencePattern();

            Matcher matcherTwo = PATTERN_TYPE_TWO_COMPILED.matcher(recurrencePattern);
            if (matcherTwo.find()) {
                generateTask(plan, treatmentDate, EVERY_DAY_AT);

                generateTask(plan, treatmentDate, PATTERN_TYPE_TWO_BEFORE_TIME2);
                continue;
            }


            Matcher matcher = PATTERN_DAY_OF_WEEK_COMPILED.matcher(recurrencePattern);
            if (matcher.find()) {
                String dayName = getDayName(treatmentDate);
                String firstPartOfPattern = getFirstPartOfPatternType2(dayName);
                if (recurrencePattern.contains(firstPartOfPattern)) {
                    generateTask(plan, treatmentDate, firstPartOfPattern);
                }
            }
        }
    }

    private void generateTask(TreatmentPlan plan, Date treatmentDate, String firstPartPattern) {
        String recurrencePattern = plan.getRecurrencePattern();
        String time_HH_MM2 = recurrencePattern.replaceFirst(firstPartPattern, "");
        String[] time = convertHHMM(time_HH_MM2);
        Date taskStartTime = RecurrencePatternParser.setTimeTimeForTreatmentDate(treatmentDate, time);

        TreatmentTask treatmentTask = new TreatmentTask();

        treatmentTask.setAction(plan.getAction());
        treatmentTask.setStartTime(new Timestamp(taskStartTime.getTime()));
        treatmentTask.setSubjectPatient(plan.getSubjectPatient());
        treatmentTask.setActive(true);

        taskRepository.save(treatmentTask);

        logger.info("For Treatment Plan ID = " + plan.getId() + " created Task with ID = " + treatmentTask.getId());
    }
}

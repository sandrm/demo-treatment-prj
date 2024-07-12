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
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@RequiredArgsConstructor
@Service
public class TaskGeneratorImpl implements TaskGenerator {
    private static final Logger logger = LoggerFactory.getLogger(TaskGeneratorImpl.class);

    @Autowired
    TreatmentPlanRepository planRepository;
    @Autowired
    TreatmentTaskRepository taskRepository;
    @Autowired
    RecurrencePatternTypeOne typeOneStrategy;
    @Autowired
    RecurrencePatternTypeTwo typeTwoStrategy;

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

            var dateTask = typeOneStrategy.calculateTaskDate(treatmentDate, recurrencePattern);
            if (dateTask.isPresent()) {
                createTask(plan, dateTask.get());
            }

            var datesTask = typeTwoStrategy.calculateTaskDate(treatmentDate, recurrencePattern);
            if (datesTask.isPresent()) {
                Arrays.stream(datesTask.get())
                        .forEach(date -> createTask(plan, date));
            }
        }
    }


    public void createTask(TreatmentPlan plan, Date taskStartTime) {
        TreatmentTask treatmentTask = new TreatmentTask();

        treatmentTask.setAction(plan.getAction());
        treatmentTask.setStartTime(new Timestamp(taskStartTime.getTime()));
        treatmentTask.setSubjectPatient(plan.getSubjectPatient());
        treatmentTask.setActive(true);

        taskRepository.save(treatmentTask);

        logger.info("For Treatment Plan ID = " + plan.getId() + " created Task with ID = " + treatmentTask.getId());
    }
}

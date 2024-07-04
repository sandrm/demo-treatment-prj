package org.sandrm.demo.treatment.service;

import lombok.RequiredArgsConstructor;
import org.sandrm.demo.treatment.model.TreatmentPlan;
import org.sandrm.demo.treatment.model.TreatmentTask;
import org.sandrm.demo.treatment.repository.TreatmentPlanRepository;
import org.sandrm.demo.treatment.repository.TreatmentTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.sandrm.demo.treatment.service.RecurrencePatternParser.*;


@RequiredArgsConstructor
@Service
public class TaskGeneratorImpl implements TaskGenerator {
    private static final Logger logger = LoggerFactory.getLogger(TaskGeneratorImpl.class);

    public static final String AND = " and ";
    static String regEx_HH_MM = "([0-1]?[0-9]|2[0-3]):[0-5][0-9]";  //08:00
    static String patternType1 = "^every day at " + regEx_HH_MM + "$";  //every day at 08:00
    static Pattern patternTypeOne = Pattern.compile(patternType1);
    static String patternType2 = "^every day at " + regEx_HH_MM + AND + regEx_HH_MM + "$";  //every day at 08:00 and 16:00
    static Pattern patternTypeTwo = Pattern.compile(patternType2);
    static String patternType2_BeforeTime2 = "every day at " + regEx_HH_MM  + AND;  //"every day at 08:00 and "

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

            if (recurrencePattern.contains(EVERY_DAY_AT)) {
                Matcher matcher = patternTypeOne.matcher(recurrencePattern);
                if (matcher.find()) {
                    generateTask(plan, treatmentDate, EVERY_DAY_AT);
                    continue;
                }

                Matcher matcherTwo = patternTypeTwo.matcher(recurrencePattern);
                if (matcherTwo.find()) {
                    generateTask(plan, treatmentDate, EVERY_DAY_AT);

                    generateTask(plan, treatmentDate, patternType2_BeforeTime2);

                    continue;
                }
            }


            String dayName = RecurrencePatternParser.getDayName(treatmentDate);
            String firstPartOfPattern = RecurrencePatternParser.MAP_DAYS_OF_WEEK.get(dayName);

            if (recurrencePattern.contains(firstPartOfPattern)) {
                String patternDayOfWeek = "^every " + dayName + " at " + regEx_HH_MM + "$";
                Pattern pattern = Pattern.compile(patternDayOfWeek);
                Matcher matcher = pattern.matcher(recurrencePattern);
                if (matcher.find()) {
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

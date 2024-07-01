package com.sandrm.demo.treatment;

import com.sandrm.demo.treatment.service.TaskGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@SpringBootApplication
public class DemoTreatmentApp implements ApplicationRunner {

    public static final int DAYS_TO_ADD = 1;

    public static void main(String[] args) {

        SpringApplication.run(DemoTreatmentApp.class, args);
    }

    @Autowired
    TaskGenerator taskGenerator;

    @Override
    public void run(ApplicationArguments args) {
        /*
         * TODO It can be run by scheduler or in other way instead ApplicationRunner
         */
        LocalDate todayDate = LocalDate.now();
        LocalDate treatmentLocalDate = todayDate.plusDays(DAYS_TO_ADD);
        Date treatmentDate1 = Date.from(treatmentLocalDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

        taskGenerator.execute(treatmentDate1);
    }
}

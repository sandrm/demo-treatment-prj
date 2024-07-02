package org.sandrm.demo.treatment;

import org.sandrm.demo.treatment.service.TaskGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class DemoTreatmentApp implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(DemoTreatmentApp.class);
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

        logger.info("DemoTreatmentApp is running!" );
    }
}

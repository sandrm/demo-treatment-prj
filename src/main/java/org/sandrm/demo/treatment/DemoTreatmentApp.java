package org.sandrm.demo.treatment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Override
    public void run(ApplicationArguments args) {
        /*
         * Processing Recurrence Plans can be run by ApplicationRunner or by scheduler or API
         */

        logger.info("DemoTreatmentApp is running!" );
    }
}

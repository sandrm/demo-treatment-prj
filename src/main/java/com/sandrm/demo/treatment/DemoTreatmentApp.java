package com.sandrm.demo.treatment;

import com.sandrm.demo.treatment.service.TaskGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoTreatmentApp implements ApplicationRunner {

    public static void main(String[] args) {

        SpringApplication.run(DemoTreatmentApp.class, args);
    }

    @Autowired
    TaskGenerator taskGenerator;

    @Override
    public void run(ApplicationArguments args) {
        taskGenerator.execute();
    }
}

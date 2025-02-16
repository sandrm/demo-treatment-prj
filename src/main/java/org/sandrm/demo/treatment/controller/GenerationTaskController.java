package org.sandrm.demo.treatment.controller;

import org.sandrm.demo.treatment.service.TaskGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * http://localhost:8080/api/generateTasks?plusDays=2
 */
@RestController
@RequestMapping("/api")
public class GenerationTaskController {
    private static final Logger logger = LoggerFactory.getLogger(GenerationTaskController.class);

    @Autowired
    TaskGenerator taskGenerator;

    @GetMapping("/generateTasks")
    public ResponseEntity<HttpStatus> generateTasks(@RequestParam(required = true, defaultValue = "1") Integer plusDays) {
        LocalDate todayDate = LocalDate.now();
        LocalDate treatmentLocalDate = todayDate.plusDays(plusDays);
        Date treatmentDate = Date.from(treatmentLocalDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

        logger.info("Processing Plans for date: " + treatmentDate);

        taskGenerator.execute(treatmentDate);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

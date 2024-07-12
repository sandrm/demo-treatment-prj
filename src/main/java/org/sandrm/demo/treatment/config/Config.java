package org.sandrm.demo.treatment.config;

import org.sandrm.demo.treatment.service.RecurrencePatternTypeOne;
import org.sandrm.demo.treatment.service.RecurrencePatternTypeTwo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean(name = "typeOneStrategy")
    public RecurrencePatternTypeOne createOneStrategy() {
        return new RecurrencePatternTypeOne();
    }

    @Bean(name = "typeTwoStrategy")
    public RecurrencePatternTypeTwo createTwoStrategy() {
        return new RecurrencePatternTypeTwo();
    }

}
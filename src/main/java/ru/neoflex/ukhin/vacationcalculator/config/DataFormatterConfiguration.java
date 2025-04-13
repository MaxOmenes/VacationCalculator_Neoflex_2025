package ru.neoflex.ukhin.vacationcalculator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
public class DataFormatterConfiguration {

    @Bean
    public DateTimeFormatter dateTimeFormatter() {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

}

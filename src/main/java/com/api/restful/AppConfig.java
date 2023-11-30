package com.api.restful;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
@ComponentScan("com.api.restful")
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(context -> LocalDate.parse(context.getSource(), DateTimeFormatter.ISO_LOCAL_DATE), String.class, LocalDate.class);
        return modelMapper;
    }


}

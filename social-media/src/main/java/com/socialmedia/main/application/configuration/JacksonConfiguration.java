package com.socialmedia.main.application.configuration;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.web.config.SpringDataJacksonConfiguration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.Serial;

@Configuration
public class JacksonConfiguration {

    @Bean
    public ObjectMapper configureMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new SpringDataJacksonConfiguration.PageModule());
        return objectMapper;
    }


}

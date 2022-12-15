package com.github.ducknowledges.quiz.config;

import com.github.ducknowledges.quiz.service.IoService;
import com.github.ducknowledges.quiz.service.IoServiceStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan("com.github.ducknowledges.quiz")
public class AppConfig {
    @Bean
    public IoService ioService() {
        return new IoServiceStream(System.in, System.out, System.err);
    }
}

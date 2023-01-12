package com.github.ducknowledges.quiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuizApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuizApplication.class, args);
        //ApplicationContext context = SpringApplication.run(QuizApplication.class, args);
        //QuizManagerService quizManagerService = context.getBean(QuizManagerServiceImpl.class);
        //quizManagerService.run();
    }
}

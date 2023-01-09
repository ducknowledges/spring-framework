package com.github.ducknowledges.quiz;

import com.github.ducknowledges.quiz.service.QuizManagerService;
import com.github.ducknowledges.quiz.service.QuizManagerServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class QuizApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(QuizApplication.class, args);
        QuizManagerService quizManagerService = context.getBean(QuizManagerServiceImpl.class);
        quizManagerService.run();
    }
}

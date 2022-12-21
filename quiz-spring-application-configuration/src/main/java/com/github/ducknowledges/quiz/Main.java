package com.github.ducknowledges.quiz;

import com.github.ducknowledges.quiz.config.AppConfig;
import com.github.ducknowledges.quiz.service.QuizManagerService;
import com.github.ducknowledges.quiz.service.QuizManagerServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        QuizManagerService quizManagerService = context.getBean(QuizManagerServiceImpl.class);
        quizManagerService.run();
    }
}

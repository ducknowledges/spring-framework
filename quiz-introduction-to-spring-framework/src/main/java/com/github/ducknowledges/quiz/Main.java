package com.github.ducknowledges.quiz;

import com.github.ducknowledges.quiz.service.QuizManagerService;
import com.github.ducknowledges.quiz.service.QuizManagerServiceImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "/spring-context.xml");
        QuizManagerService service = context.getBean(QuizManagerServiceImpl.class);
        service.run();
        context.close();
    }
}

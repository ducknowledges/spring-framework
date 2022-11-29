package com.github.ducknowledges.quiz;

import com.github.ducknowledges.quiz.service.QuizService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "/spring-context.xml"
        );
        QuizService service = context.getBean(QuizService.class);
        service.run();
        context.close();
    }
}

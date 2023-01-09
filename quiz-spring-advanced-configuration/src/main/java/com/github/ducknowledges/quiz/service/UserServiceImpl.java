package com.github.ducknowledges.quiz.service;

import com.github.ducknowledges.quiz.domain.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final MessageService messageService;
    private final CommunicationService communicationService;


    public UserServiceImpl(MessageService messageService,
                           CommunicationService communicationService) {
        this.messageService = messageService;
        this.communicationService = communicationService;
    }

    @Override
    public User createUser() {
        greetUser();
        String firstName = askFirstName();
        String lastName = askLastName();
        return new User(firstName, lastName);
    }

    private void greetUser() {
        String helloMessage = messageService.getMessage("hello.user");
        communicationService.reportToUser(helloMessage);
    }

    private String askFirstName() {
        String firstNameMessage = messageService.getMessage("firstname.user");
        return communicationService.askToUser(firstNameMessage);
    }

    private String askLastName() {
        String lastNameMessage = messageService.getMessage("lastname.user");
        return communicationService.askToUser(lastNameMessage);
    }

}

package com.github.ducknowledges.quiz.service;

import com.github.ducknowledges.quiz.domain.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final CommunicationService communicationService;

    public UserServiceImpl(CommunicationService communicationService) {
        this.communicationService = communicationService;
    }

    @Override
    public User createUser() {
        communicationService.reportToUser("Please, introduce yourself");
        String firstName = communicationService.askToUser("Enter firstName:");
        String lastName = communicationService.askToUser("Enter lastName:");
        return new User(firstName, lastName);
    }

}

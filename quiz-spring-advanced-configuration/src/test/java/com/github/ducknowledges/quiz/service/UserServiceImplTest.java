package com.github.ducknowledges.quiz.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.ducknowledges.quiz.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class UserServiceImpl")
class UserServiceImplTest {

    private MessageService messageService;
    private CommunicationService communicationService;
    private UserService userService;

    @BeforeEach
    void setUp() {
        messageService = mock(MessageServiceImpl.class);
        communicationService = mock(CommunicationServiceImpl.class);
        userService = new UserServiceImpl(messageService, communicationService);
    }

    @Test
    @DisplayName("should return user")
    void shouldCreateUser() {
        String name = "userName";
        when(communicationService.askToUser(any())).thenReturn(name);

        User user = new User(name, name);
        assertThat(userService.createUser()).isEqualTo(user);
    }

    @Test
    @DisplayName("should use communication service correctly")
    void shouldInvokeCommunicationService() {
        when(messageService.getMessage("user.hello")).thenReturn("Please, introduce yourself");
        when(messageService.getMessage("user.firstname")).thenReturn("Enter firstName:");
        when(messageService.getMessage("user.lastname")).thenReturn("Enter lastName:");

        userService.createUser();
        verify(communicationService, times(1)).reportToUser("Please, introduce yourself");
        verify(communicationService, times(1)).askToUser("Enter firstName:");
        verify(communicationService, times(1)).askToUser("Enter lastName:");
    }
}
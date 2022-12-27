package com.github.ducknowledges.quiz.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.ducknowledges.quiz.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class UserServiceImpl")
class UserServiceImplTest {

    @Test
    @DisplayName("should return user")
    void shouldCreateUser() {
        String name = "userName";
        CommunicationService communicationService = mock(CommunicationServiceImpl.class);
        when(communicationService.askToUser(anyString())).thenReturn(name);
        UserService userService = new UserServiceImpl(communicationService);
        User user = new User(name, name);
        assertThat(userService.createUser()).isEqualTo(user);
    }

    @Test
    @DisplayName("should use communication service")
    void shouldInvokeCommunicationService() {
        String name = "userName";
        CommunicationService communicationService = mock(CommunicationServiceImpl.class);
        when(communicationService.askToUser(anyString())).thenReturn(name);
        new UserServiceImpl(communicationService).createUser();
        verify(communicationService, times(1)).reportToUser("Please, introduce yourself");
        verify(communicationService, times(1)).askToUser("Enter firstName:");
        verify(communicationService, times(1)).askToUser("Enter lastName:");
    }
}
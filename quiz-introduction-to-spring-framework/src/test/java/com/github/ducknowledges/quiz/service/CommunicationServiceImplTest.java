package com.github.ducknowledges.quiz.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class CommunicationServiceImpl")
class CommunicationServiceImplTest {

    private IoService ioService;
    private CommunicationService communicationService;

    @BeforeEach
    void setUp() {
        ioService = mock(IoServiceStream.class);
        communicationService = new CommunicationServiceImpl(ioService);
    }

    @Test
    @DisplayName("should report message to user")
    void shouldReportToUser() {
        String message = "message";
        communicationService.reportToUser(message);
        verify(ioService, times(1)).write(message);
    }

    @Test
    @DisplayName("should report error message to user")
    void shouldReportErrorToUser() {
        String message = "message";
        communicationService.reportErrorToUser(message);
        verify(ioService, times(1)).writeError(message);
    }
}
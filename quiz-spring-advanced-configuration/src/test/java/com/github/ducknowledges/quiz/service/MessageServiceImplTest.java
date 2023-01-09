package com.github.ducknowledges.quiz.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.ducknowledges.quiz.config.AppProps;
import java.util.Locale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;

@DisplayName("Class MessageServiceImpl")
class MessageServiceImplTest {

    private MessageService messageService;
    private MessageSource messageSource;
    private AppProps props;

    @BeforeEach
    void setUp() {
        props = mock(AppProps.class);
        when(props.getLocale()).thenReturn(Locale.ENGLISH);
        messageSource = mock(MessageSource.class);
        messageService = new MessageServiceImpl(messageSource, props);
    }

    @Test
    @DisplayName("should get message by code")
    void shouldReturnMessageByCode() {
        String messageCode = "code";
        String expected = "message";
        when(messageSource.getMessage(messageCode, new String[]{}, Locale.ENGLISH))
            .thenReturn(expected);
        assertThat(messageService.getMessage(messageCode)).isEqualTo(expected);
        verify(props, times(1)).getLocale();
    }

    @Test
    @DisplayName("should return message with arguments by code")
    void shouldReturnMessageByCodeWithArguments() {
        String messageCode = "code";
        String expected = "message";
        String[] args = {"arg1", "arg2"};
        when(messageSource.getMessage(messageCode, args, Locale.ENGLISH)).thenReturn(expected);
        assertThat(messageService.getMessage(messageCode, args)).isEqualTo(expected);
        verify(props, times(1)).getLocale();
    }
}
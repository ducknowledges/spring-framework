package com.github.ducknowledges.quiz.service;

import com.github.ducknowledges.quiz.config.AppProps;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageServiceImpl implements MessageService {

    private final MessageSource messageSource;
    private final AppProps props;

    public MessageServiceImpl(MessageSource messageSource, AppProps props) {
        this.messageSource = messageSource;
        this.props = props;
    }

    @Override
    public String getMessage(String messageCode) {
        return messageSource.getMessage(messageCode, new String[]{}, props.getLocale());
    }

    @Override
    public String getMessage(String messageCode, String[] args) {
        return messageSource.getMessage(messageCode, args, props.getLocale());
    }
}

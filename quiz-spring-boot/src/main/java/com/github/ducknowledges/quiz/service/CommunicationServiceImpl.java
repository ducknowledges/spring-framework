package com.github.ducknowledges.quiz.service;

import org.springframework.stereotype.Service;

@Service
public class CommunicationServiceImpl implements CommunicationService {

    private final IoService ioService;

    public CommunicationServiceImpl(IoService ioService) {
        this.ioService = ioService;
    }

    @Override
    public String askToUser(String message) {
        ioService.write(message);
        return ioService.read();
    }

    @Override
    public void reportToUser(String message) {
        ioService.write(message);
    }

    @Override
    public void reportErrorToUser(String errorMessage) {
        ioService.writeError(errorMessage);
    }
}

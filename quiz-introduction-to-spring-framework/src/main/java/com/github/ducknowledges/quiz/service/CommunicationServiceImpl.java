package com.github.ducknowledges.quiz.service;

public class CommunicationServiceImpl implements CommunicationService {

    private final IoService ioService;

    public CommunicationServiceImpl(IoService ioService) {
        this.ioService = ioService;
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

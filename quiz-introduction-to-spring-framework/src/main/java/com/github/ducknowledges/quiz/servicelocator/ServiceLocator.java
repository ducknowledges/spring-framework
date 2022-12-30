package com.github.ducknowledges.quiz.servicelocator;

import com.github.ducknowledges.quiz.service.IoService;
import com.github.ducknowledges.quiz.service.IoServiceStream;

public class ServiceLocator {
    private static final IoService ioServiceStream = new IoServiceStream(
        System.out,
        System.err
    );

    public IoService createIoServiceInstance() {
        return ioServiceStream;
    }
}

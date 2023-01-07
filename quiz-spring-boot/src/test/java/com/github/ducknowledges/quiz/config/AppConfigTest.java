package com.github.ducknowledges.quiz.config;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ducknowledges.quiz.service.IoService;
import com.github.ducknowledges.quiz.service.IoServiceStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class AppConfig")
class AppConfigTest {
    @Test
    @DisplayName("should get instance of IoService")
    void shouldReturnInstanceOfIoService() {
        AppConfig appConfig = new AppConfig();
        IoService ioService = appConfig.ioService();
        assertThat(ioService).isInstanceOf(IoServiceStream.class);
    }
}
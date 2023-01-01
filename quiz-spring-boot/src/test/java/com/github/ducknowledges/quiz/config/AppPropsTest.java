package com.github.ducknowledges.quiz.config;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class AppProps")
class AppPropsTest {

    private AppProps props;

    @BeforeEach
    void setUp() {
        props = new AppProps();
    }

    @Test
    @DisplayName("should return ru quiz resource path")
    void shouldGetRuQuizPath() {
        props.setLocale(new Locale("ru"));
        props.setRuQuizPath("path");
        assertThat(props.getResourcePath()).isEqualTo("path");
    }

    @Test
    @DisplayName("should return en quiz resource path")
    void shouldGetEnQuizPath() {
        props.setLocale(new Locale("en"));
        props.setEnQuizPath("path");
        assertThat(props.getResourcePath()).isEqualTo("path");
    }

    @Test
    @DisplayName("should return props success score")
    void shouldGetSuccessScore() {
        props.setSuccessScore(1);
        assertThat(props.getSuccessScore()).isEqualTo(1);
    }
}
package com.github.ducknowledges.quiz.config;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class AppProps")
class AppPropsTest {

    private AppProps props;

    @BeforeEach
    void setUp() {
        Map<String, String> path = new HashMap<>();
        path.put("en", "quiz_en.csv");
        path.put("ru_ru", "quiz_ru.csv");
        props = new AppProps();
        props.setPath(path);
    }

    @Test
    @DisplayName("should return ru quiz resource path")
    void shouldGetRuQuizPath() {
        props.setLocale(new Locale("ru_RU"));
        assertThat(props.getPath()).isEqualTo("quiz_ru.csv");
    }

    @Test
    @DisplayName("should return en quiz resource path")
    void shouldGetEnQuizPath() {
        props.setLocale(new Locale("en"));
        assertThat(props.getPath()).isEqualTo("quiz_en.csv");
    }

    @Test
    @DisplayName("should return props success score")
    void shouldGetSuccessScore() {
        props.setSuccessScore(1);
        assertThat(props.getSuccessScore()).isEqualTo(1);
    }
}
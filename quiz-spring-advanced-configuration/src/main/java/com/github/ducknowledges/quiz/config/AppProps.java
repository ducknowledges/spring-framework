package com.github.ducknowledges.quiz.config;

import java.util.Locale;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "quiz")
public class AppProps {

    private Locale locale;
    private Map<String, String> path;
    private int successScore;


    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void setPath(Map<String, String> path) {
        this.path = path;
    }

    public String getPath() {
        return path.get(locale.getLanguage());
    }

    public int getSuccessScore() {
        return successScore;
    }

    public void setSuccessScore(int successScore) {
        this.successScore = successScore;
    }
}

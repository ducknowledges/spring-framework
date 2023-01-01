package com.github.ducknowledges.quiz.config;

import java.util.Locale;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "quiz")
public class AppProps {
    private String ruQuizPath;
    private String enQuizPath;
    private int successScore;
    private Locale locale;

    public String getResourcePath() {
        String language = locale.getLanguage();
        if (language.equals("ru")) {
            return ruQuizPath;
        }
        return enQuizPath;
    }

    public int getSuccessScore() {
        return successScore;
    }

    public void setSuccessScore(int successScore) {
        this.successScore = successScore;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void setRuQuizPath(String ruQuizPath) {
        this.ruQuizPath = ruQuizPath;
    }

    public void setEnQuizPath(String enQuizPath) {
        this.enQuizPath = enQuizPath;
    }
}

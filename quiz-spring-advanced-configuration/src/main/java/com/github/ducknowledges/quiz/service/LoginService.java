package com.github.ducknowledges.quiz.service;

import com.github.ducknowledges.quiz.domain.User;
import java.util.Optional;

public interface LoginService {

    User login();

    void logout();

    Optional<User> getLoggedUser();

    boolean isLogged();
}

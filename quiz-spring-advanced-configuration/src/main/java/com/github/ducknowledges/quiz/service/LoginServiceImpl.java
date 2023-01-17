package com.github.ducknowledges.quiz.service;

import com.github.ducknowledges.quiz.domain.User;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    private final UserService userService;
    private Optional<User> loggedUser;

    public LoginServiceImpl(UserService userService) {
        this.userService = userService;
        this.loggedUser = Optional.empty();
    }

    @Override
    public User login() {
        User user = userService.createUser();
        this.loggedUser = Optional.of(user);
        return user;
    }

    @Override
    public void logout() {
        this.loggedUser = Optional.empty();
    }

    @Override
    public Optional<User> getLoggedUser() {
        return this.loggedUser;
    }

    @Override
    public boolean isLogged() {
        return this.loggedUser.isPresent();
    }
}

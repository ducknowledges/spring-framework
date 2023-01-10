package com.github.ducknowledges.quiz.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.ducknowledges.quiz.domain.User;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class LoginService")
class LoginServiceImplTest {

    private LoginService loginService;

    @BeforeEach
    void setUp() {
        UserService userService = mock(UserServiceImpl.class);
        when(userService.createUser()).thenReturn(new User("firstName", "lastName"));
        loginService = new LoginServiceImpl(userService);
    }

    @Test
    @DisplayName("should login user")
    void shouldLoginUser() {
        User user = loginService.login();
        assertThat(user).isEqualTo(new User("firstName", "lastName"));
    }

    @Test
    @DisplayName("should logout user")
    void shouldLogoutUser() {
        loginService.login();
        loginService.logout();
        assertThat(loginService.getLoggedUser()).isEqualTo(Optional.empty());

    }

    @Test
    @DisplayName("should return logged user")
    void shouldGetLoggedUser() {
        loginService.login();
        Optional<User> expected = Optional.of(new User("firstName", "lastName"));
        assertThat(loginService.getLoggedUser()).isEqualTo(expected);
    }

    @Test
    @DisplayName("should return logged status")
    void shouldReturnIsLoggedUser() {
        assertThat(loginService.isLogged()).isFalse();
        loginService.login();
        assertThat(loginService.isLogged()).isTrue();
    }
}
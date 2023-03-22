package com.github.ducknowledges.bookstore.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class User")
class UserTest {

    @Test
    @DisplayName("should has correct setters")
    void shouldHasCorrectSetters() {
        User user = new User();
        user.setId(1L);
        user.setUsername("name");
        user.setPassword("password");
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        assertAll(
            () -> assertThat(user.getId()).isEqualTo(1L),
            () -> assertThat(user.getUsername()).isEqualTo("name"),
            () -> assertThat(user.getPassword()).isEqualTo("password"),
            () -> assertThat(user.isAccountNonExpired()).isTrue(),
            () -> assertThat(user.isAccountNonLocked()).isTrue(),
            () -> assertThat(user.isCredentialsNonExpired()).isTrue(),
            () -> assertThat(user.isEnabled()).isTrue()
        );
    }

}

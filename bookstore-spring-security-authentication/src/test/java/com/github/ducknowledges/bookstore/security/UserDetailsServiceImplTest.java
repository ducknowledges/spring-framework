package com.github.ducknowledges.bookstore.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.github.ducknowledges.bookstore.dao.UserDao;
import com.github.ducknowledges.bookstore.domain.User;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@SpringBootTest
@DisplayName("Class UserDetailsServiceImpl")
class UserDetailsServiceImplTest {

    @MockBean
    private UserDao userDao;

    @Autowired
    private UserDetailsService userDetailsService;

    @Test
    @DisplayName("should get user details")
    void shouldLoadUserDetailsByUsername() {
        when(userDao.findByUsername("user")).thenReturn(Optional.of(new User()));
        UserDetails expectedUser = new User();
        UserDetails actualUser = userDetailsService.loadUserByUsername("user");
        assertThat(actualUser).isEqualTo(expectedUser);
    }

    @Test
    @DisplayName("should throw UsernameNotFoundException")
    void shouldThrowException() {
        when(userDao.findByUsername("user")).thenReturn(Optional.empty());
        try {
            UserDetails actualUser = userDetailsService.loadUserByUsername("user");
        } catch (UsernameNotFoundException e) {
            assertThat(e).isInstanceOf(UsernameNotFoundException.class);
            assertThat(e.getMessage()).isEqualTo("User user not found");
        }
    }
}

package com.github.ducknowledges.bookstore.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ducknowledges.bookstore.domain.User;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@DisplayName("Class BookDaoJpa")
class UserDaoJpaTest {

    @Autowired
    private TestEntityManager manager;

    @Autowired
    private UserDao userDao;

    @DisplayName("should return user")
    @Test
    void shouldReturnCorrectUser() {
        User expectedUser = manager.getEntityManager()
            .createQuery(
                "select u from User u where u.username = :username",
                User.class)
            .setParameter("username", "user")
            .getSingleResult();

        Optional<User> optionalUser = userDao.findByUsername("user");
        assertThat(optionalUser).isEqualTo(Optional.of(expectedUser));
    }

    @DisplayName("should return empty user")
    @Test
    void shouldReturnEmptyUser() {
        Optional<User> optionalUser = userDao.findByUsername("non exist user");
        assertThat(optionalUser).isEmpty();
    }
}

package com.github.ducknowledges.bookstore.dao;

import com.github.ducknowledges.bookstore.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}

package org.crud.todo.repository.user;

import org.crud.todo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByUsername(String user_name);
    boolean existsByUsername(String user_name);
}

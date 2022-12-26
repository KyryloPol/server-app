package com.trainme.treainmeapp.dao;

import com.trainme.treainmeapp.domain.User;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// A repository that extends JpaRepository. It is a repository that is used to store and retrieve data from the database.
@Repository
@EnableJpaRepositories
@EntityScan
public interface UserRepository extends JpaRepository<User,Long>{
    /**
     * Find a user by username, and return an Optional that contains the user if found, or an empty Optional if not found.
     *
     * @param username The username of the user you want to find.
     * @return Optional value of type User
     */
    Optional<User> findUserByUsername(String username);

    /**
     * Find a user by id, and return it wrapped in an Optional.
     *
     * @param id The id of the user to find.
     * @return Optional value of type User
     */
    Optional<User> findUserById(Long id);
}

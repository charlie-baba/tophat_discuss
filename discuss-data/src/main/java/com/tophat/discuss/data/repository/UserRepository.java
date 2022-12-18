package com.tophat.discuss.data.repository;

import com.tophat.discuss.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Charles on 17/12/2022
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u")
    List<User> getAllUsers();

    User findByUsername(String username);

    User findByPhoneNumber(String phoneNumber);

    User findUserById(Long id);
}

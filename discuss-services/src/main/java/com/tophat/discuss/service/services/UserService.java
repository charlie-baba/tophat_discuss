package com.tophat.discuss.service.services;

import com.tophat.discuss.data.models.User;
import com.tophat.discuss.service.pojo.request.UserRequest;

import java.util.List;

/**
 * @author Charles on 17/12/2022
 */
public interface UserService {

    List<User> getAllUsers();

    User getUser(Long id);

    User createUser(UserRequest userRequest);

    void deleteUser(Long id);
}

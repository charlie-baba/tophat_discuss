package com.tophat.discuss.service.services.impl;

import com.tophat.discuss.data.models.User;
import com.tophat.discuss.data.repository.UserRepository;
import com.tophat.discuss.service.pojo.request.UserRequest;
import com.tophat.discuss.service.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Charles on 17/12/2022
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public User getUser(Long id) {
        User user = userRepository.findUserById(id);
        if (user == null) {
            throw new EntityNotFoundException("User with id: "+ id +" does not exist.");
        }
        return user;
    }

    @Override
    @Transactional
    public User createUser(UserRequest userRequest) {
        if (userRepository.findByUsername(userRequest.getUsername()) != null) {
            throw new EntityNotFoundException("A user with the email " + userRequest.getUsername() + " already exists");
        }
        if (userRepository.findByPhoneNumber(userRequest.getPhoneNumber()) != null) {
            throw new EntityNotFoundException("A user with the phone number " + userRequest.getPhoneNumber() + " already exists");
        }

        User user = new User();
        BeanUtils.copyProperties(userRequest, user);
        userRepository.save(user);
        return user;
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findUserById(id);
        if (user == null) {
            throw new EntityNotFoundException("User with id: "+ id +" does not exist.");
        }

        userRepository.delete(user);
    }
}

package com.tophat.discuss.service.services.impl;

import com.tophat.discuss.data.enums.UserType;
import com.tophat.discuss.data.models.User;
import com.tophat.discuss.data.repository.UserRepository;
import com.tophat.discuss.data.pojo.request.UserRequest;
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
        return userRepository.findAll();
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
    public User createUser(UserRequest request) {
        if (userRepository.findByUsername(request.getUsername()) != null) {
            throw new EntityNotFoundException("A user with the username " + request.getUsername() + " already exists");
        }
        if (request.getPhoneNumber() != null && userRepository.findByPhoneNumber(request.getPhoneNumber()) != null) {
            throw new EntityNotFoundException("A user with the phone number " + request.getPhoneNumber() + " already exists");
        }

        User user = new User();
        BeanUtils.copyProperties(request, user);
        user.setUserType(UserType.valueOf(request.getUserType()));
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

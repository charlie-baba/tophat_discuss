package com.tophat.discuss.service.services;

import com.tophat.discuss.data.enums.UserType;
import com.tophat.discuss.data.models.User;
import com.tophat.discuss.data.pojo.request.UserRequest;
import com.tophat.discuss.data.repository.UserRepository;
import com.tophat.discuss.service.services.impl.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Charles on 17/12/2022
 */
public class UserServiceTest extends SharedTestData {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl service;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllUsersShouldBeSuccessful() {
        //Arrange
        doReturn(List.of(getUser())).when(userRepository).findAll();

        //Act
        List<User> response = service.getAllUsers();

        //Assert
        Assertions.assertAll(
                () -> Assertions.assertNotNull(response),
                () -> Assertions.assertEquals(1, response.size())
        );
    }

    @Test
    public void getUserShouldBeSuccessful() {
        //Arrange
        User user = getUser();
        doReturn(user).when(userRepository).findUserById(userId);

        //Act
        User response = service.getUser(userId);

        //Assert
        Assertions.assertEquals(user, response);
    }

    @Test
    public void getUserWithInvalidIdShouldFail() {
        //Arrange
        doReturn(null).when(userRepository).findUserById(userId);

        //Act //Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.getUser(userId);
        }, "User with id: " + userId + " does not exist.");
    }

    @Test
    public void createUserShouldBeSuccessful() {
        //Arrange
        String phoneNumber = "6479991111";
        UserRequest request = new UserRequest();
        request.setFirstName("Frank");
        request.setLastName("Miles");
        request.setUsername(testUsername);
        request.setPhoneNumber(phoneNumber);
        request.setUserType(UserType.Professor.toString());
        doReturn(null).when(userRepository).findByUsername(testUsername);
        doReturn(null).when(userRepository).findByPhoneNumber(phoneNumber);

        //Act
        User response = service.createUser(request);

        //Assert
        verify(userRepository).save(any(User.class));
        Assertions.assertAll(
                () -> Assertions.assertNotNull(response),
                () -> Assertions.assertEquals(testUsername, response.getUsername()),
                () -> Assertions.assertEquals(phoneNumber, response.getPhoneNumber())
        );
    }

    @Test
    public void createUserWithExistingUsernameShouldFail() {
        //Arrange
        UserRequest request = new UserRequest();
        request.setUsername(testUsername);
        doReturn(getUser()).when(userRepository).findByUsername(testUsername);

        //Act //Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.createUser(request);
        }, "A user with the username " + testUsername + " already exists");
    }

    @Test
    public void createUserWithExistingPhoneNumberShouldFail() {
        //Arrange
        String phoneNumber = "6479991111";
        UserRequest request = new UserRequest();
        request.setUsername(testUsername);
        request.setPhoneNumber(phoneNumber);
        doReturn(null).when(userRepository).findByUsername(testUsername);
        doReturn(getUser()).when(userRepository).findByPhoneNumber(phoneNumber);

        //Act //Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.createUser(request);
        }, "A user with the phone number " + phoneNumber + " already exists");
    }

    @Test
    public void deleteUserShouldBeSuccessful() {
        //Arrange
        User user = getUser();
        doReturn(user).when(userRepository).findUserById(userId);

        //Act
        service.deleteUser(userId);

        //Assert
        verify(userRepository).delete(user);
    }

    @Test
    public void deleteUserWithInvalidIdShouldFail() {
        //Arrange
        doReturn(null).when(userRepository).findUserById(userId);

        //Act //Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.deleteUser(userId);
        }, "User with id: " + userId + " does not exist.");
        verify(userRepository, never()).delete(any(User.class));
    }

}

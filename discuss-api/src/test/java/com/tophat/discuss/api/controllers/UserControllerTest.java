package com.tophat.discuss.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tophat.discuss.data.enums.UserType;
import com.tophat.discuss.data.pojo.request.UserRequest;
import com.tophat.discuss.service.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Charles on 17/12/2022
 */
@RunWith(SpringRunner.class)
public class UserControllerTest extends SharedTestData {

    @Mock
    private UserService mockService;

    @InjectMocks
    private UserController userControllerTest;

    private MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userControllerTest).build();
    }

    @Test
    public void getUsersShouldBeSuccessful() throws Exception {
        //Arrange
        doReturn(List.of(getUser())).when(mockService).getAllUsers();

        //Act //Assert
        this.mockMvc.perform(get("/api/v1/user/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    public void getUserShouldBeSuccessful() throws Exception {
        //Arrange
        doReturn(getUser()).when(mockService).getUser(userId);

        //Act //Assert
        this.mockMvc.perform(get("/api/v1/user/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userId)))
                .andExpect(jsonPath("$.username", is(testUsername)));
    }

    @Test
    public void createUserShouldBeSuccessful() throws Exception {
        //Arrange
        UserRequest request = new UserRequest();
        request.setFirstName("Frank");
        request.setLastName("Miles");
        request.setUsername(testUsername);
        request.setPhoneNumber("6479991111");
        request.setUserType(UserType.Professor.toString());
        doReturn(getUser()).when(mockService).createUser(request);

        //Act //Assert
        this.mockMvc.perform(post("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUserShouldBeSuccessful() throws Exception {
        //Act //Assert
        this.mockMvc.perform(delete("/api/v1/user/{id}", userId))
                .andExpect(status().isOk());
    }

}

package com.tophat.discuss.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tophat.discuss.data.pojo.request.DiscussionRequest;
import com.tophat.discuss.data.pojo.response.DiscussionResponse;
import com.tophat.discuss.service.services.DiscussionService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Charles on 17/12/2022
 */
@RunWith(SpringRunner.class)
public class DiscussionControllerTest extends SharedTestData {

    @Mock
    private DiscussionService mockService;

    @InjectMocks
    private DiscussionController discussionControllerTest;

    private MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(discussionControllerTest).build();
    }

    @Test
    public void getDiscussionsShouldBeSuccessful() throws Exception {
        //Arrange
        doReturn(List.of(getDiscussionResponse())).when(mockService).getAllDiscussions();

        //Act //Assert
        this.mockMvc.perform(get("/api/v1/discussion/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    public void getDiscussionShouldBeSuccessful() throws Exception {
        //Arrange
        doReturn(getDiscussionResponse()).when(mockService).getDiscussion(discussionId);

        //Act //Assert
        this.mockMvc.perform(get("/api/v1/discussion/{id}", discussionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(discussionId)));
    }

    @Test
    public void createDiscussionShouldBeSuccessful() throws Exception {
        //Arrange
        DiscussionRequest request = new DiscussionRequest();
        request.setQuestion("How is your day going so far?");
        request.setAuthorId(userId);
        doReturn(getDiscussionResponse()).when(mockService).createDiscussion(request);

        //Act //Assert
        this.mockMvc.perform(post("/api/v1/discussion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void updateDiscussionShouldBeSuccessful() throws Exception {
        //Arrange
        DiscussionRequest request = new DiscussionRequest();
        request.setQuestion("How is your week going so far?");
        request.setAuthorId(userId);
        doReturn(getDiscussionResponse()).when(mockService).updateDiscussion(discussionId, request);

        //Act //Assert
        this.mockMvc.perform(put("/api/v1/discussion/{id}", discussionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteDiscussionShouldBeSuccessful() throws Exception {
        //Act //Assert
        this.mockMvc.perform(delete("/api/v1/discussion/{id}", discussionId))
                .andExpect(status().isOk());
    }

    private DiscussionResponse getDiscussionResponse() {
        return new DiscussionResponse(getDiscussion());
    }
}

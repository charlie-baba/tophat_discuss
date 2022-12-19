package com.tophat.discuss.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tophat.discuss.data.pojo.request.CommentRequest;
import com.tophat.discuss.data.pojo.response.CommentResponse;
import com.tophat.discuss.service.services.CommentService;
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

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Charles on 19/12/2022
 */
@RunWith(SpringRunner.class)
public class CommentsControllerTest extends SharedTestData {

    @Mock
    private CommentService mockService;

    @InjectMocks
    private CommentsController commentControllerTest;

    private MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(commentControllerTest).build();
    }

    @Test
    public void addCommentShouldBeSuccessful() throws Exception {
        //Arrange
        CommentRequest request = new CommentRequest();
        request.setComment("it is going great, Thanks!");
        request.setAuthorId(userId);
        request.setDiscussionId(discussionId);
        doReturn(new CommentResponse(getParentComment())).when(mockService).addComment(request);

        //Act //Assert
        this.mockMvc.perform(post("/api/v1/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCommentShouldBeSuccessful() throws Exception {
        //Act //Assert
        this.mockMvc.perform(delete("/api/v1/comment/{id}", parentCommentId))
                .andExpect(status().isOk());
    }

}

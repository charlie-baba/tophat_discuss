package com.tophat.discuss.service.services;

import com.tophat.discuss.data.models.Discussion;
import com.tophat.discuss.data.models.User;
import com.tophat.discuss.data.pojo.request.DiscussionRequest;
import com.tophat.discuss.data.pojo.response.DiscussionResponse;
import com.tophat.discuss.data.repository.DiscussionRepository;
import com.tophat.discuss.data.repository.UserRepository;
import com.tophat.discuss.service.services.impl.DiscussionServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Charles on 17/12/2022
 */
public class DiscussionServiceTest extends SharedTestData {

    @Mock
    private UserRepository userRepository;

    @Mock
    private DiscussionRepository discussionRepository;

    @InjectMocks
    private DiscussionServiceImpl service;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllDiscussionsShouldBeSuccessful() {
        //Arrange
        doReturn(listDiscussions()).when(discussionRepository).findDiscussionsForListing();

        //Act
        List<DiscussionResponse> response = service.getAllDiscussions();

        //Assert
        Assertions.assertAll(
                () -> Assertions.assertNotNull(response),
                () -> Assertions.assertEquals(2, response.size())
        );
    }

    @Test
    public void getDiscussionShouldBeSuccessful() {
        //Arrange
        Discussion discussion = getDiscussion();
        discussion.getComments().add(getParentComment());
        doReturn(discussion).when(discussionRepository).findDiscussionById(discussionId);

        //Act
        DiscussionResponse response = service.getDiscussion(discussionId);

        //Assert
        Assertions.assertAll(
                () -> Assertions.assertNotNull(response),
                () -> Assertions.assertEquals(1, response.getComments().size()),
                () -> Assertions.assertEquals(discussionId, response.getId()),
                () -> Assertions.assertEquals(discussion.getQuestion(), response.getQuestion())
        );
    }

    @Test
    public void getDiscussionWithInvalidIdShouldFail() {
        //Arrange
        doReturn(null).when(discussionRepository).findDiscussionById(discussionId);

        //Act //Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.getDiscussion(discussionId);
        }, "Discussion with id: " + discussionId + " does not exist.");
    }

    @Test
    public void createDiscussionShouldBeSuccessful() {
        //Arrange
        User author = getUser();
        String question = "What do we want to learn today?";
        DiscussionRequest request = new DiscussionRequest();
        request.setQuestion(question);
        request.setAuthorId(userId);
        doReturn(author).when(userRepository).findUserById(userId);

        //Act
        DiscussionResponse response = service.createDiscussion(request);

        //Assert
        verify(discussionRepository).save(any(Discussion.class));
        Assertions.assertAll(
                () -> Assertions.assertNotNull(response),
                () -> Assertions.assertEquals(question, response.getQuestion()),
                () -> Assertions.assertEquals(userId, response.getAuthorId()),
                () -> Assertions.assertEquals(0L, response.getCommentsCount())
        );
    }

    @Test
    public void createDiscussionWithInvalidUserIdShouldFail() {
        //Arrange
        DiscussionRequest request = new DiscussionRequest();
        request.setQuestion("What do we want to learn today?");
        request.setAuthorId(userId);
        doReturn(null).when(userRepository).findUserById(userId);

        //Act //Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.createDiscussion(request);
        }, "User with id: " + userId + " does not exist.");
    }

    @Test
    public void updateDiscussionShouldBeSuccessful() {
        //Arrange
        Discussion discussion = getDiscussion();
        String question = "How is your week going so far?";
        DiscussionRequest request = new DiscussionRequest();
        request.setQuestion(question);
        doReturn(discussion).when(discussionRepository).findDiscussionById(discussionId);

        //Act
        DiscussionResponse response = service.updateDiscussion(discussionId, request);

        //Assert
        verify(discussionRepository).save(any(Discussion.class));
        Assertions.assertAll(
                () -> Assertions.assertNotNull(response),
                () -> Assertions.assertEquals(discussionId, response.getId()),
                () -> Assertions.assertEquals(question, response.getQuestion())
        );
    }

    @Test
    public void updateDiscussionWithInvalidUserIdShouldFail() {
        //Arrange
        DiscussionRequest request = new DiscussionRequest();
        request.setQuestion("How is your week going so far?");
        doReturn(null).when(discussionRepository).findDiscussionById(discussionId);

        //Act //Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.updateDiscussion(discussionId, request);
        }, "Discussion with id: " + discussionId + " does not exist.");
    }

    @Test
    public void deleteDiscussionShouldBeSuccessful() {
        //Arrange
        Discussion discussion = getDiscussion();
        doReturn(discussion).when(discussionRepository).findDiscussionById(discussionId);

        //Act
        service.deleteDiscussion(discussionId);

        //Assert
        verify(discussionRepository).delete(discussion);
    }

    @Test
    public void deleteDiscussionWithInvalidIdShouldFail() {
        //Arrange
        doReturn(null).when(discussionRepository).findDiscussionById(discussionId);

        //Act //Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.deleteDiscussion(discussionId);
        }, "Discussion with id: " + discussionId + " does not exist.");
        verify(discussionRepository, never()).delete(any(Discussion.class));
    }

    private List<DiscussionResponse> listDiscussions() {
        List<DiscussionResponse> response = new ArrayList<>();
        response.add(new DiscussionResponse(getDiscussion()));
        response.add(new DiscussionResponse(1L, "What do we want to learn today?", userId, now, 3L));
        return response;
    }
}

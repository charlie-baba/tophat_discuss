package com.tophat.discuss.service.services;

import com.tophat.discuss.data.models.Comment;
import com.tophat.discuss.data.models.Discussion;
import com.tophat.discuss.data.models.User;
import com.tophat.discuss.data.pojo.request.CommentRequest;
import com.tophat.discuss.data.pojo.response.CommentResponse;
import com.tophat.discuss.data.repository.CommentRepository;
import com.tophat.discuss.data.repository.DiscussionRepository;
import com.tophat.discuss.data.repository.UserRepository;
import com.tophat.discuss.service.services.impl.CommentServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Charles on 17/12/2022
 */
public class CommentServiceTest extends SharedTestData {

    @Mock
    private UserRepository userRepository;

    @Mock
    private DiscussionRepository discussionRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentServiceImpl service;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addCommentWithoutParentCommentShouldBeSuccessful() {
        //Arrange
        User author = getUser();
        Discussion discussion = getDiscussion();
        String comment = "it is going great, Thanks!";
        CommentRequest request = new CommentRequest();
        request.setComment(comment);
        request.setAuthorId(userId);
        request.setDiscussionId(discussionId);
        doReturn(author).when(userRepository).findUserById(userId);
        doReturn(discussion).when(discussionRepository).findDiscussionById(discussionId);

        //Act
        CommentResponse response = service.addComment(request);

        //Assert
        verify(commentRepository).save(any(Comment.class));
        Assertions.assertAll(
                () -> Assertions.assertNotNull(response),
                () -> Assertions.assertEquals(comment, response.getComment()),
                () -> Assertions.assertEquals(userId, response.getAuthorId()),
                () -> Assertions.assertEquals(discussionId, response.getDiscussionId()),
                () -> Assertions.assertNull(response.getParentCommentId())
        );
    }

    @Test
    public void addCommentWithParentCommentShouldBeSuccessful() {
        //Arrange
        User author = getUser();
        Discussion discussion = getDiscussion();
        String comment = "Unfortunately, I had a bad day!";
        CommentRequest request = new CommentRequest();
        request.setComment(comment);
        request.setAuthorId(userId);
        request.setDiscussionId(discussionId);
        request.setParentCommentId(parentCommentId);
        doReturn(author).when(userRepository).findUserById(userId);
        doReturn(discussion).when(discussionRepository).findDiscussionById(discussionId);
        doReturn(getParentComment()).when(commentRepository).findCommentById(parentCommentId);

        //Act
        CommentResponse response = service.addComment(request);

        //Assert
        verify(commentRepository).save(any(Comment.class));
        Assertions.assertAll(
                () -> Assertions.assertNotNull(response),
                () -> Assertions.assertEquals(comment, response.getComment()),
                () -> Assertions.assertEquals(userId, response.getAuthorId()),
                () -> Assertions.assertEquals(discussionId, response.getDiscussionId()),
                () -> Assertions.assertEquals(parentCommentId, response.getParentCommentId())
        );
    }

    @Test
    public void addCommentWithInvalidDiscussionIdShouldFail() {
        //Arrange
        User author = getUser();
        String comment = "Unfortunately, I had a bad day!";
        CommentRequest request = new CommentRequest();
        request.setComment(comment);
        request.setAuthorId(userId);
        request.setDiscussionId(discussionId);
        doReturn(author).when(userRepository).findUserById(userId);
        doReturn(null).when(discussionRepository).findDiscussionById(discussionId);

        //Act //Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.addComment(request);
        }, "Discussion with id: "+ discussionId +" does not exist.");
        verify(commentRepository, never()).save(any(Comment.class));
    }


    @Test
    public void addCommentWithInvalidUserIdShouldFail() {
        //Arrange
        Discussion discussion = getDiscussion();
        String comment = "Unfortunately, I had a bad day!";
        CommentRequest request = new CommentRequest();
        request.setComment(comment);
        request.setAuthorId(userId);
        request.setDiscussionId(discussionId);
        doReturn(null).when(userRepository).findUserById(userId);
        doReturn(discussion).when(discussionRepository).findDiscussionById(discussionId);

        //Act //Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.addComment(request);
        }, "User with id: "+ userId +" does not exist.");
        verify(commentRepository, never()).save(any(Comment.class));
    }

    @Test
    public void deleteCommentShouldBeSuccessful() {
        //Arrange
        Comment comment = getParentComment();
        doReturn(comment).when(commentRepository).findCommentById(parentCommentId);

        //Act
        service.deleteComment(parentCommentId);

        //Assert
        verify(commentRepository).delete(comment);
    }

    @Test
    public void deleteCommentWithInvalidIdShouldFail() {
        //Arrange
        doReturn(null).when(commentRepository).findCommentById(parentCommentId);

        //Act //Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.deleteComment(parentCommentId);
        }, "Comment with id: "+ parentCommentId +" does not exist.");
        verify(commentRepository, never()).delete(any(Comment.class));
    }
}

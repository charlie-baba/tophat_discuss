package com.tophat.discuss.service.services.impl;

import com.tophat.discuss.data.models.Comment;
import com.tophat.discuss.data.models.Discussion;
import com.tophat.discuss.data.models.User;
import com.tophat.discuss.data.repository.CommentRepository;
import com.tophat.discuss.data.repository.DiscussionRepository;
import com.tophat.discuss.data.repository.UserRepository;
import com.tophat.discuss.data.pojo.request.CommentRequest;
import com.tophat.discuss.data.pojo.response.CommentResponse;
import com.tophat.discuss.service.services.CommentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Charles on 17/12/2022
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DiscussionRepository discussionRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    @Transactional
    public CommentResponse addComment(CommentRequest request) {
        Discussion discussion = discussionRepository.findDiscussionById(request.getDiscussionId());
        if (discussion == null) {
            throw new EntityNotFoundException("Discussion with id: "+ request.getAuthorId() +" does not exist.");
        }
        User author = userRepository.findUserById(request.getAuthorId());
        if (author == null) {
            throw new EntityNotFoundException("User with id: "+ request.getAuthorId() +" does not exist.");
        }
        Comment parentComment = null;
        if (request.getParentCommentId() != null) {
            parentComment = commentRepository.findCommentById(request.getParentCommentId());
        }

        Comment comment = new Comment();
        comment.setAuthor(author);
        comment.setDiscussion(discussion);
        comment.setComment(request.getComment());
        comment.setParentComment(parentComment);
        commentRepository.save(comment);
        return new CommentResponse(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findCommentById(id);
        if (comment == null) {
            throw new EntityNotFoundException("Comment with id: "+ id +" does not exist.");
        }
        commentRepository.delete(comment);
    }

}

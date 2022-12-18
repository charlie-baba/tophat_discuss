package com.tophat.discuss.service.services;

import com.tophat.discuss.data.models.Comment;
import com.tophat.discuss.service.pojo.request.CommentRequest;

/**
 * @author Charles on 17/12/2022
 */
public interface CommentService {

    Comment addComment(CommentRequest request);

    void deleteComment(Long id);
}

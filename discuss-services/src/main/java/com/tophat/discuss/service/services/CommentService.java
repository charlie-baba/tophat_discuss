package com.tophat.discuss.service.services;

import com.tophat.discuss.data.pojo.request.CommentRequest;
import com.tophat.discuss.data.pojo.response.CommentResponse;

/**
 * @author Charles on 17/12/2022
 */
public interface CommentService {

    CommentResponse addComment(CommentRequest request);

    void deleteComment(Long id);
}

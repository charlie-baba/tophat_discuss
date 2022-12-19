package com.tophat.discuss.api.controllers;

import com.tophat.discuss.data.pojo.request.CommentRequest;
import com.tophat.discuss.data.pojo.response.CommentResponse;
import com.tophat.discuss.service.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Charles on 17/12/2022
 */
@RestController
@RequestMapping("/api/v1/comment")
public class CommentsController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public CommentResponse addComment(@RequestBody CommentRequest request) {
        return commentService.addComment(request);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable("id") Long id) {
        commentService.deleteComment(id);
    }

}

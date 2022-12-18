package com.tophat.discuss.api.controllers;

import com.tophat.discuss.data.models.Discussion;
import com.tophat.discuss.service.pojo.request.DiscussionRequest;
import com.tophat.discuss.service.services.DiscussionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Charles on 17/12/2022
 */
@RestController
@RequestMapping("/api/v1/discussion")
public class DiscussionController {

    @Autowired
    private DiscussionService discussionService;

    @GetMapping("/getAll")
    public List<Discussion> getDiscussions() {
        return discussionService.getAllDiscussions();
    }

    @GetMapping("/{id}}")
    public Discussion getDiscussions(@PathVariable("id") Long id) {
        return discussionService.getDiscussion(id);
    }

    @PostMapping
    public Discussion createDiscussion(@RequestBody DiscussionRequest request) {
        return discussionService.createDiscussion(request);
    }

    @PutMapping("/{id}")
    public Discussion updateDiscussion(@PathVariable("id") Long id,
                           @RequestBody DiscussionRequest request) {
        return discussionService.updateDiscussion(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteDiscussion(@PathVariable("id") Long id) {
        discussionService.deleteDiscussion(id);
    }

}

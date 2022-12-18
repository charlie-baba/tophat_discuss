package com.tophat.discuss.service.services;

import com.tophat.discuss.data.models.Discussion;
import com.tophat.discuss.service.pojo.request.DiscussionRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Charles on 17/12/2022
 */
@Service
public interface DiscussionService {

    List<Discussion> getAllDiscussions();

    Discussion getDiscussion(Long id);

    Discussion createDiscussion(DiscussionRequest request);

    Discussion updateDiscussion(Long id, DiscussionRequest request);

    void deleteDiscussion(Long id);
}

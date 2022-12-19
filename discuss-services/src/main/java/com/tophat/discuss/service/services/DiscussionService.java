package com.tophat.discuss.service.services;

import com.tophat.discuss.data.pojo.request.DiscussionRequest;
import com.tophat.discuss.data.pojo.response.DiscussionResponse;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Charles on 17/12/2022
 */
@Service
public interface DiscussionService {

    List<DiscussionResponse> getAllDiscussions();

    DiscussionResponse getDiscussion(Long id);

    DiscussionResponse createDiscussion(DiscussionRequest request);

    DiscussionResponse updateDiscussion(Long id, DiscussionRequest request);

    void deleteDiscussion(Long id);
}

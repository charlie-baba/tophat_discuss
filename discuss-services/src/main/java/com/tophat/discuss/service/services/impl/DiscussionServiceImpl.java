package com.tophat.discuss.service.services.impl;

import com.tophat.discuss.data.models.Discussion;
import com.tophat.discuss.data.models.User;
import com.tophat.discuss.data.repository.DiscussionRepository;
import com.tophat.discuss.data.repository.UserRepository;
import com.tophat.discuss.data.pojo.request.DiscussionRequest;
import com.tophat.discuss.data.pojo.response.DiscussionResponse;
import com.tophat.discuss.service.services.DiscussionService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Charles on 17/12/2022
 */
@Service
public class DiscussionServiceImpl implements DiscussionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DiscussionRepository discussionRepository;

    public List<DiscussionResponse> getAllDiscussions() {
        return discussionRepository.findDiscussionsForListing();
    }

    @Override
    public DiscussionResponse getDiscussion(Long id) {
        Discussion discussion = discussionRepository.findDiscussionById(id);
        if (discussion == null) {
            throw new EntityNotFoundException("Discussion with id: "+ id +" does not exist.");
        }
        return new DiscussionResponse(discussion);
    }

    @Override
    @Transactional
    public DiscussionResponse createDiscussion(DiscussionRequest request) {
        User user = userRepository.findUserById(request.getAuthorId());
        if (user == null) {
            throw new EntityNotFoundException("User with id: "+ request.getAuthorId() +" does not exist.");
        }
        Discussion discussion = new Discussion();
        discussion.setAuthor(user);
        discussion.setQuestion(request.getQuestion());
        discussionRepository.save(discussion);
        return new DiscussionResponse(discussion);
    }

    @Override
    @Transactional
    public DiscussionResponse updateDiscussion(Long id, DiscussionRequest request) {
        Discussion discussion = discussionRepository.findDiscussionById(id);
        if (discussion == null) {
            throw new EntityNotFoundException("Discussion with id: "+ id +" does not exist.");
        }

        discussion.setQuestion(request.getQuestion());
        discussionRepository.save(discussion);
        return new DiscussionResponse(discussion);
    }

    @Override
    @Transactional
    public void deleteDiscussion(Long id) {
        Discussion discussion = discussionRepository.findDiscussionById(id);
        if (discussion == null) {
            throw new EntityNotFoundException("Discussion with id: "+ id +" does not exist.");
        }

        discussionRepository.delete(discussion);
    }

}

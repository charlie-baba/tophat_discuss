package com.tophat.discuss.api.controllers;

import com.tophat.discuss.data.enums.UserType;
import com.tophat.discuss.data.models.Comment;
import com.tophat.discuss.data.models.Discussion;
import com.tophat.discuss.data.models.User;

import java.util.Date;

/**
 * @author Charles on 17/12/2022
 */
public abstract class SharedTestData {

    protected final String testUsername = "doe_001";
    protected final Long userId = 79238741197L;
    protected final Long discussionId = 9802380271L;
    protected final Long parentCommentId = 6924791729L;
    protected final Date now = new Date();

    protected User getUser() {
        User user = new User();
        user.setId(userId);
        user.setUserType(UserType.Student);
        user.setUsername(testUsername);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPhoneNumber("6479363787");
        user.setDateCreated(now);
        return user;
    }

    protected Discussion getDiscussion() {
        Discussion discussion = new Discussion();
        discussion.setAuthor(getUser());
        discussion.setQuestion("How is your day going so far?");
        discussion.setDateCreated(now);
        discussion.setId(discussionId);
        return discussion;
    }

    protected Comment getParentComment() {
        Comment comment = new Comment();
        comment.setComment("it is going great, Thanks!");
        comment.setAuthor(getUser());
        comment.setDiscussion(getDiscussion());
        comment.setId(parentCommentId);
        comment.setDateCreated(now);
        return comment;
    }
}

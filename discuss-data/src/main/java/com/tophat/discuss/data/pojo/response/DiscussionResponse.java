package com.tophat.discuss.data.pojo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tophat.discuss.data.models.Discussion;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Charles on 17/12/2022
 */
@Getter
@Setter
@ToString
@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscussionResponse implements Serializable {

    private Long id;

    private String question;

    private Long authorId;

    private Date dateCreated;

    private Long commentsCount;

    private Set<CommentResponse> comments = new HashSet<>();

    public DiscussionResponse() {}

    public DiscussionResponse(Discussion discussion) {
        this.id = discussion.getId();
        this.question = discussion.getQuestion();
        this.authorId = discussion.getAuthor().getId();
        this.dateCreated = discussion.getDateCreated();
        if (!CollectionUtils.isEmpty(discussion.getComments())) {
            comments = discussion.getComments().stream().map(CommentResponse::new).collect(Collectors.toSet());
            commentsCount = (long) comments.size();
        }
    }

    public DiscussionResponse(Long id, String question, Long authorId, Date dateCreated, Long commentsCount) {
        this.id = id;
        this.question = question;
        this.authorId = authorId;
        this.dateCreated = dateCreated;
        this.commentsCount = commentsCount;
    }
}

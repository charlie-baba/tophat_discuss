package com.tophat.discuss.data.pojo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tophat.discuss.data.models.Comment;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Charles on 17/12/2022
 */
@Getter
@Setter
@ToString
@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentResponse implements Serializable {

    private Long id;

    private String comment;

    private Long authorId;

    private Long discussionId;

    private Long parentCommentId;

    private Date dateCreated;

    public CommentResponse() {}

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.authorId = comment.getAuthor().getId();
        this.discussionId = comment.getDiscussion().getId();
        this.dateCreated = comment.getDateCreated();
        if (comment.getParentComment() != null)
            this.parentCommentId = comment.getParentComment().getId();
    }

}

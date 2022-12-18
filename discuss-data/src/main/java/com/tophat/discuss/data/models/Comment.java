package com.tophat.discuss.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Charles on 17/12/2022
 */
@Entity
@Getter
@Setter
@Table(name = "comments")
public class Comment extends BaseEntity {

    @Column(name = "comment")
    private String comment;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "user_fk")
    private User author;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Discussion.class)
    @JoinColumn(name = "discussion_fk")
    private Discussion discussion;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Comment.class)
    @JoinColumn(name = "parent_comment_fk")
    private Comment parentComment;

}

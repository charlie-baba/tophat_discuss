package com.tophat.discuss.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Charles on 17/12/2022
 */
@Entity
@Getter
@Setter
@Table(name = "discussions")
public class Discussion extends BaseEntity {

    @Column(name = "question")
    private String question;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "user_fk")
    private User author;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, targetEntity = Comment.class, cascade = CascadeType.ALL)
    @JoinColumn(name="question_fk")
    private Set<Comment> comments = new HashSet<>();

}

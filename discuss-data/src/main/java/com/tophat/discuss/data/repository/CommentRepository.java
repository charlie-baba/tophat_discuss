package com.tophat.discuss.data.repository;

import com.tophat.discuss.data.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Charles on 18/12/2022
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment findCommentById(Long id);
}

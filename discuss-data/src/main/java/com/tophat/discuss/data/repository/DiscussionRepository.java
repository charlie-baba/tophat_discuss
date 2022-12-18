package com.tophat.discuss.data.repository;

import com.tophat.discuss.data.models.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Charles on 17/12/2022
 */
@Repository
public interface DiscussionRepository extends JpaRepository<Discussion, Long> {

    @Query("select d from Discussion d")
    List<Discussion> getAllDiscussions();

    Discussion findDiscussionById(Long id);

    @Query("select d from Discussion d left join fetch d.comments where d.id = :id")
    Discussion loadDiscussionById(@Param("id") Long id);

}

package com.tophat.discuss.data.repository;

import com.tophat.discuss.data.models.Discussion;
import com.tophat.discuss.data.pojo.response.DiscussionResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Charles on 17/12/2022
 */
@Repository
public interface DiscussionRepository extends JpaRepository<Discussion, Long> {

    @Query("select new com.tophat.discuss.data.pojo.response.DiscussionResponse(d.id, d.question, d.author.id, d.dateCreated, " +
            "(select count(c.id) from Comment c where c.discussion.id = d.id)) from Discussion d group by d.id order by d.dateCreated desc")
    List<DiscussionResponse> findDiscussionsForListing();

    Discussion findDiscussionById(Long id);

}

package com.tophat.discuss.data.pojo.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author Charles on 17/12/2022
 */
@Getter
@Setter
@ToString
@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentRequest implements Serializable {

    @Required(message = "Discussion id is required")
    private Long discussionId;

    private Long parentCommentId;

    @Required(message = "User id is required")
    private Long authorId;

    @NotBlank(message = "Comment is required")
    private String comment;

}

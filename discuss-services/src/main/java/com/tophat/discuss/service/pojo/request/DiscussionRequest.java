package com.tophat.discuss.service.pojo.request;

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
public class DiscussionRequest implements Serializable {

    @Required(message = "User id is required")
    private Long authorId;

    @NotBlank(message = "Question is required")
    private String question;

}

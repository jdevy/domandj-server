package com.jdevy.domandj.comment;

import com.jdevy.domandj.post.PostId;
import com.jdevy.domandj.user.Username;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

@Document
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment implements Serializable{

    @Id
    private String id;

    @NotBlank
    private String content;

    @CreatedDate
    private LocalDateTime createdDate;

    private PostId post;

    @CreatedBy
    private Username author;
}

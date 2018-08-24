package com.jdevy.domandj.student;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Document
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student implements Serializable {
    @Id
    private String id;

    @NotBlank
    private String lastName;

    @NotBlank
    private String firstName;

    private String avatar;
}

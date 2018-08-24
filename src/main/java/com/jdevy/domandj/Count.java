package com.jdevy.domandj;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serializable;

@Getter
@ToString
@AllArgsConstructor(onConstructor = @__({@JsonCreator}))
public class Count implements Serializable {

    private long count;
}

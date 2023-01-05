package com.egorkin.model.datamodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    @JsonProperty
    private String name;
    @JsonProperty
    private String catchPhrase;
    @JsonProperty
    private String bs;
}

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
public class Address {
    @JsonProperty
    private String street;
    @JsonProperty
    private String suite;
    @JsonProperty
    private String city;
    @JsonProperty
    private String zipcode;
    @JsonProperty
    private Geo geo;
}

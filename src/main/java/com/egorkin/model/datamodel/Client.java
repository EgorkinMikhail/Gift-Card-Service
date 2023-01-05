package com.egorkin.model.datamodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @JsonProperty
    private Integer id;
    @JsonProperty
    private String name;
    @JsonProperty
    private String username;
    @JsonProperty
    private String email;
    @JsonProperty
    private Address address;
    @JsonProperty
    private String phone;
    @JsonProperty
    private String website;
    @JsonProperty
    private Company company;
    @JsonIgnore
    private Boolean winner;
}

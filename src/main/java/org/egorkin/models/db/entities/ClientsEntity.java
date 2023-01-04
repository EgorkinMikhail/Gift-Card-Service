package org.egorkin.models.db.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Clients")
public class ClientsEntity {
    @Id
    @Column(unique = true, nullable = false, name = "client_id")
    @JsonProperty("id")
    private String clientId;
    private String name;
    private String userName;
    private String email;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;
    private String phone;
    private String website;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

}

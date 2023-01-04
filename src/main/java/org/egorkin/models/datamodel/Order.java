package org.egorkin.models.datamodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class Order {
    String userId;
    Double amount;
}

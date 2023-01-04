package org.egorkin.models.db;

import org.egorkin.models.db.entities.ClientsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientsRepository extends JpaRepository<ClientsEntity, String> {
}

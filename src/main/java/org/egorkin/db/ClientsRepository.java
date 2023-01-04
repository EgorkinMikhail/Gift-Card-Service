package org.egorkin.db;

import org.egorkin.db.entities.ClientsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientsRepository extends JpaRepository<ClientsEntity, String> {
}

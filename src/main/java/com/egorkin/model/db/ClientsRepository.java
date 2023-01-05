package com.egorkin.model.db;

import com.egorkin.model.db.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientsRepository extends JpaRepository<ClientEntity, Integer> {

    @Transactional(readOnly = true)
    List<ClientEntity> findAll();

    @Transactional(readOnly = true)
    Optional<ClientEntity> findById(Integer key);

    @Modifying
    @Transactional
    @Query(value = "UPDATE clients SET winner = false WHERE winner = true", nativeQuery = true)
    void clearWinners();

    @Transactional(readOnly = true)
    @Query(value = "SELECT * FROM clients WHERE winner = true", nativeQuery = true)
    Optional<ClientEntity> findWinner();
}

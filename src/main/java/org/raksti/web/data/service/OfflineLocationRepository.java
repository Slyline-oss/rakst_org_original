package org.raksti.web.data.service;

import org.raksti.web.data.entity.OfflineLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface OfflineLocationRepository extends JpaRepository<OfflineLocation, UUID> {

    @Query("UPDATE OfflineLocation ol SET ol.slotsTaken = 0")
    @Modifying
    @Transactional
    void resetToInitial();

}

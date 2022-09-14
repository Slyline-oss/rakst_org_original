package org.raksti.web.data.service;

import org.raksti.web.data.entity.OfflineLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OfflineLocationRepository extends JpaRepository<OfflineLocation, UUID> {
    List<OfflineLocation> findAllByCountryAndCityAndAddress(String country, String city, String address);
}

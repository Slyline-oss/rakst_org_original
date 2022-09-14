package org.raksti.web.data.service;

import org.raksti.web.data.entity.OfflineLocation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OfflineLocationService {
    private final OfflineLocationRepository offlineLocationRepository;

    public OfflineLocationService(OfflineLocationRepository offlineLocationRepository) {
        this.offlineLocationRepository = offlineLocationRepository;
    }

    public List<OfflineLocation> getAll() {
        return offlineLocationRepository.findAll();
    }

    public List<OfflineLocation> getAllByCountyAndByCityAndByAddress(String country, String city, String address) {
        return offlineLocationRepository.findAllByCountryAndCityAndAddress(country, city, address);
    }

    public OfflineLocation getById(UUID uuid) {
        return offlineLocationRepository.getReferenceById(uuid);
    }

    public void save(OfflineLocation offlineLocation) {
        offlineLocationRepository.save(offlineLocation);
    }

    public void delete(OfflineLocation offlineLocation) {
        offlineLocationRepository.delete(offlineLocation);
    }
}

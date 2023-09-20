package org.raksti.web.data.service;

import org.jetbrains.annotations.NotNull;
import org.raksti.web.data.entity.OfflineLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OfflineLocationService {
    private final OfflineLocationRepository offlineLocationRepository;

    @Autowired
    public OfflineLocationService(@NotNull OfflineLocationRepository offlineLocationRepository) {
        this.offlineLocationRepository = offlineLocationRepository;
    }

    public List<OfflineLocation> getAll() {
        return offlineLocationRepository.findAll();
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

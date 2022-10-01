package org.raksti.web.data.service;

import org.raksti.web.data.entity.Sponsor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SponsorService {

    private final SponsorRepository sponsorRepository;

    public SponsorService(SponsorRepository sponsorRepository) {
        this.sponsorRepository = sponsorRepository;
    }

    public Optional<Sponsor> findById(Long id) {
        return sponsorRepository.findById(id);
    }

    public List<Sponsor> getAllEntities() {
        return sponsorRepository.findAll();
    }

    public void save(Sponsor sponsor) {
        sponsorRepository.save(sponsor);
    }

    public void deleteEntity(Sponsor sponsor) {
        sponsorRepository.delete(sponsor);
    }
}

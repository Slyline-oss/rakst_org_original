package org.raksti.web.data.service;

import org.raksti.web.data.entity.DonateText;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonateService {

    private final DonateRepository donateRepository;

    public DonateService(DonateRepository donateRepository) {
        this.donateRepository = donateRepository;
    }

    public void saveDonate(DonateText donateText) {
        donateRepository.save(donateText);
    }

    public void deleteAll() {
        donateRepository.deleteAll();
    }

    public List<DonateText> getAll() {
        return donateRepository.findAll();
    }
}

package org.raksti.web.data.service;

import org.raksti.web.data.entity.Pages;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PagesService {

    private final PagesRepository pagesRepository;

    public PagesService(PagesRepository pagesRepository) {
        this.pagesRepository = pagesRepository;
    }

    public Optional<Pages> findById(String id) {
        return pagesRepository.findById(id);
    }

    public void savePage(Pages pages) {
        pagesRepository.save(pages);
    }

}

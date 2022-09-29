package org.raksti.web.data.service;

import org.raksti.web.data.entity.About;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AboutService {

    private final AboutRepository aboutRepository;

    public AboutService(AboutRepository aboutRepository) {
        this.aboutRepository = aboutRepository;
    }

    public Optional<About> getAboutById(Long id) {
        return aboutRepository.findById(id);
    }

    public void saveAbout(About about) {
        aboutRepository.save(about);
    }

    public void deleteAll() {
        aboutRepository.deleteAll();
    }

    public List<About> getAll() {
        return aboutRepository.findAll();
    }
}

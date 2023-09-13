package org.raksti.web.views.about;

import org.raksti.web.data.entity.About;
import org.raksti.web.data.service.AboutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;


@Component
public class AboutService {

    private final AboutRepository aboutRepository;
    private final CacheManager cacheManager;

    @Autowired
    public AboutService(@NotNull AboutRepository aboutRepository, @NotNull CacheManager cacheManager) {
        this.aboutRepository = aboutRepository;
        this.cacheManager = cacheManager;
    }

    @Cacheable(value = "about")
    public List<About> getAll() {
        return aboutRepository.findAllByOrderByIdDesc();
    }

    @CacheEvict(value = "about")
    public void save(@NotNull About about) {
        aboutRepository.save(about);
        Objects.requireNonNull(cacheManager.getCache("about")).clear();
    }

    @CacheEvict(value = "about")
    public void delete(@NotNull About about) {
        aboutRepository.delete(about);
        Objects.requireNonNull(cacheManager.getCache("about")).clear();
    }

}

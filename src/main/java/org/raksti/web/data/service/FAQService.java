package org.raksti.web.data.service;

import org.jetbrains.annotations.NotNull;
import org.raksti.web.data.entity.FAQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service(value = "faq_service")
public class FAQService {

    private final FAQRepository faqRepository;
    private final CacheManager cacheManager;

    @Autowired
    public FAQService(@NotNull FAQRepository faqRepository, @NotNull CacheManager cacheManager) {
        this.faqRepository = faqRepository;
        this.cacheManager = cacheManager;
    }

    public void delete(@NotNull FAQ item) {
        faqRepository.delete(item);
        Objects.requireNonNull(cacheManager.getCache("faq")).clear();
    }

    @NotNull
    @Cacheable(value = "faq")
    public List<FAQ> getAll() {
        return faqRepository.findAll();
    }

    public void save(@NotNull FAQ faq) {
        faqRepository.save(faq);
        Objects.requireNonNull(cacheManager.getCache("faq")).clear();
    }

}

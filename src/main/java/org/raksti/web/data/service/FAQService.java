package org.raksti.web.data.service;

import org.raksti.web.data.entity.FAQ;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service(value = "faq_service")
public class FAQService {

    private final FAQRepository faqRepository;

    public FAQService(FAQRepository faqRepository) {
        this.faqRepository = faqRepository;
    }

    public Optional<FAQ> getById(Long id) {
        return faqRepository.findById(id);
    }

    public void deleteItems(Set<FAQ> list) {
        faqRepository.deleteAll(list);
    }

    public Long count() {
        return faqRepository.count();
    }

    public List<FAQ> getAllFAQ() {
        return faqRepository.findAll();
    }

    public void save(FAQ faq) {
        faqRepository.save(faq);
    }

    public FAQ getByQuestion(String question) {
        return faqRepository.findByQuestion(question);
    }

}

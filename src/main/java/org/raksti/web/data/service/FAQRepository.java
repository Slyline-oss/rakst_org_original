package org.raksti.web.data.service;

import org.raksti.web.data.entity.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FAQRepository extends JpaRepository<FAQ, Long> {

    Optional<FAQ> findById(Long id);
    FAQ findByQuestion(String question);

}

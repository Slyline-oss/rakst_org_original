package org.raksti.web.data.service;

import org.raksti.web.data.entity.About;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AboutRepository extends JpaRepository<About, Long> {

    Optional<About> findById(Long id);
}

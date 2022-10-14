package org.raksti.web.data.service;

import org.raksti.web.data.entity.Pages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PagesRepository extends JpaRepository<Pages, String> {

    @Override
    Optional<Pages> findById(String id);
}

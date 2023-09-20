package org.raksti.web.data.service;

import org.jetbrains.annotations.NotNull;
import org.raksti.web.data.entity.Pages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PagesRepository extends JpaRepository<Pages, String> {

    @Override
    @NotNull
    Optional<Pages> findById(@NotNull String id);
}

package org.raksti.web.data.service;

import org.jetbrains.annotations.NotNull;
import org.raksti.web.data.entity.About;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AboutRepository extends JpaRepository<About, Long> {

    @Override
    @NotNull
    Optional<About> findById(@NotNull Long id);

    List<About> findAllByOrderByIdDesc();
}

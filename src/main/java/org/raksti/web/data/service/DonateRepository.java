package org.raksti.web.data.service;

import org.raksti.web.data.entity.DonateText;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DonateRepository extends JpaRepository<DonateText, Long> {

    @Override
    Optional<DonateText> findById(Long aLong);
}

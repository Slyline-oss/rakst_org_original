package org.raksti.web.data.service;

import org.raksti.web.data.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResultRepository extends JpaRepository<Result, String> {

    @Override
    Optional<Result> findById(String email);


}

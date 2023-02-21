package org.raksti.web.data.service;

import org.raksti.web.data.entity.Result;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("resultService")
public class ResultService {

    private final ResultRepository resultRepository;

    public ResultService(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    public Optional<Result> findByEmail(String email) {
        return resultRepository.findById(email);
    }

}

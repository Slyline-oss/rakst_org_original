package org.raksti.web.data.service;

import org.raksti.web.data.entity.OfflineExam;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OfflineExamService {
    private final OfflineExamRepository offlineExamRepository;

    public OfflineExamService(OfflineExamRepository offlineExamRepository) {
        this.offlineExamRepository = offlineExamRepository;
    }

    public List<OfflineExam> getAll() {
        return offlineExamRepository.findAll();
    }

    public OfflineExam findById(UUID uuid) {
        return offlineExamRepository.getReferenceById(uuid);
    }

    public void save(OfflineExam offlineExam) {
        offlineExamRepository.save(offlineExam);
    }

}

package org.raksti.web.data.service;

import org.raksti.web.data.entity.OfflineExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OfflineExamRepository extends JpaRepository<OfflineExam, UUID> {
}

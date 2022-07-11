package com.example.application.data.service;

import com.example.application.data.entity.Text;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface TextRepository extends JpaRepository<Text, String> {

    Optional<Text> findById(String id);

    @Modifying
    @Query("update Text t set t.content_1 = ?1, t.content_2 = ?2, t.content_3 = ?3  where t.id = ?4")
    public void setContentById(String content_1, String content_2, String content_3, String id);
}

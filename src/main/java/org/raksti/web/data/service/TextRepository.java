package org.raksti.web.data.service;

import org.jetbrains.annotations.NotNull;
import org.raksti.web.data.entity.Text;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Repository
public interface TextRepository extends JpaRepository<Text, String> {

    @NotNull Optional<Text> findById(@NotNull String id);

    @Modifying
    @Query("update Text t set t.content_1 = ?1, t.content_2 = ?2, t.content_3 = ?3  where t.id = ?4")
    void setContentById(String content_1, String content_2, String content_3, String id);
}

package org.raksti.web.data.service;

import org.jetbrains.annotations.NotNull;
import org.raksti.web.data.entity.Text;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Repository
public interface TextRepository extends JpaRepository<Text, String> {

    @NotNull Optional<Text> findById(@NotNull String id);

}

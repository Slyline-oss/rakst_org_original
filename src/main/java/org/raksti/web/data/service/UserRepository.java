package org.raksti.web.data.service;

import org.raksti.web.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmail(String email);

    List<User> findByAllowEmailsTrue();

    @Modifying
    @Query("update User u set u.hashedPassword = ?1 where u.email = ?2")
    void setUsersPasswordByEmail(String password, String email);

    @Modifying
    @Query("UPDATE User u SET u.offlineLocation = null, u.offlineNote = null")
    @Transactional
    void clearOfflineParticipationData();

    User findByResetPasswordToken(String token);

    User findByEmailConfirmationToken(String token);
}


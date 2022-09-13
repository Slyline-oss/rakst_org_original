package org.raksti.web.data.service;

import org.raksti.web.data.entity.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmail(String email);

    @Modifying
    @Query("update User u set u.hashedPassword = ?1 where u.email = ?2")
    void setUsersPasswordByEmail(String password, String email);

    User findByResetPasswordToken(String token);
    User findByEmailConfirmationToken(String token);
}

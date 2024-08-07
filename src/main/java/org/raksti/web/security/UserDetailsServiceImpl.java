package org.raksti.web.security;

import org.jetbrains.annotations.NotNull;
import org.raksti.web.data.Role;
import org.raksti.web.data.entity.User;
import org.raksti.web.data.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(@NotNull UserRepository userRepository, @NotNull PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email.toLowerCase(Locale.ROOT));
        if (user == null) {
//            Notification.show("Šāds lietotājs nav atrasts", 5000, Notification.Position.TOP_CENTER)
//                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
            throw new UsernameNotFoundException("No user present with email: " + email);
        } else {
            if (!user.isConfirmed()) {
//                Notification.show("Jūsu e-pasta adrese nav apstiprināta", 5000, Notification.Position.TOP_CENTER)
//                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
                throw new UsernameNotFoundException("Email is not confirmed " + email);
            }
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getHashedPassword(),
                    getAuthorities(user));
        }
    }

    private static List<GrantedAuthority> getAuthorities(User user) {
        return user.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());

    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void register(String firstName, String lastName, String email, String password1, String emailConfirmationToken, boolean allowEmails) {
        userRepository.save(new User(firstName, firstName, lastName, passwordEncoder.encode(password1), email, Set.of(Role.USER), emailConfirmationToken, allowEmails));
    }

    public void register(String email, String firstName, String lastName, String password, LocalDate birthday, String telNumber, String language, String
                         country, String city, String age, String education, String gender, boolean allowEmails) {
        userRepository.save(new User(email, firstName, lastName, passwordEncoder.encode(password), Set.of(Role.USER), birthday, telNumber, language, age, country, city, education, gender, allowEmails));
    }

    public void registerAdmin(String email, String password) {
        userRepository.save(new User("","","",  passwordEncoder.encode(password), email, Set.of(Role.ADMIN), true));
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByEmailConfirmationToken(String token) {
        return userRepository.findByEmailConfirmationToken(token);
    }

    public void updatePassword(String email, String password) {
       userRepository.setUsersPasswordByEmail(passwordEncoder.encode(password), email);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }


    public void updateResetPasswordToken(String token, String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        }
    }

    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

}

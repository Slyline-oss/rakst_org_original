package com.example.application.security;

import com.example.application.data.Role;
import com.example.application.data.entity.Provider;
import com.example.application.data.entity.User;
import com.example.application.data.service.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user present with username: " + username);
        } else {
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getHashedPassword(),
                    getAuthorities(user));
        }
    }


    private static List<GrantedAuthority> getAuthorities(User user) {
        return user.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());

    }

    public void register(String firstName, String lastName, String email, String password1, String emailConfirmationToken) {
        userRepository.save(new User(firstName, firstName, lastName, passwordEncoder.encode(password1), email, Set.of(Role.USER), emailConfirmationToken));
    }

    public void register(String email, String password, String emailConfirmationToken) {
        userRepository.save(new User(email, passwordEncoder.encode(password), emailConfirmationToken));
    }

    public void register(String email, String firstName, String lastName, String password, LocalDate birthday, String telNumber, String language) {
        userRepository.save(new User(email, firstName, lastName, passwordEncoder.encode(password), birthday, telNumber, language, Set.of(Role.USER)));
    }

    public void register(String email, String firstName, String lastName, String password, LocalDate birthday, String telNumber, String language, String
                         country, String city, String age, String education, String gender) {
        userRepository.save(new User(email, firstName, lastName, passwordEncoder.encode(password), Set.of(Role.USER), birthday, telNumber, language, age, country, city, education, gender));
    }

    public void registerAdmin(String email, String password) {
        userRepository.save(new User("","","",  passwordEncoder.encode(password), email, Set.of(Role.ADMIN, Role.USER)));
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

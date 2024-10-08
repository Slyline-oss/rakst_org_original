package org.raksti.web.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;
import org.raksti.web.data.Role;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@SuppressWarnings("unused")
@Entity
@Table(name = "application_user")
public class User extends AbstractEntity {

    private String resetPasswordToken;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    @JsonIgnore
    private String hashedPassword;
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;
    @Lob
    private String profilePictureUrl;
    @Enumerated(EnumType.STRING)
    private Provider provider;
    private LocalDate birthday;
    private String telNumber;
    private String language;
    private String age;
    private String country;
    private String city;
    private String education;
    private String gender;
    private boolean anonymous;
    private boolean confirmed;
    private String emailConfirmationToken;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "offline_location_id", referencedColumnName = "id")
    private OfflineLocation offlineLocation = null;

    private String offlineNote;
    private boolean allowEmails;

    //registration not anonymous
    public User(String username, String firstName, String lastName, String hashedPassword, String email, Set<Role> roles, String emailConfirmationToken, boolean allowEmails) {
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.email = email;
        this.roles = roles;
        this.firstName = firstName;
        this.lastName = lastName;
        this.anonymous = false;
        this.emailConfirmationToken = emailConfirmationToken;
        this.confirmed = false;
        this.allowEmails = allowEmails;
    }

    //admin registration
    public User(String username, String firstName, String lastName, String hashedPassword, String email, Set<Role> roles, boolean allowEmails) {
        this.email = email;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hashedPassword = hashedPassword;
        this.roles = roles;
        this.confirmed = true;
        this.allowEmails = allowEmails;
    }

    public User(String email, String firstName, String lastName, String hashedPassword, LocalDate birthday, String telNumber, String language, Set<Role> roles, boolean allowEmails) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hashedPassword = hashedPassword;
        this.birthday = birthday;
        this.telNumber = telNumber;
        this.language = language;
        this.roles = roles;
        this.anonymous = false;
        this.confirmed = true;
        this.allowEmails = allowEmails;
    }

    //manual user creation
    public User(String email, String firstName, String lastName, String hashedPassword, Set<Role> roles, LocalDate birthday, String telNumber, String language, String age, String country, String city, String education, String gender, boolean allowEmails) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hashedPassword = hashedPassword;
        this.roles = roles;
        this.birthday = birthday;
        this.telNumber = telNumber;
        this.language = language;
        this.age = age;
        this.country = country;
        this.city = city;
        this.education = education;
        this.gender = gender;
        this.anonymous = false;
        this.confirmed = true;
        this.allowEmails = allowEmails;
    }

    public User() {}

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getEmail() {
        return StringUtils.lowerCase(email);
    }

    public void setEmail(String email) {
        this.email = StringUtils.lowerCase(email);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getEmailConfirmationToken() {
        return emailConfirmationToken;
    }

    public void setEmailConfirmationToken(String emailConfirmationToken) {
        this.emailConfirmationToken = emailConfirmationToken;
    }

    public OfflineLocation getOfflineLocation() {
        return offlineLocation;
    }

    public void setOfflineLocation(OfflineLocation offlineLocation) {
        this.offlineLocation = offlineLocation;
    }

    public String getOfflineNote() {
        return offlineNote;
    }

    public void setOfflineNote(String offlineNote) {
        this.offlineNote = offlineNote;
    }

    public boolean isAllowEmails() {
        return allowEmails;
    }

    public void setAllowEmails(boolean allowEmails) {
        this.allowEmails = allowEmails;
    }
}

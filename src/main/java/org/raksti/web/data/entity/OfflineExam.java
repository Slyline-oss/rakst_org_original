package org.raksti.web.data.entity;

import org.jsoup.internal.StringUtil;
import org.raksti.web.data.entity.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "offline_exams")
public class OfflineExam extends AbstractEntity{
    private String city;
    private String address;
    private Integer totalSlots;
    private Integer availableSlots;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "participants",
            joinColumns = @JoinColumn(name = "offline_exam_id"),
            inverseJoinColumns = @JoinColumn(name = "tmp_user_id")
    )
    private Set<User> participants = new HashSet<>();

    public OfflineExam(String city, String address, Integer totalSlots) {
        this.city = city;
        this.address = address;
        this.totalSlots = totalSlots;
        this.availableSlots = totalSlots;
    }

    public OfflineExam() {}

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getTotalSlots() {
        return totalSlots;
    }

    public void setTotalSlots(Integer totalSlots) {
        this.totalSlots = totalSlots;
    }

    public Integer getAvailableSlots() {
        return availableSlots;
    }

    public void setAvailableSlots(Integer availableSlots) {
        this.availableSlots = availableSlots;
    }

    public Set<User> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<User> participants) {
        this.participants = participants;
    }
}

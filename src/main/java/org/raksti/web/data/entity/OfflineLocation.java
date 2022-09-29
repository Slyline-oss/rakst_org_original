package org.raksti.web.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "offline_locations")
public class OfflineLocation extends AbstractEntity{
    private String country;
    private String city;
    private String address;
    private Integer slotsTotal;
    private Integer slotsTaken = 0;
    @JsonIgnore
    @OneToMany(mappedBy = "offlineLocation", fetch = FetchType.LAZY)
    private Set<User> participants = new HashSet<>();
    public OfflineLocation(String country, String city, String address, Integer slotsTotal) {
        this.country = country;
        this.city = city;
        this.address = address;
        this.slotsTotal = slotsTotal;
    }

    public OfflineLocation() {

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getSlotsTotal() {
        return slotsTotal;
    }

    public void setSlotsTotal(Integer slotsTotal) {
        this.slotsTotal = slotsTotal;
    }

    public Integer getSlotsTaken() {
        return slotsTaken;
    }

    public void setSlotsTaken(Integer slotsTaken) {
        this.slotsTaken = slotsTaken;
    }

    public Set<User> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<User> participants) {
        this.participants = participants;
    }
}

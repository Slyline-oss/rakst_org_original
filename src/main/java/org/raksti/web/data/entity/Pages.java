package org.raksti.web.data.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "original_text")
public class Pages {

    @Id
    private String id;

    private boolean powerOn;

    public Pages() {
    }

    public Pages(String id, boolean powerOn) {
        this.id = id;
        this.powerOn = powerOn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isPowerOn() {
        return powerOn;
    }

    public void setPowerOn(boolean on) {
        this.powerOn = on;
    }
}

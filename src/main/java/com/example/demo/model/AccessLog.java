package com.example.demo.model;

import javax.persistence.*;
import java.time.Instant;

@Entity
public class AccessLog {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private DigitalKey digitalKey;

    @ManyToOne
    private Guest guest;

    private Instant accessTime;
    private String result;

    // getters & setters
    public DigitalKey getDigitalKey() { return digitalKey; }
    public void setDigitalKey(DigitalKey digitalKey) { this.digitalKey = digitalKey; }

    public Guest getGuest() { return guest; }
    public void setGuest(Guest guest) { this.guest = guest; }

    public Instant getAccessTime() { return accessTime; }
    public void setAccessTime(Instant accessTime) { this.accessTime = accessTime; }

    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
}

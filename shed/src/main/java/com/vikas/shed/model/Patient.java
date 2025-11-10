package com.vikas.shed.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int age;

    @Column(name = "patient_condition")
    private String patientCondition;

    private int severityLevel;

    // 🆕 User will only enter a simple address like "Kengeri, Bangalore"
    private String address;

    private LocalDateTime arrivalTime = LocalDateTime.now();

    public Patient() {}

    public Patient(String name, int age, String patientCondition, int severityLevel, String address) {
        this.name = name;
        this.age = age;
        this.patientCondition = patientCondition;
        this.severityLevel = severityLevel;
        this.address = address;
        this.arrivalTime = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getPatientCondition() { return patientCondition; }
    public void setPatientCondition(String patientCondition) { this.patientCondition = patientCondition; }

    public int getSeverityLevel() { return severityLevel; }
    public void setSeverityLevel(int severityLevel) { this.severityLevel = severityLevel; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public LocalDateTime getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(LocalDateTime arrivalTime) { this.arrivalTime = arrivalTime; }
}

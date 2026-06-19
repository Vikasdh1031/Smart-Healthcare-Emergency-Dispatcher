package com.vikas.shed.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "hospital")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }


    private String name;
    private String location;
    private int capacity;
    private int availableBeds;

    // ✅ Add these for mapping hospital coordinates
    private double latitude;
    private double longitude;
}
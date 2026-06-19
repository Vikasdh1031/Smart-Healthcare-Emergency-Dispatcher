package com.vikas.shed.service;

import com.vikas.shed.model.Hospital;
import com.vikas.shed.model.Patient;
import com.vikas.shed.repository.HospitalRepository;
import com.vikas.shed.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class DispatcherService {

    private final HospitalRepository hospitalRepository;
    private final PatientRepository patientRepository;

    public DispatcherService(HospitalRepository hospitalRepository, PatientRepository patientRepository) {
        this.hospitalRepository = hospitalRepository;
        this.patientRepository = patientRepository;
    }

    // ✅ Convert address to coordinates (using OpenStreetMap)
    public double[] getCoordinatesFromAddress(String address) {
        try {
            String url = "https://nominatim.openstreetmap.org/search?q=" +
                    address.replace(" ", "+") + "&format=json&limit=1";

            RestTemplate restTemplate = new RestTemplate();
            List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);

            if (response != null && !response.isEmpty()) {
                Map<String, Object> location = response.get(0);
                double lat = Double.parseDouble((String) location.get("lat"));
                double lon = Double.parseDouble((String) location.get("lon"));
                return new double[]{lat, lon};
            } else {
                System.err.println("❌ Address to Coordinates Error: Could not find coordinates for address: " + address);
            }
        } catch (Exception e) {
            System.err.println("❌ Address Conversion Error: " + e.getMessage());
        }
        return new double[]{0, 0};
    }

    // ✅ Haversine distance formula
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    // ✅ Assign nearest hospital & return route info
    public Map<String, Object> assignHospital(Patient patient) {
        List<Hospital> hospitals = hospitalRepository.findAll();

        if (hospitals.isEmpty()) {
            throw new RuntimeException("No hospitals found in the database!");
        }

        // Convert patient's address → coordinates
        double[] coords = getCoordinatesFromAddress(patient.getAddress());
        double patientLat = coords[0];
        double patientLon = coords[1];

        // Find nearest hospital
        Hospital nearest = hospitals.stream()
                .filter(h -> h.getAvailableBeds() > 0)
                .min(Comparator.comparingDouble(h ->
                        calculateDistance(patientLat, patientLon, h.getLatitude(), h.getLongitude())))
                .orElseThrow(() -> new RuntimeException("No hospital with available beds found."));

        // Calculate distance
        double distance = calculateDistance(patientLat, patientLon, nearest.getLatitude(), nearest.getLongitude());

        // Update hospital availability
        nearest.setAvailableBeds(nearest.getAvailableBeds() - 1);
        hospitalRepository.save(nearest);

        // Save patient info
        patientRepository.save(patient);

        // ✅ Create Google Maps direction link
        String routeUrl = "https://www.google.com/maps/dir/?api=1"
                + "&origin=" + patient.getAddress().replace(" ", "+")
                + "&destination=" + nearest.getName().replace(" ", "+") + "," + nearest.getLocation().replace(" ", "+");

        // ✅ Prepare neat response
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("hospitalName", nearest.getName());
        response.put("hospitalLocation", nearest.getLocation());
        response.put("availableBedsLeft", nearest.getAvailableBeds());
        response.put("distanceKm", String.format("%.2f", distance));
        response.put("routeUrl", routeUrl);

        System.out.println("✅ Nearest Hospital: " + nearest.getName());
        return response;
    }
}
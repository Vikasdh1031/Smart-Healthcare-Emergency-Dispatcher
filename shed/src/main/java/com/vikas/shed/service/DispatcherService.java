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

            String url = "https://nominatim.openstreetmap.org/search?q="
                    + address.replace(" ", "+")
                    + "&format=json&limit=1";

            RestTemplate restTemplate = new RestTemplate();

            org.springframework.http.HttpHeaders headers =
                    new org.springframework.http.HttpHeaders();

            headers.set("User-Agent",
                    "SmartHealthcareDispatcher/1.0");

            org.springframework.http.HttpEntity<String> entity =
                    new org.springframework.http.HttpEntity<>(headers);

            org.springframework.http.ResponseEntity<List> response =
                    restTemplate.exchange(
                            url,
                            org.springframework.http.HttpMethod.GET,
                            entity,
                            List.class
                    );

            List<Map<String, Object>> locations = response.getBody();

            if (locations != null && !locations.isEmpty()) {

                Map<String, Object> location = locations.get(0);

                double lat =
                        Double.parseDouble(location.get("lat").toString());

                double lon =
                        Double.parseDouble(location.get("lon").toString());

                System.out.println("✅ Address = " + address);
                System.out.println("✅ Latitude = " + lat);
                System.out.println("✅ Longitude = " + lon);

                return new double[]{lat, lon};
            }

        } catch (Exception e) {
            e.printStackTrace();
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
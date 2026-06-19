package com.vikas.shed.controller;

import com.vikas.shed.model.Hospital;
import com.vikas.shed.repository.HospitalRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospitals")
@CrossOrigin
public class HospitalController {

    private final HospitalRepository hospitalRepository;

    public HospitalController(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    // List all hospitals
    @GetMapping
    public List<Hospital> getAll() {
        return hospitalRepository.findAll();
    }

    // Create single hospital
    @PostMapping
    public Hospital create(@RequestBody Hospital hospital) {
        // ensure null id for auto-generation
        hospital.setId(null);
        return hospitalRepository.save(hospital);
    }

    // 🔥 BULK upload (array of hospitals)
    @PostMapping("/bulk")
    public ResponseEntity<List<Hospital>> bulkCreate(@RequestBody List<Hospital> hospitals) {
        // make sure none of them try to override ids
        hospitals.forEach(h -> h.setId(null));
        List<Hospital> saved = hospitalRepository.saveAll(hospitals);
        return ResponseEntity.ok(saved);
    }

    // Update by id
    @PutMapping("/{id}")
    public ResponseEntity<Hospital> update(@PathVariable Long id, @RequestBody Hospital updated) {
        return hospitalRepository.findById(id)
                .map(existing -> {
                    existing.setName(updated.getName());
                    existing.setLocation(updated.getLocation());
                    existing.setCapacity(updated.getCapacity());
                    existing.setAvailableBeds(updated.getAvailableBeds());
                    existing.setLatitude(updated.getLatitude());
                    existing.setLongitude(updated.getLongitude());
                    return ResponseEntity.ok(hospitalRepository.save(existing));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete by id (optional)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!hospitalRepository.existsById(id)) return ResponseEntity.notFound().build();
        hospitalRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
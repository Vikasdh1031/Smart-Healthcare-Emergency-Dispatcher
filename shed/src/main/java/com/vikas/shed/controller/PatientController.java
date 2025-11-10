package com.vikas.shed.controller;

import com.vikas.shed.model.Patient;
import com.vikas.shed.service.PatientService;
import org.springframework.web.bind.annotation.*;

import java.util.Queue;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/add")
    public Patient addPatient(@RequestBody Patient patient) {
        return patientService.addPatient(patient);
    }

    @GetMapping("/next")
    public Patient getNextPatient() {
        return patientService.getNextPatient();
    }

    @GetMapping("/queue")
    public Queue<Patient> getAllPatients() {
        return patientService.getAllQueuedPatients();
    }
}

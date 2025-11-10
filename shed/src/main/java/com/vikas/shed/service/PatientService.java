package com.vikas.shed.service;

import com.vikas.shed.model.Patient;
import com.vikas.shed.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.PriorityQueue;
import java.util.Queue;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    // DSA Priority Queue to hold patients by severity
    private final Queue<Patient> patientQueue = new PriorityQueue<>();

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    // Add patient both to DB and Queue
    public Patient addPatient(Patient patient) {
        patientRepository.save(patient);
        patientQueue.offer(patient);
        return patient;
    }

    // Get next high-priority patient
    public Patient getNextPatient() {
        return patientQueue.poll();
    }

    // Show all queued patients (by priority)
    public Queue<Patient> getAllQueuedPatients() {
        return new PriorityQueue<>(patientQueue);
    }
}

package com.vikas.shed.controller;

import com.vikas.shed.model.Patient;
import com.vikas.shed.service.DispatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class DispatcherController {

    @Autowired
    private DispatcherService dispatcherService;

    @PostMapping("/dispatch")
    public Map<String, Object> assignHospital(@RequestBody Patient patient) {
        return dispatcherService.assignHospital(patient);
    }
}
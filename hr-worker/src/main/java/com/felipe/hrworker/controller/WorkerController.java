package com.felipe.hrworker.controller;

import com.felipe.hrworker.entity.Worker;
import com.felipe.hrworker.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/workers")
public class WorkerController {
    @Autowired
    private WorkerRepository workerRepository;
    @GetMapping
    public ResponseEntity<List<Worker>> findAll() {
        List<Worker> workerList = workerRepository.findAll();
        return ResponseEntity.ok(workerList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Worker> findById(@PathVariable Long id) {
        Optional<Worker> workerFound = workerRepository.findById(id);
        if (workerFound.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Worker worker = workerFound.get();
        return ResponseEntity.ok(worker);
    }

}

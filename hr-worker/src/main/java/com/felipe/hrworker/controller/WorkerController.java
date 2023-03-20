package com.felipe.hrworker.controller;

import com.felipe.hrworker.entity.Worker;
import com.felipe.hrworker.repository.WorkerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/workers")
@RefreshScope
public class WorkerController {
    private static final Logger logger = LoggerFactory.getLogger(WorkerController.class);

    private final Environment env;
    private final WorkerRepository workerRepository;

    public WorkerController(WorkerRepository workerRepository, Environment env) {
        this.workerRepository = workerRepository;
        this.env = env;
    }

    @GetMapping(value = "/configs")
    public ResponseEntity<Void> getConfig() {
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<List<Worker>> findAll() {
        List<Worker> workerList = workerRepository.findAll();
        return ResponseEntity.ok(workerList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Worker> findById(@PathVariable Long id) {
        logger.info("PORT = " + env.getProperty("local.server.port"));

        Optional<Worker> workerFound = workerRepository.findById(id);
        if (workerFound.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Worker worker = workerFound.get();
        return ResponseEntity.ok(worker);
    }

}

package com.felipe.hrworker.repository;

import com.felipe.hrworker.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
}

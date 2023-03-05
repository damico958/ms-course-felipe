package com.felipe.hrpayroll.service;

import com.felipe.hrpayroll.client.WorkerFeignClient;
import com.felipe.hrpayroll.entity.Payment;
import com.felipe.hrpayroll.entity.Worker;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final WorkerFeignClient workerFeignClient;

    public PaymentService(WorkerFeignClient workerFeignClient) {
        this.workerFeignClient = workerFeignClient;
    }

    public Payment getPayment(long workerId, int days) {
        Worker worker = workerFeignClient.findById(workerId).getBody();
        if (worker == null) {
            throw new RuntimeException("Worker not found");
        }
        String workerName = worker.getName();
        Double dailyIncome = worker.getDailyIncome();
        return new Payment(workerName, dailyIncome, days);
    }

}

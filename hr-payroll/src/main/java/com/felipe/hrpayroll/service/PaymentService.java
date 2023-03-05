package com.felipe.hrpayroll.service;

import com.felipe.hrpayroll.entity.Payment;
import com.felipe.hrpayroll.entity.Worker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {

    private RestTemplate restTemplate;
    @Value("${hr-worker.host}")
    private String workerHost;

    public PaymentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Payment getPayment(long workerId, int days) {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("id", "" + workerId);
        Worker worker = restTemplate.getForObject(workerHost + "/workers/{id}", Worker.class, uriVariables);
        if (worker == null) {
            throw new RuntimeException("Worker not found");
        }
        String workerName = worker.getName();
        Double dailyIncome = worker.getDailyIncome();
        return new Payment(workerName, dailyIncome, days);
    }

}

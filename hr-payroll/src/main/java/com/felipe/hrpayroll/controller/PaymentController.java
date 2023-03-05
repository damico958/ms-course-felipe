package com.felipe.hrpayroll.controller;

import com.felipe.hrpayroll.entity.Payment;
import com.felipe.hrpayroll.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/payments")
public class PaymentController {
    private static final Double DAILY_INCOME = 1000.0;

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @HystrixCommand(fallbackMethod = "getPaymentFallback")
    @GetMapping(value = "/{workerId}/days/{days}")
    public ResponseEntity<Payment> getPayment(@PathVariable Long workerId, @PathVariable Integer days) {
        Payment payment = paymentService.getPayment(workerId, days);
        return ResponseEntity.ok(payment);
    }

    public ResponseEntity<Payment> getPaymentFallback(Long workerId, Integer days) {
        Payment payment = new Payment("Felipe", DAILY_INCOME, days);
        return ResponseEntity.ok(payment);
    }

}

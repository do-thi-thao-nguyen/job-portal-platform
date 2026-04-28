package com.jobportal.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import com.jobportal.backend.entity.Company;
import com.jobportal.backend.entity.JobPackage;
import com.jobportal.backend.entity.PaymentTransaction;
import com.jobportal.backend.repository.PaymentTransactionRepository;
import com.jobportal.backend.service.MomoService;
import com.jobportal.backend.repository.CompanyRepository;
import com.jobportal.backend.repository.JobPackageRepository;
import com.jobportal.backend.repository.UserRepository;
import com.jobportal.backend.entity.User;
import java.util.Optional;


@RestController
@RequestMapping("/momo")
public class MomoController {

    @Autowired
    private MomoService momoService;

    @Autowired
    private PaymentTransactionRepository transactionRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private JobPackageRepository packageRepository;

    @Autowired
    private UserRepository userRepository;

    // =============================
    // CREATE PAYMENT
    // =============================
  @PostMapping("/create")
public Map<String, String> create(
        @RequestParam Long packageId,
        @RequestParam Long companyId
) throws Exception {

    System.out.println("========== CREATE PAYMENT ==========");
    System.out.println("packageId = " + packageId);
    System.out.println("companyId = " + companyId);

    // 🔥 GET USER
    String email = SecurityContextHolder.getContext()
            .getAuthentication()
            .getName();

    System.out.println("EMAIL = " + email);

    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

    System.out.println("USER ID = " + user.getId());

    // 🔥 GET COMPANY (THEO ID FE GỬI)
    Company company = companyRepository.findById(companyId)
            .orElseThrow(() -> new RuntimeException("Company not found"));

    System.out.println("COMPANY OWNER ID = " + company.getEmployer().getId());

    // 🔥 CHECK OWNER
    if (!company.getEmployer().getId().equals(user.getId())) {
        System.out.println("❌ NOT OWNER");
        throw new RuntimeException("Không phải công ty của bạn");
    }

    // 🔥 GET PACKAGE
    JobPackage jobPackage = packageRepository.findById(packageId)
            .orElseThrow(() -> new RuntimeException("Package not found"));

    // 🔥 CREATE ORDER
    String orderId = "order_" + System.currentTimeMillis();
    Long amount = jobPackage.getPrice(); // 🔥 dùng đúng giá package

    System.out.println("ORDER ID = " + orderId);
    System.out.println("AMOUNT = " + amount);

    // 🔥 SAVE TRANSACTION
    PaymentTransaction tx = new PaymentTransaction();
    tx.setOrderId(orderId);
    tx.setAmount(amount);
    tx.setStatus("PENDING");
    tx.setCompanyId(companyId);
    tx.setPackageId(packageId);

    transactionRepository.save(tx);

    // 🔥 CALL MOMO
    String payUrl = momoService.createPayment(
            amount,
            orderId,
            companyId,
            packageId
    );

    System.out.println("PAY URL = " + payUrl);

    return Map.of("payUrl", payUrl);
}
    // =============================
    // IPN CALLBACK
    // =============================
    @PostMapping("/ipn")
    public String handleMomo(@RequestBody Map<String, Object> body) {

        System.out.println("🔥 MOMO IPN HIT: " + body);

        String orderId = (String) body.get("orderId");
        Integer resultCode = (Integer) body.get("resultCode");
        String extraData = (String) body.get("extraData");

        PaymentTransaction tx = transactionRepository
                .findByOrderId(orderId)
                .orElseThrow();

        // 🔥 tránh xử lý lại nhiều lần
        if ("SUCCESS".equals(tx.getStatus())) {
            return "OK";
        }

        if (resultCode == 0) {

            tx.setStatus("SUCCESS");

            // =============================
            // 🔥 DECODE extraData
            // =============================
            String decoded = new String(
                    java.util.Base64.getDecoder().decode(extraData)
            );

            // companyId=1&packageId=2
            String[] parts = decoded.split("&");

            Long companyId = Long.parseLong(parts[0].split("=")[1]);
            Long packageId = Long.parseLong(parts[1].split("=")[1]);

            // =============================
            // 🔥 UPDATE COMPANY
            // =============================
            Company company = companyRepository.findById(companyId).orElseThrow();
            JobPackage jobPackage = packageRepository.findById(packageId).orElseThrow();

            company.setCurrentPackage(jobPackage); 
            company.setRemainingPosts(jobPackage.getPostLimit());

            companyRepository.save(company);

        } else {
            tx.setStatus("FAILED");
        }

        transactionRepository.save(tx);

        return "OK";
    }
   @PostMapping("/confirm")
    public String confirm(@RequestBody Map<String, String> body) {

        String orderId = body.get("orderId");

        System.out.println("CONFIRM ORDER ID = " + orderId);

        Optional<PaymentTransaction> optionalTx =
                transactionRepository.findByOrderId(orderId);

        if (optionalTx.isEmpty()) {
            System.out.println("❌ NOT FOUND ORDER");
            return "NOT_FOUND"; // tránh crash
        }

        PaymentTransaction tx = optionalTx.get();

        if ("SUCCESS".equals(tx.getStatus())) {
            return "OK";
        }

        tx.setStatus("SUCCESS");

        Company company = companyRepository
                .findById(tx.getCompanyId())
                .orElseThrow();

        JobPackage jobPackage = packageRepository
                .findById(tx.getPackageId())
                .orElseThrow();

        company.setCurrentPackage(jobPackage);
        company.setRemainingPosts(jobPackage.getPostLimit());

        companyRepository.save(company);
        transactionRepository.save(tx);

        return "OK";
    }
}
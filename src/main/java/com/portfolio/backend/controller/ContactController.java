package com.portfolio.backend.controller;

import com.portfolio.backend.model.FormSubmission;
import com.portfolio.backend.model.OtpRequest;
import com.portfolio.backend.model.VerifyOtpRequest;
import com.portfolio.backend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ContactController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-otp")
    public String sendOtp(@RequestBody OtpRequest request) {
        String otp = String.format("%06d", new Random().nextInt(900000) + 100000);

        String sql = "INSERT INTO user_otp (email, name, otp, is_verified) VALUES (?, ?, ?, FALSE) " +
                     "ON DUPLICATE KEY UPDATE name=?, otp=?, is_verified=FALSE";
        jdbcTemplate.update(sql, request.getEmail(), request.getName(), otp, request.getName(), otp);

        emailService.sendOtpEmail(request.getEmail(), otp);
        return "OTP sent successfully to " + request.getEmail();
    }

    @PostMapping("/verify-otp")
    public boolean verifyOtp(@RequestBody VerifyOtpRequest request) {
        String sql = "SELECT COUNT(*) FROM user_otp WHERE email = ? AND otp = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, request.getEmail(), request.getOtp());

        if (count != null && count > 0) {
            jdbcTemplate.update("UPDATE user_otp SET is_verified = TRUE WHERE email = ?", request.getEmail());
            return true;
        }
        return false;
    }

    @PostMapping("/submit-answers")
    public String submitAnswers(@RequestBody FormSubmission submission) {
        String verifyCheck = "SELECT is_verified FROM user_otp WHERE email = ?";
        Boolean isVerified = jdbcTemplate.queryForObject(verifyCheck, Boolean.class, submission.getEmail());

        if (Boolean.TRUE.equals(isVerified)) {
            String insertSql = "INSERT INTO user_responses (name, email, answer_one, answer_two) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(insertSql, submission.getName(), submission.getEmail(), submission.getAnswerOne(), submission.getAnswerTwo());

            emailService.sendNoReplyConfirmation(submission.getEmail(), submission.getName());
            return "Response saved and confirmation email sent!";
        }
        return "Error: Email was not verified via OTP.";
    }
}
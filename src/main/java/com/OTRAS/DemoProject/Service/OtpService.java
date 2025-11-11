package com.OTRAS.DemoProject.Service;
 
import java.time.LocalDateTime;

import java.util.Optional;

import java.util.Random;
 
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;

import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Service;
 
import com.OTRAS.DemoProject.Entity.OtpToken;

import com.OTRAS.DemoProject.Repository.OtpTokenRepository;
 
@Service

public class OtpService {
 
    @Autowired

    private JavaMailSender mailSender;
 
    @Autowired

    private OtpTokenRepository otpRepo;
 

    public String generateOtp() {

        return String.format("%06d", new Random().nextInt(999999));

    }
 

    public String sendOtp(String target, String type) {

        String otp = generateOtp();
 
        OtpToken token = new OtpToken();

        token.setTarget(target);

        token.setType(type);

        token.setOtp(otp);

        token.setUsed(false);

        token.setExpiresAt(LocalDateTime.now().plusMinutes(5));

        otpRepo.save(token);
 
        if ("EMAIL".equalsIgnoreCase(type)) {

            SimpleMailMessage message = new SimpleMailMessage();

            message.setTo(target);

            message.setSubject("Your OTP Code");

            message.setText("Your OTP is: " + otp + "\nIt expires in 5 minutes.");

            mailSender.send(message);

        } else if ("MOBILE".equalsIgnoreCase(type)) {

            System.out.println("SMS sent to " + target + ": " + otp);
            return "Your mobile OTP is : "+ otp;
        }
 
        return type + " OTP sent successfully!";

    }
 

    public String verifyOtp(String target, String otp, String type) {

        Optional<OtpToken> tokenOpt = otpRepo.findTopByTargetAndTypeOrderByExpiresAtDesc(target, type);
 
        if (tokenOpt.isEmpty()) return "Invalid OTP!";

        OtpToken token = tokenOpt.get();
 
        if (token.isUsed()) return "OTP already used! or Mobile given mobile number ";

        if (token.getExpiresAt().isBefore(LocalDateTime.now())) return "OTP expired!";

        if (!token.getOtp().equals(otp)) return "Invalid OTP!";
 
        token.setUsed(true);

        otpRepo.save(token);
 
        return type + " OTP verified successfully!";

    }
 

    public boolean isOtpVerified(String target, String type) {

        return otpRepo.existsByTargetAndTypeAndUsed(target, type, true);

    }

}

 
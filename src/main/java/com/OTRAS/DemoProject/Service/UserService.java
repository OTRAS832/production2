package com.OTRAS.DemoProject.Service;
 
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;
 
import com.OTRAS.DemoProject.Entity.User;

import com.OTRAS.DemoProject.Repository.OtpTokenRepository;

import com.OTRAS.DemoProject.Repository.UserRepository;
 
@Service

public class UserService {
 
    @Autowired

    private OtpService otpService;
 
    @Autowired

    private UserRepository userRepository;
 
    @Autowired

    private PasswordEncoder passwordEncoder;


    @Autowired

    private OtpTokenRepository otpRepo;
 
    public String sendEmailOtp(String email) {

        return otpService.sendOtp(email, "EMAIL");

    }
 
    public String sendMobileOtp(String mobile) {

        return otpService.sendOtp(mobile, "MOBILE");

    }
 
    public String registerUser(String email, String emailOtp, String mobile, String mobileOtp,

            String username, String password, String confirmPassword) {
 
			if (!password.equals(confirmPassword))

			return "Passwords do not match!";


			boolean isEmailVerified = otpRepo.existsByTargetAndOtpAndTypeAndUsedTrue(email, emailOtp, "EMAIL");

			if (!isEmailVerified)

			return "Email OTP not verified or invalid!";


			boolean isMobileVerified = otpRepo.existsByTargetAndOtpAndTypeAndUsedTrue(mobile, mobileOtp, "MOBILE");

			if (!isMobileVerified)

			return "Mobile OTP not verified or invalid!";

			if (userRepository.findByEmail(email).isPresent())

			return "User already registered with this email!";


			User user = new User();

			user.setEmail(email);

			user.setMobileNumber(mobile);

			user.setUsername(username);

			user.setPassword(passwordEncoder.encode(password));

			user.setCreatedAt(LocalDateTime.now());

			userRepository.save(user);

			return "Registration successful!";

			}
 
    public boolean isOtpVerified(String target, String type) {

        return otpService.isOtpVerified(target, type);

    }

}

 
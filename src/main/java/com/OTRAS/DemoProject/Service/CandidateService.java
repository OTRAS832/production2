package com.OTRAS.DemoProject.Service;
 
import com.OTRAS.DemoProject.DTO.SignupRequest;
import com.OTRAS.DemoProject.Entity.Candidate;
import com.OTRAS.DemoProject.Repository.CandidateRepository;
import com.OTRAS.DemoProject.Repository.OtpTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
@Service
@RequiredArgsConstructor
@Transactional
public class CandidateService {
 
private final CandidateRepository candidateRepo;
private final OtpTokenRepository otpRepo;
private final PasswordEncoder passwordEncoder;
 
public Candidate signup(SignupRequest req) {
 
	
	 if (!req.isTermsAccepted()) {
         throw new IllegalArgumentException("You must accept Terms and Conditions");
     }
 
     if (!req.getPassword().equals(req.getConfirmPassword())) {
         throw new IllegalArgumentException("Passwords do not match");
     }
     
     if (!otpRepo.existsByTargetAndOtpAndTypeAndUsedTrue(req.getEmail(), req.getEmailOtp(), "EMAIL")) {
         throw new IllegalArgumentException("Invalid or unused Email OTP");
     }
     if (!otpRepo.existsByTargetAndOtpAndTypeAndUsedTrue(req.getMobile(), req.getMobileOtp(), "MOBILE")) {
         throw new IllegalArgumentException("Invalid or unused Mobile OTP");
     }
 
     if (candidateRepo.existsByEmail(req.getEmail())) {
         throw new IllegalStateException("Email already registered");
     }
     if (candidateRepo.existsByMobile(req.getMobile())) {
         throw new IllegalStateException("Mobile already registered");
     }
 
     Candidate candidate = Candidate.builder()
         .fullName(req.getFullName())
         .dateOfBirth(req.getDateOfBirth())
         .gender(req.getGender())
         .fatherName(req.getFatherName())
         .motherName(req.getMotherName())
         .nationality(req.getNationality())
         .email(req.getEmail())
         .emailVerified(true)
         .mobile(req.getMobile())
         .mobileVerified(true)
         .qualification(req.getQualification())
         .interest(req.getInterest())
         .password(passwordEncoder.encode(req.getPassword()))
         .termsAccepted(req.isTermsAccepted())
         .build();
 
     return candidateRepo.save(candidate);
}
}
 
 
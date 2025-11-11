package com.OTRAS.DemoProject.Repository;
 
import java.util.Optional;
 
import org.springframework.data.jpa.repository.JpaRepository;
 
import com.OTRAS.DemoProject.Entity.OtpToken;
 
public interface OtpTokenRepository extends JpaRepository<OtpToken, Long> {
 
    Optional<OtpToken> findByTargetAndOtp(String target, String otp);
    
    boolean existsByTargetAndTypeAndUsed(String target, String type, boolean used);
 
    OtpToken findTopByTargetAndOtp(String target, String otp);
 
	Optional<OtpToken> findTopByTargetAndTypeOrderByExpiresAtDesc(String target, String type);
 
	boolean existsByTargetAndOtpAndTypeAndUsedTrue(String email, String emailOtp, String string);
 
 
}
 
 
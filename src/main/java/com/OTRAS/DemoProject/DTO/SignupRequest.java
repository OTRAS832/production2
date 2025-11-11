package com.OTRAS.DemoProject.DTO;
 
import lombok.Data;
import java.time.LocalDate;
 
@Data
public class SignupRequest {
	
    private String fullName;
    private LocalDate dateOfBirth;
    private String gender;          
    private String fatherName;
    private String motherName;
    private String nationality = "Indian";
    private String email;
    private String mobile;
    private String emailOtp;
    private String mobileOtp;
    private String qualification;
    private String interest;
    private String password;
    private String confirmPassword;
 
    private boolean termsAccepted;
 
    public boolean isPasswordMatching() {
        return password != null && password.equals(confirmPassword);
    }
}
 
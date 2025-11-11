package com.OTRAS.DemoProject.Service;
 
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
 
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
 
import com.OTRAS.DemoProject.Config.DigiLockerConfig;
import com.OTRAS.DemoProject.DTO.DigiLockerDocumentDTO;
import com.OTRAS.DemoProject.Repository.CandidateProfileRepository;
import com.OTRAS.DemoProject.Repository.CandidateRepository;
 
import lombok.RequiredArgsConstructor;
 
@Service
@RequiredArgsConstructor
public class DigiLockerService {
 
    private final DigiLockerConfig digilockerConfig;
    private final CandidateRepository candidateRepository;
    private final CandidateProfileRepository candidateProfileRepository;
 
    private final Map<String, String> otpStorage = new ConcurrentHashMap<>();
    private final Map<String, Long> otpTimestamp = new ConcurrentHashMap<>();
    private static final long OTP_VALIDITY_DURATION = 5 * 60 * 1000; // 5 minutes
 
    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); 
        return String.valueOf(otp);
    }
    
    public ResponseEntity<?> sendAadhaarOtp(String aadhaarNumber) {
        try {
            if (aadhaarNumber == null || !aadhaarNumber.matches("\\d{12}")) {
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "FAILED",
                        "message", "Invalid Aadhaar number. Must be 12 digits."
                ));
            }
 
            String otp = generateOtp();
            String txnId = "TXN_" + System.currentTimeMillis() + "_" + aadhaarNumber.substring(8);
            
            otpStorage.put(txnId, otp);
            otpTimestamp.put(txnId, System.currentTimeMillis());
            
            System.out.println(".............Debuging for OTP generation,..........");
            System.out.println("Aadhaar: " + aadhaarNumber);
            System.out.println("TXN ID: " + txnId);
            System.out.println("Generated OTP: " + otp);
            System.out.println("...................................................");
            
            return ResponseEntity.ok(Map.of(
                    "txnId", txnId,
                    "message", "OTP sent successfully to mobile linked with Aadhaar",
                    "status", "SUCCESS",
                    "aadhaarLast4", aadhaarNumber.substring(8),
                    "otp", otp
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "ERROR",
                    "message", "Failed to send OTP: " + e.getMessage()
            ));
        }
    }
 
    public ResponseEntity<?> verifyAadhaarOtp(String txnId, String otp) {
        try {
            String storedOtp = otpStorage.get(txnId);
            Long timestamp = otpTimestamp.get(txnId);
            
            if (storedOtp == null || timestamp == null) {
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "FAILED",
                        "message", "Invalid transaction ID or OTP expired."
                ));
            }
            
            if (System.currentTimeMillis() - timestamp > OTP_VALIDITY_DURATION) {
                otpStorage.remove(txnId);
                otpTimestamp.remove(txnId);
                
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "FAILED",
                        "message", "OTP has expired. Please request a new OTP."
                ));
            }
            
            if (storedOtp.equals(otp)) {
                otpStorage.remove(txnId);
                otpTimestamp.remove(txnId);
                
                return ResponseEntity.ok(Map.of(
                        "txnId", txnId,
                        "status", "VERIFIED",
                        "message", "Aadhaar OTP verified successfully",
                        "digilockerRedirect", true
                ));
            } else {
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "FAILED",
                        "message", "Invalid OTP. Please try again."
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "ERROR",
                    "message", "OTP verification failed: " + e.getMessage()
            ));
        }
    }
    public void cleanupExpiredOtps() {
        long currentTime = System.currentTimeMillis();
        otpTimestamp.entrySet().removeIf(entry -> {
            if (currentTime - entry.getValue() > OTP_VALIDITY_DURATION) {
                otpStorage.remove(entry.getKey());
                return true;
            }
            return false;
        });
    }
    
    public String generateAadhaarVerificationUrl(Long candidateId, String candidateName, String aadhaarNumber) {
        try {
            String baseUrl = "http://localhost:8068/aadhaar-verification";
            
            String url = baseUrl + "?candidateId=" + candidateId
                       + "&candidateName=" + URLEncoder.encode(candidateName != null ? candidateName : "User", "UTF-8")
                       + "&aadhaarNumber=" + URLEncoder.encode(aadhaarNumber != null ? aadhaarNumber : "000000000000", "UTF-8");
            
            System.out.println("Generated Aadhaar Verification URL: " + url);
            return url;
            
        } catch (Exception e) {
            e.printStackTrace();
            return "http://localhost:8068/aadhaar-verification?candidateId=" + candidateId;
        }
    }
//    public String generateAadhaarVerificationUrl(Long candidateId) {
//        return "http://localhost:8068/aadhaar-verification?candidateId=" + candidateId;
//    }
 
    public String generateDigiLockerRedirectUrl(Long candidateId) {
        try {
            String digilockerUrl = "http://localhost:8068/api/digilocker/digilocker-page?candidateId=" + candidateId;
            
            System.out.println("Generated DigiLocker Redirect URL: " + digilockerUrl);
            return digilockerUrl;
            
        } catch (Exception e) {
            e.printStackTrace();
            return "http://localhost:8068/api/digilocker/digilocker-page?candidateId=" + candidateId;
        }
    }
    public String exchangeCodeForToken(String code) {
        try {
            return "sandbox_access_token_" + System.currentTimeMillis();
        } catch (Exception e) {
            throw new RuntimeException("Failed to exchange code for token: " + e.getMessage());
        }
    }
 
    public List<String> fetchDocumentsFromDigiLocker(String accessToken) {
        try {
            List<String> mockDocuments = Arrays.asList(
                    "https://sandbox.digilocker.gov.in/documents/10th_certificate_" + System.currentTimeMillis() + ".pdf",
                    "https://sandbox.digilocker.gov.in/documents/12th_certificate_" + System.currentTimeMillis() + ".pdf",
                    "https://sandbox.digilocker.gov.in/documents/degree_certificate_" + System.currentTimeMillis() + ".pdf"
            );
            
            return mockDocuments;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch documents from DigiLocker: " + e.getMessage());
        }
    }
 
    public Map<String, String> fetchDetailedDocumentsFromDigiLocker(String accessToken) {
        try {
            Map<String, String> documents = new HashMap<>();
            String timestamp = String.valueOf(System.currentTimeMillis());
            
            documents.put("tenthCertificate", "https://sandbox.digilocker.gov.in/documents/10th_certificate_" + timestamp + ".pdf");
            documents.put("intermediateCertificate", "https://sandbox.digilocker.gov.in/documents/12th_certificate_" + timestamp + ".pdf");
            documents.put("degreeCertificate", "https://sandbox.digilocker.gov.in/documents/degree_certificate_" + timestamp + ".pdf");
            documents.put("btechCertificate", "https://sandbox.digilocker.gov.in/documents/btech_certificate_" + timestamp + ".pdf");
            documents.put("diplomaCertificate", "https://sandbox.digilocker.gov.in/documents/diploma_certificate_" + timestamp + ".pdf");
            documents.put("postGraduationCertificate", "https://sandbox.digilocker.gov.in/documents/pg_certificate_" + timestamp + ".pdf");
            
            return documents;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch detailed documents from DigiLocker: " + e.getMessage());
        }
    }
 
    public DigiLockerDocumentDTO convertToDocumentDTO(Map<String, String> documents) {
        DigiLockerDocumentDTO dto = new DigiLockerDocumentDTO();
        dto.setTenthCertificate(documents.get("tenthCertificate"));
        dto.setIntermediateCertificate(documents.get("intermediateCertificate"));
        dto.setDegreeCertificate(documents.get("degreeCertificate"));
        dto.setBtechCertificate(documents.get("btechCertificate"));
        dto.setDiplomaCertificate(documents.get("diplomaCertificate"));
        dto.setPostGraduationCeritifcate(documents.get("postGraduationCertificate"));
        return dto;
    }
}
 
 
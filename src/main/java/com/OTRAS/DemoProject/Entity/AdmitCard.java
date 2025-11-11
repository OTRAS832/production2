package com.OTRAS.DemoProject.Entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdmitCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String examRollNo;       
    private String candidateName;    
    private String fatherName;
    private String gender;
    private LocalDate dateOfBirth;

    private String examCenter;       
    private String collegeName = "Vignan College";      
    private String universityName = "Vignan University";  
    private String centerAddress="Guntur-Tenali Rd, Vadlamudi,Andhra Pradesh 522213";
    
    @OneToOne
    @JoinColumn(name = "payment_id", nullable = false, unique = true)
    private PaymentSuccesfullData paymentSuccesfullData;
    private String otrasId;
}

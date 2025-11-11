package com.OTRAS.DemoProject.Entity;
 
import jakarta.persistence.*;

import lombok.*;
 
@Entity

@Getter

@Setter

@AllArgsConstructor

@NoArgsConstructor

@Builder

public class ExamAssignment {
 
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
 
    private String rollNumber;       

    private Long jobPostId;        

    private String setName;         

    private Long questionPaperId;    

    private boolean assigned;      
 
    private String examStatus; 
    
    @ManyToOne

    @JoinColumn(name = "payment_id")

    private PaymentSuccesfullData paymentSuccesfullData;

}

 
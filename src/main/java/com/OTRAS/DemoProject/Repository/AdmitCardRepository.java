package com.OTRAS.DemoProject.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.OTRAS.DemoProject.Entity.AdmitCard;
import com.OTRAS.DemoProject.Entity.PaymentSuccesfullData;

@Repository
public interface AdmitCardRepository extends JpaRepository<AdmitCard, Long> {

	Optional< AdmitCard> findByPaymentSuccesfullData(PaymentSuccesfullData payment);
}

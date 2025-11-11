package com.OTRAS.DemoProject.Repository;
 
import java.util.Optional;
 
import org.springframework.data.jpa.repository.JpaRepository;
 
import com.OTRAS.DemoProject.Entity.User;
 
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByMobileNumber(String mobileNumber);

	boolean existsByEmail(String email);

}

 
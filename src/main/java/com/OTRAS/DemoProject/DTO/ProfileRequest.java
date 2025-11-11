package com.OTRAS.DemoProject.DTO;
 
import lombok.Data;
import java.time.Year;
 
@Data
public class ProfileRequest {
private String matricBoard;
private String matricRollNumber;
private Year matricYearOfPassing;
private String digilockerCode; 
}
 
package com.OTRAS.DemoProject.Entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VacancyDetails {

	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	   
	   private String postName;
	   private int total;
	   private int age;
	   
	   @ManyToOne
	   @JoinColumn(name="jobPostId")
	   private JobPost jobPost;
	   
	   @OneToMany(mappedBy = "vacancyDetails",fetch = FetchType.LAZY,orphanRemoval = true,cascade = CascadeType.ALL)
	   private List<Religion>religions;
}

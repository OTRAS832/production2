package com.OTRAS.DemoProject.DTO;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class JobApplicationRequest {
	
    private String otrId;
    private Long postId;
    private String center1;
    private String center2;
    private String center3;

    private MultipartFile livePhoto;
    private MultipartFile signature;
}
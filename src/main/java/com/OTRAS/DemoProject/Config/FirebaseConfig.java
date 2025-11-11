//package com.OTRAS.DemoProject.Config;
//
//import java.io.IOException;
//import java.io.InputStream;
//
//import javax.annotation.PostConstruct;
//
//import org.springframework.context.annotation.Configuration;
//
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseOptions;
//
//@Configuration
//public class FirebaseConfig {
//
//	@PostConstruct
//	public void initialize() throws IOException {
//	    InputStream serviceAccount = getClass().getClassLoader()
//	        .getResourceAsStream("otras-63416-firebase-adminsdk-fbsvc-e2e8dd3915.json");
//
//	    FirebaseOptions options = FirebaseOptions.builder()
//	        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//	        .build();
//
//	    if (FirebaseApp.getApps().isEmpty()) {
//	        FirebaseApp.initializeApp(options);
//	    }
//	}
//
//   
//}

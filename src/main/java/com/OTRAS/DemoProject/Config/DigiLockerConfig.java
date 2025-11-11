package com.OTRAS.DemoProject.Config;
 
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Configuration;
 
@Configuration

public class DigiLockerConfig {
 
    @Value("${digilocker.client.id}")

    public String CLIENT_ID;
 
    @Value("${digilocker.client.secret}")

    public String CLIENT_SECRET;
 
    @Value("${digilocker.redirect.uri}")

    public String REDIRECT_URI;
 
    @Value("${digilocker.code.verifier}")

    public String CODE_VERIFIER;
 
    public final String BASE_URL = "https://sandbox.digilocker.gov.in/rest/2.0";

    public final String AUTH_BASE_URL = "https://sandbox.digilocker.gov.in/public/oauth2";

    public final String API_BASE_URL = "https://sandbox.digilocker.gov.in/rest/2.0";

    @Value("${uidai.api.key:default_key}")

    public String UIDAI_API_KEY;

    @Value("${uidai.api.secret:default_secret}")

    public String UIDAI_API_SECRET;

    @Value("${uidai.auth.url:https://api.sandbox.co.in/authenticate}")

    public String UIDAI_AUTH_URL;

}
 
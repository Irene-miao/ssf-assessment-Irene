package ibf2022.batch2.ssf.frontcontroller.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ibf2022.batch2.ssf.frontcontroller.model.Login;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@Service
public class AuthenticationService {


	@Value("${spring.data.api.url}")
    private String authURL;
	

	// TODO: Task 2
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write the authentication method in here
	public String authenticate(Login login) throws Exception {
		String json = login.toJSON().toString();
		
		String url = UriComponentsBuilder
        .fromUriString(authURL)
        .toUriString();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;
       
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
       
       
        HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);
        resp = template.exchange(url, HttpMethod.POST, requestEntity, String.class);
        if (null == resp) {
            JsonObject error = Json.createObjectBuilder()
       .add("message", "Not authenticated")
       .build();
       
       return error.toString();
        }
      
     
       return resp.getBody().toString();


	}

	// TODO: Task 3
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write an implementation to disable a user account for 30 mins
	public void disableUser(String username) {
	}

	// TODO: Task 5
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write an implementation to check if a given user's login has been disabled
	public boolean isLocked(String username) {
		return false;
	}
}

package ibf2022.batch2.ssf.frontcontroller.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ibf2022.batch2.ssf.frontcontroller.model.Login;
import ibf2022.batch2.ssf.frontcontroller.respositories.AuthenticationRepository;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;



@RestController
@RequestMapping(path="/api", 
    produces = "application/json", consumes = "application/json")
public class ProtectedController {

	@Autowired
	private AuthenticationRepository authRepo;
	

	@Value("${spring.data.chuk.api.url}")
    private String chukURL;
	
	@PostMapping(path="/authenticate", produces = "application/json")
	public ResponseEntity<String> getAuth(@RequestBody String json) throws IOException{
		
		
		String url = UriComponentsBuilder
        .fromUriString(chukURL)
        .toUriString();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;
       
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
       
       
        HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);
        resp = template.exchange(url, HttpMethod.POST, requestEntity, String.class);
		JsonObjectBuilder obj = Json.createObjectBuilder();
		JsonObject error = null;

		if (null == resp && resp.getStatusCode().isSameCodeAs(HttpStatus.BAD_REQUEST)) {
        error = obj.add("message", "Invalid payload").build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.toString());
		}else if  (null == resp && resp.getStatusCode().isSameCodeAs(HttpStatus.UNAUTHORIZED)) {
		error = obj.add("message", "Incorrect username and/or password").build();
	   
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.toString());
	}

		System.out.println(resp.getBody().toString());
		Login login = new Login();
		login.jsonStrToObj(json);
		authRepo.saveLogin(login.getId(), json);
			
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(json);
        
		
	}
}

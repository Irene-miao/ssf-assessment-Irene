package ibf2022.batch2.ssf.frontcontroller.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.security.SecureRandom;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;



public class Login implements Serializable{
    
    
    @NotNull(message="Name cannot be null")
    @Size(min=2, max=64, message="Name must be at least 2 characters")
    private String username;

    @NotNull(message="Password cannot be null")
    @Size(min=2, max=64, message="Password must be at least 2 characters")
    private String password;
    
    private String id;
    
   private String auth;

    public Login() {
        this.id = generateId(8);
    }



    public Login(String username, String password, String id) {
        this.username = username;
        this.password = password;
        this.id = generateId(8);
    }

   

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    @Override
    public String toString() {
        return "Login [username=" + username + ", password=" + password + ", id=" + id + "]";
    }


    public synchronized String generateId(int maxChars){
        SecureRandom sr= new SecureRandom();
        StringBuilder strbuilder = new StringBuilder();
        while(strbuilder.length() < maxChars){
            strbuilder.append(Integer.toHexString(sr.nextInt()));
        }

        return strbuilder.toString().substring(0, maxChars);
    }
    
    public JsonObjectBuilder toJSON(){
        return Json.createObjectBuilder()
            .add("username", this.getUsername())
            .add("password", this.getPassword());
    }

     // convert one json string to one object
     public Login jsonStrToObj(String json) throws IOException {
        Login login = new Login();
        try(InputStream is = new ByteArrayInputStream(json.getBytes())){
            JsonReader r = Json.createReader(is);
            JsonObject o = r.readObject();

            System.out.println(o.toString());
         
            String id = o.getString("id");
            String auth= o.getString("Authenticated");

            login.setId(id);
          login.setAuth(auth);;
       
        }
        return login;
    }



    public String getId() {
        return id;
    }



    public void setId(String id) {
        this.id = id;
    }



    public String getAuth() {
        return auth;
    }



    public void setAuth(String auth) {
        this.auth = auth;
    }
    
    
}

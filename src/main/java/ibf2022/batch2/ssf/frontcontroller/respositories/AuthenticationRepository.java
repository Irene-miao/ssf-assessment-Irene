package ibf2022.batch2.ssf.frontcontroller.respositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AuthenticationRepository {


	private static final String LOGIN = "login";
	// TODO Task 5
	// Use this class to implement CRUD operations on Redis
	@Autowired 
	@Qualifier("auth")
    RedisTemplate<String, String> redisTemplate;

	public void saveLogin(String id, final String json) {
		redisTemplate.opsForHash().put(LOGIN, id, json);
		
	}

	public String getById(String id){
        String json = (String) redisTemplate.opsForHash().get(LOGIN, id);
        return json;
    }

	public void delete(String id){
		redisTemplate.opsForHash().delete(LOGIN, id);
	}


}

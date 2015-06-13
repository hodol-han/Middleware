package kr.ac.ajou.lazybones.restcontrollers;



import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
//This Component will send the information about this Service Provider when SP server initiated.
public class ReservationRESTController {
	
	private String regAddress = "http://210.107.197.150:8080/"//PA(Personal Assisstant) server address
			+ "PAmanager/webresources/RegisterServiceProvider/{SPname}/{URI}";//The Personal Assisstant system server address.
	
	private final String SPname = "WasherMan";//the name for registration
	private final String Uri = "www.naver.com";//the name for retrieving the services in this Service Provider.
	//uri should be where PA can get services from uri.
	
	private RestTemplate restTempleate = new RestTemplate();
	
	@PostConstruct
	public void RegisterServiceProvider(){
		
		//Register Service Provider when initiation.(Include SP name, and the place where can get the services from uri.)
		String result = restTempleate.getForObject(regAddress, String.class, SPname, Uri);
		
		
	}
		
	
}


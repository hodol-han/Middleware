package kr.ac.ajou.lazybones.restcontrollers;



import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class ReservationRESTController {
	
	private final String regAddress = "http://210.107.197.44:8080/"
			+ "PAmanager/webresources/RegisterServiceProvider/{SPname}/{URI}";//The Personal Assisstant system server address.
	
	private final String SPname = "WasherMan";//the name for registration
	private final String Uri = "";//the name for retrieving the services in this Service Provider.
	
	private RestTemplate restTempleate = new RestTemplate();
	
	@PostConstruct
	public void RegisterServiceProvider(){
		
		restTempleate.getForObject(regAddress, void.class, SPname, Uri);
		
	}
		
	
}


package kr.ac.ajou.lazybones.restcontrollers;



import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import kr.ac.ajou.lazybones.DataStructure.ServiceForPA;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;


@Controller
//This Component will send the information about this Service Provider when SP server initiated.
public class ReservationRESTController {
	
	private String regAddress = "http://210.107.197.150:8080/"//PA(Personal Assisstant) server address
			+ "PAmanager/webresources/RegisterServiceProvider/{SPname}/{URI}";//The Personal Assisstant system server address.
	
	private final String SPname = "WasherMan";//the name for registration
	private final String Uri = "GetServicesRegisterEntry";//the name for retrieving the services in this Service Provider.
	//uri should be where PA can get services from uri.
	
	private RestTemplate restTempleate = new RestTemplate();
	
	@PostConstruct
	public void RegisterServiceProvider(){
		
		//Register Service Provider when initiation.(Include SP name, and the place where can get the services from uri.)
		String result = restTempleate.getForObject(regAddress, String.class, SPname, Uri);
			
	}
	
	@RequestMapping(value = "/GetServicesRegisterEntry/{SPid}", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ServiceForPA> getServices(@PathVariable("SPid") Long id)
	{
		
		
		return null;
		
	}
	
		
	
}


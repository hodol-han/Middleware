package kr.ac.ajou.lazybones.restcontrollers;



import java.net.URI;
import java.util.HashMap;
import java.util.List;



import java.util.Map;

import javax.annotation.PostConstruct;

import kr.ac.ajou.lazybones.DataStructure.ServiceForPA;
//import kr.ac.ajou.lazybones.DataStructure.ServiceProviderForPA;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;


@Component
//This Component will send the information about this Service Provider when SP server initiated.
public class ReservationRESTController {
	
	//private String getregAddress = "http://localhost:8080/"//PA(Personal Assisstant) server address
			//+ "PAmanager/webresources/RegisterServiceProvider/{SPname}/{URI}";//The Personal Assisstant system server address.
	//post uri
	private String postregAddress = "http://localhost:8080/PAmanager/webresources/RegisterServiceProviderPost";
	
	private final String SPname = "WasherManPost";//the name for registration
	private final String Uri = "GetServicesRegisterEntry";//the name for retrieving the services in this Service Provider.
	//uri should be where PA can get services from uri.
	
	//private Map<String, String> map;
	
	
	private RestTemplate restTempleate = new RestTemplate();
	
	@PostConstruct
	public void RegisterServiceProvider(){
		
		
//		//map = new HashMap<String, String>();
//		//map.put("SPname", "WasherManPost");
//		//map.put("Uri","GetServicesRegisterEntry1" );
//		//Register Service Provider when initiation.
//		//(Include SP name, and the place where can get the services from uri.)
//		//String result = restTempleate.getForObject(getregAddress, String.class, map);
//		
//		
//		//Post method
//		ServiceProviderForPA sp = new ServiceProviderForPA(SPname, Uri);
//		URI result1 = restTempleate.postForLocation(postregAddress, sp);
//		System.out.println(result1);
//		
		
	}
	
	/*@RequestMapping(value = "/GetServicesRegisterEntry/{SPid}", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ServiceForPA> getServices(@PathVariable("SPid") Long id)
	{
		
		
		return null;
		
	}*/
	
		
	
}


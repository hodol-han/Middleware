package kr.ac.ajou.lazybones.restcontrollers;



import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;




import javax.annotation.PostConstruct;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PathVariable;


import org.springframework.web.client.RestTemplate;




@Component
//This Component will send the information about this Service Provider when SP server initiated.
public class ReservationRESTController {
	
	private String getregAddress = "http://localhost:8080/"//PA(Personal Assisstant) server address
			+ "PAmanager/webresources/RegisterServiceProvider/{SPname}/{ServiceName}/{RedirectionAddress}";//The Personal Assisstant system server address.
	
	//the name for registration
	private final String SPname = "WasherMan";
	//the name for retrieving the services in this Service Provider.
	//uri should be where PA can get services from uri.
	private final String ServiceEntryUri = "localhost%3A8090%2FWasherMan%2FGetServicesRegisterEntry";
	
		
	
	private RestTemplate restTempleate = new RestTemplate();
	
	@PostConstruct
	//This method is used for registering it to PA System.
	public void RegisterServiceProvider(){
		
		//Register Service Provider when initiation.
		//(Include SP name, and the place where can get the services from uri.)
		System.out.println("I am in!");
		String result = restTempleate.getForObject(getregAddress, String.class, SPname, "Reservation", ServiceEntryUri);
		
		System.out.println(result);
		
		if(!result.equals("OK"))
		{
			System.out.println("Registeration is failed");
		}
		
	}
	

		
	
}


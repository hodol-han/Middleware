package kr.ac.ajou.lazybones.DataStructure;

import java.io.Serializable;
import java.util.List;

public class ServiceForPA implements Serializable{
	
	private String name;
	private String redirectionAddress;
	private String serviceIntroduction;
	private Long ownerid;
	
	 
	public ServiceForPA(){
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRedirectionAddress() {
		return redirectionAddress;
	}
	public void setRedirectionAddress(String redirectionAddress) {
		this.redirectionAddress = redirectionAddress;
	}
	public String getServiceIntroduction() {
		return serviceIntroduction;
	}
	public void setServiceIntroduction(String serviceIntroduction) {
		this.serviceIntroduction = serviceIntroduction;
	}
	public Long getOwnerid() {
		return ownerid;
	}
	public void setOwnerid(Long ownerid) {
		this.ownerid = ownerid;
	}
	
	
	
	
	
	
	
	
	
	

}

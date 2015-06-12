package kr.ac.ajou.lazybones.DataStructure;

import java.io.Serializable;
import java.util.List;

public class RegisterSPInfo implements Serializable{
	
	private String SPname;
	private List<String> ServiceNames[];
	private List<String> ServiceURI[];
	
	public String getSPname() {
		return SPname;
	}
	public void setSPname(String sPname) {
		SPname = sPname;
	}
	public List<String>[] getServiceNames() {
		return ServiceNames;
	}
	public void setServiceNames(List<String>[] serviceNames) {
		ServiceNames = serviceNames;
	}
	public List<String>[] getServiceURI() {
		return ServiceURI;
	}
	public void setServiceURI(List<String>[] serviceURI) {
		ServiceURI = serviceURI;
	}
	
	
	
	

}

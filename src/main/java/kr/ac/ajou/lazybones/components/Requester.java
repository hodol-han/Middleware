package kr.ac.ajou.lazybones.components;

public interface Requester {
	
	public String query(String string);
	public boolean connect();
	public boolean disconnect();

}

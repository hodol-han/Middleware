package kr.ac.ajou.lazybones.connection;

public interface Requester {
	
	public String getSn();
	public String query(String string);
	public boolean connect();
	public boolean disconnect();
	public boolean isConnected();

}

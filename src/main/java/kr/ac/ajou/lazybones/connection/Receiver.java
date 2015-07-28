package kr.ac.ajou.lazybones.connection;

public interface Receiver {
	
	public String getSn();
	public String receive();
	public boolean connect();
	public boolean disconnect();
	public boolean isConnected();

}

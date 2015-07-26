package kr.ac.ajou.lazybones.components;

import java.net.Socket;

public class TcpConnection implements Connection {

	Socket socket;
	
	public TcpConnection(Socket socket){
		this.socket = socket;
	}
	
	
	@Override
	public String command(String string) {
		
		
		return null;
	}

}

package kr.ac.ajou.lazybones.components;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TcpRequester implements Requester {

	private Socket socket;
	private BufferedReader reader;
	private BufferedWriter writer;
	
	public TcpRequester(Socket socket) throws IOException{
		this.socket = socket;
		this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	}
	
	
	@Override
	public String query(String string) {
		
		try {
			this.writer.write(string);
			return this.reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public boolean connect() {
		// TODO Auto-generated method stub
		
		return false;
	}


	@Override
	public boolean disconnect() {
		// TODO Auto-generated method stub
		try {
			if(!socket.isClosed())
				socket.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}

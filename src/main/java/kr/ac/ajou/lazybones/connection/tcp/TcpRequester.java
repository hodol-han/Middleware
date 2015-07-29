package kr.ac.ajou.lazybones.connection.tcp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.CharBuffer;

import kr.ac.ajou.lazybones.connection.Requester;

public class TcpRequester implements Requester {

	private String serialNumber;
	private Socket socket;
	private BufferedReader reader;
	private BufferedWriter writer;

	public TcpRequester(String serialNumber, Socket socket) throws IOException {
		
		this.serialNumber = serialNumber;
		this.socket = socket;
		this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	}

	@Override
	public String query(String string) {

		try {
			this.writer.write(string);
			this.writer.flush();

			String result = this.reader.readLine();
			
			System.out.println("Result: " + result);
			return result;

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
			if (!socket.isClosed())
				socket.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean isConnected() {
		return socket.isConnected();
	}

	@Override
	public String getSn() {
		
		return this.serialNumber;
	}

}

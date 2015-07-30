package kr.ac.ajou.lazybones.connection.tcp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import kr.ac.ajou.lazybones.connection.Receiver;

public class TcpReceiver implements Receiver {

	private String serialNumber;
	private Socket socket;
	private BufferedReader reader;

	public TcpReceiver(String serialNumber, Socket socket) throws IOException {
		this.serialNumber = serialNumber;
		this.socket = socket;
		this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	@Override
	public String receive() {
		try {
			return this.reader.readLine();
		} catch (IOException e) {
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
		return !socket.isClosed();		
	}

	@Override
	public String getSn() {
		// TODO Auto-generated method stub
		return this.serialNumber;
	}

}

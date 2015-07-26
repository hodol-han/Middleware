package kr.ac.ajou.lazybones.components;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TcpConnectionAccepter {

	private TcpListener listener;
	
	@Autowired
	NodeManager nodemanager;
	
	
	private class TcpListener extends Thread {

		private ServerSocket socket;
		
		public TcpListener(ServerSocket socket){
			this.socket = socket;
		}
		
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("LOL! I'm running!");
			
			
			while(!this.isInterrupted()){
			try {
				System.out.println("Waiting for connection.");				
				Socket clientSocket = socket.accept();

				Node node = new Node(new TcpConnection(clientSocket));
				
				System.out.println("Inet Addr: "+clientSocket.getInetAddress());
				System.out.println("Port Num: "+clientSocket.getPort());				

				System.out.println("Local Inet Addr: "+clientSocket.getLocalAddress());
				System.out.println("Local Port Num: "+clientSocket.getLocalPort());				

				nodemanager.addLiveNode(node);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
			try {
				if(!socket.isClosed())
					socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	@PostConstruct
	public void initialize() throws IOException{
		listener = new TcpListener(new ServerSocket(5555));
		listener.start();
	}
	
	@PreDestroy
	public void terminate(){
		listener.interrupt();
		
	}

}

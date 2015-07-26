package kr.ac.ajou.lazybones.components;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.ajou.lazybones.managers.NodeManager;
import kr.ac.ajou.lazybones.managers.ReceiverThreadPool;
import kr.ac.ajou.lazybones.templates.NodeInformation;

@Component
public class TcpNodeConnectionAccepter {

	private TcpListener listener;

	@Autowired
	NodeManager nodeManager;	

	private class TcpListener extends Thread {

		private ServerSocket controlSocket;
		private ServerSocket dataSocket;

		public TcpListener(ServerSocket controlSocket, ServerSocket dataSocket) {
			this.controlSocket = controlSocket;
			this.dataSocket = dataSocket;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("LOL! I'm running!");

			while (!this.isInterrupted()) {
				try {
					System.out.println("Waiting for connection.");
					Socket clientRequestSocket = controlSocket.accept();
					Socket clientReceiveSocket = dataSocket.accept();

					System.out.println("Inet Addr: " + clientRequestSocket.getInetAddress());
					System.out.println("Port Num: " + clientRequestSocket.getPort());

					System.out.println("Local Inet Addr: " + clientRequestSocket.getLocalAddress());
					System.out.println("Local Port Num: " + clientRequestSocket.getLocalPort());

					ObjectMapper mapper = new ObjectMapper();
					NodeInformation nodeInfo = mapper.readValue(clientRequestSocket.getInputStream(),
							NodeInformation.class);

					Requester requester = new TcpRequester(clientRequestSocket);
					Receiver receiver = new TcpReceiver(clientReceiveSocket);

					nodeManager.addLiveNode(nodeInfo.getSn(), requester, receiver);
					

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			try {
				if (!controlSocket.isClosed())
					controlSocket.close();
				if (!dataSocket.isClosed())
					dataSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@PostConstruct
	public void initialize() throws IOException {
		listener = new TcpListener(new ServerSocket(5555), new ServerSocket(5556));
		listener.start();
	}

	@PreDestroy
	public void terminate() {
		listener.interrupt();

	}

}

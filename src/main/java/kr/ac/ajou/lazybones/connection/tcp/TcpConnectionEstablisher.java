package kr.ac.ajou.lazybones.connection.tcp;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.ac.ajou.lazybones.connection.Receiver;
import kr.ac.ajou.lazybones.connection.Requester;
import kr.ac.ajou.lazybones.managers.RequesterManager;
import kr.ac.ajou.lazybones.repos.entities.NodeEntity;
import kr.ac.ajou.lazybones.managers.NodeManager;
import kr.ac.ajou.lazybones.managers.ReceiverManager;
import kr.ac.ajou.lazybones.templates.NodeInformation;

@Component
public class TcpConnectionEstablisher {

	private TcpListener controlSocketListener;
	private TcpListener dataSocketListener;

	@Autowired
	RequesterManager requesterManager;

	@Autowired
	NodeManager nodeManager;

	@Autowired
	ReceiverManager receiverManager;

	private abstract class SocketTask {
		public void run(Socket socket) {

		}
	}

	private class TcpConnectionValidator extends Thread {

		private Socket clientSocket;
		private SocketTask task;

		public TcpConnectionValidator(Socket clientSocket, SocketTask task) {
			this.clientSocket = clientSocket;
			this.task = task;
		}

		@Override
		public void run() {
			task.run(clientSocket);
		}
	}

	private class TcpListener extends Thread {

		private ServerSocket socket;

		public TcpListener(ServerSocket socket) {
			this.socket = socket;

			System.out.println("Listening IP Addr: " + socket.getInetAddress());

		}

		public void processAcception(Socket socket) {

		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("LOL! I'm running!");

			while (!this.isInterrupted()) {
				try {
					System.out.println("Waiting for connection.");

					Socket clientSocket = socket.accept();
					this.processAcception(clientSocket);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			try {
				if (!socket.isClosed())
					socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@PostConstruct
	public void initialize() throws IOException {

		// ControlSocket Listener
		controlSocketListener = new TcpListener(new ServerSocket(5555)) {
			@Override
			public void processAcception(Socket socket) {
				SocketTask task = new SocketTask() {
					@Override
					public void run(Socket socket) {
						// System.out.println("Inet Addr: " +
						// socket.getInetAddress());
						// System.out.println("Port Num: " + socket.getPort());
						//
						// System.out.println("Local Inet Addr: " +
						// socket.getLocalAddress());
						// System.out.println("Local Port Num: " +
						// socket.getLocalPort());

						ObjectMapper mapper = new ObjectMapper();
						mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false);

						NodeInformation nodeInfo;
						try {
							nodeInfo = mapper.readValue(socket.getInputStream(), NodeInformation.class);

							Requester requester;

							NodeEntity node = nodeManager.findNodeBySerial(nodeInfo.getSn());
							BufferedWriter writer = new BufferedWriter(
									new OutputStreamWriter(socket.getOutputStream()));

							if (node != null) {
								requester = new TcpRequester(node.getSerialNumber(), socket);
								System.out.println("Requester connection established: " + node.getSerialNumber());
								requesterManager.attachRequester(node.getNID(), requester);
								writer.write("{\"conn\":\"accepted\"}");
								writer.flush();

							} else {
								writer.write("{\"conn\":\"refused\"}");
								writer.flush();
								
//								try {
//									Thread.sleep(3000);;
//								} catch (InterruptedException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								}
//								writer.close();

								System.out.println("Connection refused: Not registered node.");
							}

						} catch (IOException e) {
							// e.printStackTrace();
							System.out.println("Wrong input. Connection will be refused.");
						}

					}
				};

				new TcpConnectionValidator(socket, task).start();
			}
		};

		// ControlSocket Listener
		dataSocketListener = new TcpListener(new ServerSocket(5556)) {
			@Override
			public void processAcception(Socket socket) {
				SocketTask task = new SocketTask() {
					@Override
					public void run(Socket socket) {
						// System.out.println("Inet Addr: " +
						// socket.getInetAddress());
						// System.out.println("Port Num: " + socket.getPort());
						//
						// System.out.println("Local Inet Addr: " +
						// socket.getLocalAddress());
						// System.out.println("Local Port Num: " +
						// socket.getLocalPort());

						ObjectMapper mapper = new ObjectMapper();
						mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false);

						NodeInformation nodeInfo;
						try {
							nodeInfo = mapper.readValue(socket.getInputStream(), NodeInformation.class);

							Receiver receiver;

							NodeEntity node = nodeManager.findNodeBySerial(nodeInfo.getSn());
							BufferedWriter writer = new BufferedWriter(
									new OutputStreamWriter(socket.getOutputStream()));

							if (node != null) {

								receiver = new TcpReceiver(node.getSerialNumber(), socket);
								System.out.println("Receiver connection established: " + node.getSerialNumber());
								receiverManager.attachReceiver(node.getNID(), receiver);
								writer.write("{\"conn\":\"accepted\"}");
								writer.flush();
							} else {
								writer.write("{\"conn\":\"refused\"}");
								writer.flush();

//								try {
//									Thread.sleep(1000);
//								} catch (InterruptedException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								}
//								writer.close();

								System.out.println("Connection refused: Not registered node.");
							}

						} catch (IOException e) {
							// e.printStackTrace();
							System.out.println("Wrong input. Connection will be refused.");
						}

					}
				};

				new TcpConnectionValidator(socket, task).start();
			}
		};

		controlSocketListener.start();
		dataSocketListener.start();
	}

	@PreDestroy
	public void terminate() {
		controlSocketListener.interrupt();
		dataSocketListener.interrupt();

	}

}

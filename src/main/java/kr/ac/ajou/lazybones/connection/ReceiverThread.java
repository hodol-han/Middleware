package kr.ac.ajou.lazybones.connection;

import kr.ac.ajou.lazybones.managers.DataDispatcher;

public class ReceiverThread extends Thread {

	private DataDispatcher disp;

	private Receiver recv;

	public ReceiverThread(Receiver recv, DataDispatcher disp) {
		super();
		this.disp = disp;
		this.recv = recv;
	}

	@Override
	public void run() {
		while (!this.isInterrupted()) {

			String data = recv.receive();
			disp.dispatch(recv.getSn(), data);
		}
	}

	@Override
	public void interrupt() {
		// TODO Auto-generated method stub
		super.interrupt();
		this.recv.disconnect();
	}
	
	public boolean isConnected(){
		return this.recv.isConnected();
	}
	
	public String getSerialNumber(){
		return this.recv.getSn();
	}
	
}
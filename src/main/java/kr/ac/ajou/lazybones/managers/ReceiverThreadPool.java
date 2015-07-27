package kr.ac.ajou.lazybones.managers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.ac.ajou.lazybones.components.Receiver;

@Component
public class ReceiverThreadPool {
	
	private Map<Long, ReceiverThread> receivers;

	public class ReceiverThread extends Thread {

		@Autowired
		private DataDispatcher disp;

		private Receiver recv;

		public ReceiverThread(Receiver recv) {
			super();
			this.recv = recv;
		}

		@Override
		public void run() {
			while (!this.isInterrupted()) {

				String data = recv.receive();
				disp.dispatch(data);
			}
		}

		@Override
		public void interrupt() {
			// TODO Auto-generated method stub
			super.interrupt();
			this.recv.disconnect();
		}
	}

	public ReceiverThreadPool() {
		this.receivers = new HashMap<>();
	}

	/**
	 * @param receivers
	 */
	public ReceiverThreadPool(Map<Long, ReceiverThread> receivers) {
		this.receivers = receivers;
	}

	public void attachReceiver(Long nid, Receiver receiver) {
		ReceiverThread thread = new ReceiverThread(receiver);

		this.receivers.put(nid, thread);
		thread.start();
	}
	
	

	public void detachReceiver(Long nid) {
		ReceiverThread thread = this.receivers.get(nid);
		if(thread != null){
			thread.interrupt();
			this.receivers.remove(nid);			
		}
	}

}

package kr.ac.ajou.lazybones.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.ac.ajou.lazybones.connection.Receiver;
import kr.ac.ajou.lazybones.connection.ReceiverThread;

@Component
public class ReceiverManager {

	@Autowired
	private DataDispatcher disp;

	public static final int MONITOR_INTERVAL = 2000;
	private Timer nodeMonitorTimer;

	private class LivenessMonitorTask extends TimerTask {
		@Override
		public void run() {
			for (Entry<String, ReceiverThread> entry : receivers.entrySet()) {
				if (!entry.getValue().isConnected()) {
					System.out.println("Receiver Disconnected: " + entry.getKey());
					detachReceiver(entry.getKey());
					System.out.println("Receiver Detached: " + entry.getKey());
				}
			}
		}
	}

	private Map<String, ReceiverThread> receivers;

	public ReceiverManager() {
		this.receivers = new HashMap<>();
	}

	/**
	 * @param receivers
	 */
	public ReceiverManager(Map<String, ReceiverThread> receivers) {
		this.receivers = receivers;
	}

	public synchronized void attachReceiver(String nid, Receiver receiver) {
		ReceiverThread thread = new ReceiverThread(receiver, disp);

		this.receivers.put(nid, thread);
		thread.start();
	}

	public synchronized void detachReceiver(String nid) {
		ReceiverThread thread = this.receivers.get(nid);
		if (thread != null) {
			thread.interrupt();
			this.receivers.remove(nid);
		}
	}

	@PostConstruct
	public void initialize() {
		LivenessMonitorTask nodeMonitor = new LivenessMonitorTask();
		this.nodeMonitorTimer = new Timer();
		nodeMonitorTimer.scheduleAtFixedRate(nodeMonitor, MONITOR_INTERVAL, MONITOR_INTERVAL);
	}
	
	@PreDestroy
	public void terminate(){
		nodeMonitorTimer.cancel();
	}
	

}

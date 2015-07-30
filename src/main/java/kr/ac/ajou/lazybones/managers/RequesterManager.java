package kr.ac.ajou.lazybones.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import java.util.Map.Entry;

import org.springframework.stereotype.Component;

import kr.ac.ajou.lazybones.connection.Requester;
import kr.ac.ajou.lazybones.repos.entities.NodeEntity;
import kr.ac.ajou.lazybones.repos.entities.UserEntity;
import kr.ac.ajou.lazybones.templates.Result;

@Component
public class RequesterManager {

	public static final int MONITOR_INTERVAL = 2000;
	private Timer nodeMonitorTimer;

	private class LivenessMonitorTask extends TimerTask {
		@Override
		public void run() {
			for (Entry<String, Requester> entry : requesters.entrySet()) {
				if (!entry.getValue().isConnected()) {
					System.out.println("Requester disconnected: " + entry.getKey());
					detachRequester(entry.getKey());
					System.out.println("Requester detached: " + entry.getKey());
				}

			}
		}
	}

	private Map<String, Requester> requesters = new HashMap<>();

	public synchronized void attachRequester(String nid, Requester requester) {
		this.requesters.put(nid, requester);
	}

	public synchronized void detachRequester(String nid) {
		this.requesters.remove(nid);
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

	public Result queryToNode(UserEntity user, NodeEntity node, String query) {

		Result result = new Result();

		if (node.getOwner().equals(user.getUserID())) {
			Requester requester = this.requesters.get(node.getNID());
			if (requester != null) {
				String data;
				synchronized (requester) {
					data = requester.query(query);
				}
				result.setData(data);
				result.setResult("Succeed");
				return result;

			} else {
				result.setResult("Failed");
				result.setReason("Target node is not online.");
				return result;
			}
		} else {
			result.setResult("Failed");
			result.setReason("Not authorized user.");
			return result;
		}

	}

}

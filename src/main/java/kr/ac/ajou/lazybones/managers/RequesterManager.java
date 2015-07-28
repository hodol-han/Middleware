package kr.ac.ajou.lazybones.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;

import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.ac.ajou.lazybones.connection.Receiver;
import kr.ac.ajou.lazybones.connection.Requester;
import kr.ac.ajou.lazybones.repos.entities.NodeEntity;
import kr.ac.ajou.lazybones.repos.entities.UserEntity;
import kr.ac.ajou.lazybones.templates.Result;

@Component
public class RequesterManager {

	public static final long MONITOR_INTERVAL = 3000;
	private Timer nodeMonitorTimer;

	private class LivenessMonitorTask extends TimerTask {
		@Override
		public void run() {
			for (Entry<Long, Requester> entry : requesters.entrySet()) {
				if (!entry.getValue().isConnected()) {
					System.out.println("Requester disconnected: " + entry.getKey());
					detachRequester(entry.getKey());
					System.out.println("Requester detached: " + entry.getKey());
				}

			}
		}
	}

	private Map<Long, Requester> requesters = new HashMap<>();

	public synchronized void attachRequester(Long nid, Requester requester) {
		this.requesters.put(nid, requester);
	}

	public synchronized void detachRequester(Long nid) {
		this.requesters.remove(nid);
	}

	@PostConstruct
	public void initialize() {
		LivenessMonitorTask nodeMonitor = new LivenessMonitorTask();
		this.nodeMonitorTimer = new Timer();
		nodeMonitorTimer.scheduleAtFixedRate(nodeMonitor, MONITOR_INTERVAL, MONITOR_INTERVAL);
	}

	public Result queryToNode(UserEntity user, NodeEntity node, String query) {

		Result result = new Result();

		if (node.getOwner().getId().equals(user.getId())) {
			Requester requester = this.requesters.get(node.getId());
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

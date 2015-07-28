package kr.ac.ajou.lazybones.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import kr.ac.ajou.lazybones.repos.entities.NodeEntity;

@Service
public class DataDispatcher {

	@Autowired
	LogManager logger;
	
	@Autowired
	NodeManager nodeManager;
	
	public void dispatch(String sn, String data){
		NodeEntity node = nodeManager.findBySerial(sn);
		logger.logNodeSensorData(node, data);
	}
	
}

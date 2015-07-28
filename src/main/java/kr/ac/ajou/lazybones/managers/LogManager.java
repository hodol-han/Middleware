package kr.ac.ajou.lazybones.managers;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.ac.ajou.lazybones.repos.NodeSensorLogRepository;
import kr.ac.ajou.lazybones.repos.UserCommandLogRepository;
import kr.ac.ajou.lazybones.repos.entities.NodeEntity;
import kr.ac.ajou.lazybones.repos.entities.NodeSensorLogEntity;
import kr.ac.ajou.lazybones.repos.entities.UserCommandLogEntity;
import kr.ac.ajou.lazybones.repos.entities.UserEntity;

@Repository
public class LogManager {
	
	@Autowired
	NodeSensorLogRepository nodeLogRepo;
	
	@Autowired
	UserCommandLogRepository userLogRepo;
		
	public void logUserCommand(UserEntity user, NodeEntity node, String command){

		Date loggedAt = Calendar.getInstance().getTime();
		
		UserCommandLogEntity logEntity = new UserCommandLogEntity();
		logEntity.setUser(user);
		logEntity.setNode(node);
		logEntity.setCommand(command);
		logEntity.setLoggedAt(loggedAt);
		
		userLogRepo.save(logEntity);
	}
	
	public void logNodeSensorData(NodeEntity node, String sensorData){
		
		Date loggedAt = Calendar.getInstance().getTime();
		
		NodeSensorLogEntity logEntity = new NodeSensorLogEntity();
		logEntity.setNode(node);
		logEntity.setSensorData(sensorData);
		logEntity.setLoggedAt(loggedAt);
		
		nodeLogRepo.save(logEntity);	
		
	}

	public List<NodeSensorLogEntity> getNodeDataLog(UserEntity user, NodeEntity node){
		if(node.getOwner().getId().equals(user.getId()))
			return nodeLogRepo.findNodeSensorLogEntitiesByNode(node);
		else
			return null;
	}
	
	public List<UserCommandLogEntity> getUserCommandLogsByNode(UserEntity user, NodeEntity node){
		if(node.getOwner().getId().equals(user.getId()))
			return userLogRepo.findUserCommandLogEntitiesByNode(node);
		else
			return null;
	}

	public List<UserCommandLogEntity> getUserCommandLogsByUser(UserEntity user){
		return userLogRepo.findUserCommandLogEntitiesByUser(user);		
	}
	
}

package kr.ac.ajou.lazybones.managers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.ac.ajou.lazybones.repos.NodeHistoryRepository;
import kr.ac.ajou.lazybones.repos.UserHistoryRepository;
import kr.ac.ajou.lazybones.repos.entities.NodeEntity;
import kr.ac.ajou.lazybones.repos.entities.NodeHistoryEntity;
import kr.ac.ajou.lazybones.repos.entities.UserEntity;
import kr.ac.ajou.lazybones.repos.entities.UserHistoryEntity;

@Repository
public class LogManager {

	NodeHistoryRepository nodeLogRepo = new NodeHistoryRepository();
	UserHistoryRepository userLogRepo = new UserHistoryRepository();

	public void logUserCommand(UserEntity user, NodeEntity node, String command) {

		Date loggedAt = Calendar.getInstance().getTime();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MMdd'T'HH:mm:ss.SSS'Z'");
		String stringifiedLoggedAt = dateFormatter.format(loggedAt);

		UserHistoryEntity logEntity = new UserHistoryEntity();
		logEntity.setUID(user.getUserID());
		logEntity.setNID(node.getNID());
		logEntity.setQuery(command);
		logEntity.setTime(stringifiedLoggedAt);

		userLogRepo.createUserHistoryItem(logEntity);
	}

	public void logNodeSensorData(NodeEntity node, String sensorData) {

		Date loggedAt = Calendar.getInstance().getTime();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MMdd'T'HH:mm:ss.SSS'Z'");
		String stringifiedLoggedAt = dateFormatter.format(loggedAt);

		NodeHistoryEntity logEntity = new NodeHistoryEntity();
		logEntity.setNID(node.getNID());
		logEntity.setSAValues(sensorData);
		logEntity.setTime(stringifiedLoggedAt);

		nodeLogRepo.createNodeHistoryItems(logEntity);

	}

	public List<NodeHistoryEntity> getNodeDataLogs(UserEntity user, NodeEntity node) {
		if (node.getOwner().equals(user.getUserID()))
			return nodeLogRepo.findNodeHistoriesbyNodeID(node.getNID());
		else
			return null;
	}

	public List<UserHistoryEntity> getUserCommandLogsByNode(UserEntity user, NodeEntity node) {
		if (node.getOwner().equals(user.getUserID()))
			return userLogRepo.findUserHistoriesbyNodeID(node.getNID());
		else
			return null;
	}

	public List<UserHistoryEntity> getUserCommandLogsByUser(UserEntity user) {
		return userLogRepo.findUserHistoriesbyUserID(user.getUserID());
	}

	public List<UserHistoryEntity> getUserCommandLogsByUserInRangeTime(UserEntity user, Long millis) {
		return userLogRepo.findUserHistoriesByUserIDInRangeTime(user.getUserID(), millis);
	}

	public List<UserHistoryEntity> getUserCommandLogsByNodeInRangeTime(UserEntity user, NodeEntity node, Long millis) {
		if (node.getOwner().equals(user.getUserID()))
			return userLogRepo.findUserHistoriesByNodeIDInRangeTime(node.getNID(), millis);
		else
			return null;
	}

	public List<NodeHistoryEntity> getNodeDataLogsInRangeTime(UserEntity user, NodeEntity node, Long millis) {
		if (node.getOwner().equals(user.getUserID()))
			return nodeLogRepo.findSpecificNodeHistoryInRangeTime(node.getNID(), millis);
		else
			return null;
	}
}

package kr.ac.ajou.lazybones.repos;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;

import kr.ac.ajou.lazybones.managers.DynamoDBManager;
import kr.ac.ajou.lazybones.repos.entities.NodeHistoryEntity;

public class NodeHistoryRepository {
	static NodeHistory nHistoryDB;
	static DynamoDBMapper mapper;
	
	public NodeHistoryRepository () {
		nHistoryDB = new NodeHistory();
		mapper = DynamoDBManager.getMapper();
	}
	
	public NodeHistoryRepository (NodeHistoryEntity ent) {
		nHistoryDB = new NodeHistory(ent.getNID(), ent.getTime(), ent.getDoor(), ent.getLight(), 
				ent.getProximity(), ent.getAlarm(), ent.getTemperature(), ent.getHumidity());
		mapper = DynamoDBManager.getMapper();
	}
	
	public void createNodeHistoryItems(NodeHistoryEntity ent) {
		createNodeHistoryItems(nHistoryDB, ent.getNID(), ent.getTime(), ent.getDoor(), ent.getLight(), ent.getProximity(),
				ent.getAlarm(), ent.getTemperature(), ent.getHumidity());
	}
	
	private void createNodeHistoryItems(NodeHistory nHistoryDB, Integer nid, String time, String door, String light, String prox,
			String alarm, String temper, String hum) {
		nHistoryDB.setNodeID(nid);
		nHistoryDB.setTime(time);
		nHistoryDB.setDoor(door);
		nHistoryDB.setLight(light);
		nHistoryDB.setProximity(prox);
		nHistoryDB.setAlarm(alarm);
		nHistoryDB.setTemperature(temper);
		nHistoryDB.setHumidity(hum);
		
		mapper.save(nHistoryDB);
		
		Integer hashKey = nid;
		String rangeKey = time;
		NodeHistory nhKey = new NodeHistory();
		nhKey.setNodeID(hashKey);
		
		Condition rangeKeyCondition = new Condition().withComparisonOperator(ComparisonOperator.EQ.toString())
				.withAttributeValueList(new AttributeValue().withS(rangeKey));
		DynamoDBQueryExpression<NodeHistory> queryExpression = new DynamoDBQueryExpression<NodeHistory>().withHashKeyValues(nhKey)
				.withRangeKeyCondition("Time", rangeKeyCondition);
		
		List<NodeHistory> latestNodeHistory = mapper.query(NodeHistory.class, queryExpression);
        
		System.out.println("Item created: ");
		for(NodeHistory nh : latestNodeHistory) {
			System.out.format("SerialNumber=%s, Time=%s, Door=%s, Light=%s, Proximity=%s, Alarm=%s, Temperature=%s,"
					+ "Humidity=%s\n", nh.getNodeID(), nh.getTime(), nh.getDoor(), nh.getLight(), nh.getProximity(), nh.getAlarm(),
					nh.getTemperature(), nh.getHumidity());
		}
	}
	
	public void printNodeHistoryItems(NodeHistoryEntity ent) {
		printNodeHistoryItems(ent.getNID(), ent.getTime());
	}
	
	private void printNodeHistoryItems(Integer nid, String time) {
		
        Integer hashKey = nid;
		String rangeKey = time;
		NodeHistory nhKey = new NodeHistory();
		nhKey.setNodeID(hashKey);
		List<NodeHistory> latestNodeHistory;
		
		if(time != null) {
			Condition rangeKeyCondition = new Condition().withComparisonOperator(ComparisonOperator.EQ.toString())
				.withAttributeValueList(new AttributeValue().withS(rangeKey));
			DynamoDBQueryExpression<NodeHistory> queryExpression = new DynamoDBQueryExpression<NodeHistory>().withHashKeyValues(nhKey)
				.withRangeKeyCondition("Time", rangeKeyCondition);
			latestNodeHistory = mapper.query(NodeHistory.class, queryExpression);
		}
		else {
			DynamoDBQueryExpression<NodeHistory> queryExpression = new DynamoDBQueryExpression<NodeHistory>().withHashKeyValues(nhKey);
			latestNodeHistory = mapper.query(NodeHistory.class, queryExpression);
		}
		
		for(NodeHistory nh : latestNodeHistory) {
			System.out.format("SerialNumber=%s, Time=%s, Door=%s, Light=%s, Proximity=%s, Alarm=%s, Temperature=%s,"
					+ "Humidity=%s\n", nh.getNodeID(), nh.getTime(), nh.getDoor(), nh.getLight(), nh.getProximity(), nh.getAlarm(),
					nh.getTemperature(), nh.getHumidity());
		}
	}
	
	public List<NodeHistoryEntity> findNodeHistories (NodeHistoryEntity ent) {
		return findNodeHistories(ent.getNID(), ent.getTime());
	}
	
	private List<NodeHistoryEntity> findNodeHistories (Integer nid, String time) {
		Integer hashKey = nid;
		String rangeKey = time;
		NodeHistory nhKey = new NodeHistory();
		nhKey.setNodeID(hashKey);
		List<NodeHistory> latestNodeHistory;
		
		if(time != null) {
			Condition rangeKeyCondition = new Condition().withComparisonOperator(ComparisonOperator.EQ.toString())
				.withAttributeValueList(new AttributeValue().withS(rangeKey));
			DynamoDBQueryExpression<NodeHistory> queryExpression = new DynamoDBQueryExpression<NodeHistory>().withHashKeyValues(nhKey)
				.withRangeKeyCondition("Time", rangeKeyCondition);
			latestNodeHistory = mapper.query(NodeHistory.class, queryExpression);
		}
		else {
			DynamoDBQueryExpression<NodeHistory> queryExpression = new DynamoDBQueryExpression<NodeHistory>().withHashKeyValues(nhKey);
			latestNodeHistory = mapper.query(NodeHistory.class, queryExpression);
		}
		
		List<NodeHistoryEntity> list = new ArrayList<>();
		
		for(NodeHistory n : latestNodeHistory) {
			NodeHistoryEntity ret = new NodeHistoryEntity();
			
			ret.setNID(n.getNodeID());
			ret.setTime(n.getTime());
			ret.setDoor(n.getDoor());
			ret.setLight(n.getLight());
			ret.setProximity(n.getProximity());
			ret.setAlarm(n.getAlarm());
			ret.setTemperature(n.getTemperature());
			ret.setHumidity(n.getHumidity());
			
			list.add(ret);
		}
		
		return list;
	}
	
	public List<NodeHistoryEntity> findNodeHistoriesbyNodeID(Integer id) {
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		scanExpression.addFilterCondition("NodeID", new Condition().withComparisonOperator(ComparisonOperator.EQ)
				.withAttributeValueList(new AttributeValue().withN(id.toString())));
		List<NodeHistory> scanResult = mapper.scan(NodeHistory.class, scanExpression);
		
		List<NodeHistoryEntity> list = new ArrayList<>();
		
		for(NodeHistory n : scanResult) {
			NodeHistoryEntity nhe = new NodeHistoryEntity();
				
			nhe.setNID(n.getNodeID());
			nhe.setTime(n.getTime());
			nhe.setDoor(n.getDoor());
			nhe.setLight(n.getLight());
			nhe.setProximity(n.getProximity());
			nhe.setAlarm(n.getAlarm());
			nhe.setTemperature(n.getTemperature());
			nhe.setHumidity(n.getHumidity());
			
			list.add(nhe);
		}
		
		return list;
	}
	
	public void updateNodeHistoryItem(NodeHistoryEntity ent) {
		updateNodeHistoryItem(ent.getNID(), ent.getTime(), ent.getDoor(), ent.getLight(),
				ent.getProximity(), ent.getAlarm(), ent.getTemperature(), ent.getHumidity());
	}
	
	private void updateNodeHistoryItem(Integer nid, String time, String door, String light, String prox,
			String alarm, String temper, String hum) {
		NodeHistory itemRetrieved;
		
		if(time != null) {
			itemRetrieved = mapper.load(NodeHistory.class, nid, time);
			itemRetrieved.setTime(time);
		}
		else
			itemRetrieved = mapper.load(NodeHistory.class, nid);
		
		itemRetrieved.setDoor(door);
		itemRetrieved.setLight(light);
		itemRetrieved.setProximity(prox);
		itemRetrieved.setAlarm(alarm);
		itemRetrieved.setTemperature(temper);
		itemRetrieved.setHumidity(hum);
        System.out.println("Item updated: ");
        System.out.format("SerialNumber=%s, Time=%s, Door=%s, Light=%s, Proximity=%s, Alarm=%s, Temperature=%s,"
					+ "Humidity=%s\n", itemRetrieved.getNodeID(), itemRetrieved.getTime(), itemRetrieved.getDoor(), itemRetrieved.getLight(),
					itemRetrieved.getProximity(), itemRetrieved.getAlarm(), itemRetrieved.getTemperature(), itemRetrieved.getHumidity());
	}
	
	/*
	public void findUpdatedNodeHistoryItems() {
		findUpdatedNodeHistoryItems(nHistory.getSerialNumber(), nHistory.getTime());
	}
	
	public void findUpdatedNodeHistoryItems(Integer nid, String time) {
		DynamoDBMapperConfig config = new DynamoDBMapperConfig(DynamoDBMapperConfig.ConsistentReads.CONSISTENT);
		NodeHistory updatedItem;
		
		if(time != null)
			updatedItem = mapper.load(NodeHistory.class, nid, time, config);
		else
			updatedItem = mapper.load(NodeHistory.class, nid, config);
		
        System.out.println("Retrieved the previously updated item: ");
        System.out.println(updatedItem);
	}
	*/
	
	public void deleteNodeHistoryItem(NodeHistoryEntity ent) {
		deleteNodeHistoryItem(ent.getNID(), ent.getTime());
	}
	
	private void deleteNodeHistoryItem(Integer nid, String time) {
		DynamoDBMapperConfig config = new DynamoDBMapperConfig(DynamoDBMapperConfig.ConsistentReads.CONSISTENT);
		NodeHistory updatedItem;
		NodeHistory deletedItem;
		
		if(time != null) {
			updatedItem = mapper.load(NodeHistory.class, nid, time, config);
			mapper.delete(updatedItem);
			deletedItem = mapper.load(NodeHistory.class, updatedItem.getNodeID(), updatedItem.getTime(), config);
		}
		else {
			updatedItem = mapper.load(NodeHistory.class, nid, config);
			mapper.delete(updatedItem);
			deletedItem = mapper.load(NodeHistory.class, updatedItem.getNodeID(), config);
		}
		
        if (deletedItem == null)
            System.out.println("Done - The item is deleted.");
        else
        	System.out.println("Fail - The item is still remained.");
	}
    
    @DynamoDBTable(tableName="NodeHistory")
    public static class NodeHistory {
        private Integer NodeID;
        private String Time;
        private String Door;
        private String Light;
        private String Proximity;
        private String Alarm;
        private String Temperature;
        private String Humidity;
        
        public NodeHistory() {
        	
        }
        
        public NodeHistory(Integer nid, String time, String door, String light, String prox,
    			String alarm, String temper, String hum) {
			// TODO Auto-generated constructor stub
        	this.NodeID = nid;
        	this.Time = time;
        	this.Door = door;
        	this.Light = light;
        	this.Proximity = prox;
        	this.Alarm = alarm;
        	this.Temperature = temper;
        	this.Humidity = hum;
		}
                
        @DynamoDBHashKey(attributeName="NodeID")
        public Integer getNodeID() { return NodeID; }
        public void setNodeID(Integer NodeID) { this.NodeID = NodeID; }
        
        @DynamoDBRangeKey(attributeName="Time")
        public String getTime() { return Time; }    
        public void setTime(String Time) { this.Time = Time; }
        
        @DynamoDBAttribute(attributeName="Door")
        public String getDoor() { return Door; }
        public void setDoor(String Door) { this.Door = Door; }
        
        @DynamoDBAttribute(attributeName="Light")
        public String getLight() { return Light; }
        public void setLight(String Light) { this.Light = Light; }
        
        @DynamoDBAttribute(attributeName="Proximity")
        public String getProximity() { return Proximity; }
        public void setProximity(String Proximity) { this.Proximity = Proximity; }
        
        @DynamoDBAttribute(attributeName="Alarm")
        public String getAlarm() { return Alarm; }
        public void setAlarm(String Alarm) { this.Alarm = Alarm; }
        
        @DynamoDBAttribute(attributeName="Temperature")
        public String getTemperature() { return Temperature; }
        public void setTemperature(String Temperature) { this.Temperature = Temperature; }
        
        @DynamoDBAttribute(attributeName="humidity")
        public String getHumidity() { return Humidity; }
        public void setHumidity(String Humidity) { this.Humidity = Humidity; }
        
    }
}
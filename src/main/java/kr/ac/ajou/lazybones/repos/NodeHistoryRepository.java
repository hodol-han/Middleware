package kr.ac.ajou.lazybones.repos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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
		nHistoryDB = new NodeHistory(ent.getNID(), ent.getTime(), ent.getSAValues());
		mapper = DynamoDBManager.getMapper();
	}
	
	public void createNodeHistoryItems(NodeHistoryEntity ent) {
		createNodeHistoryItems(nHistoryDB, ent.getNID(), ent.getTime(), ent.getSAValues());
	}
	
	private void createNodeHistoryItems(NodeHistory nHistoryDB, String nid, String time, String values) {
		nHistoryDB.setNodeID(nid);
		nHistoryDB.setTime(time);
		nHistoryDB.setSAValues(values);
		
		mapper.save(nHistoryDB);
		
		String hashKey = nid;
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
			System.out.format("SerialNumber=%s, Time=%s, SAValues=%s\n", nh.getNodeID(), nh.getTime(), nh.getSAValues());
		}
	}
	
	public void printNodeHistoryItems(NodeHistoryEntity ent) {
		printNodeHistoryItems(ent.getNID(), ent.getTime());
	}
	
	private void printNodeHistoryItems(String nid, String time) {
		
        String hashKey = nid;
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
			System.out.format("SerialNumber=%s, Time=%s, SAValues=%s\n", nh.getNodeID(), nh.getTime(), nh.getSAValues());
		}
	}
	
	public List<NodeHistoryEntity> findNodeHistories (NodeHistoryEntity ent) {
		return findNodeHistories(ent.getNID(), ent.getTime());
	}
	
	private List<NodeHistoryEntity> findNodeHistories (String nid, String time) {
		String hashKey = nid;
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
			ret.setSAValues(n.getSAValues());
			
			list.add(ret);
		}
		
		return list;
	}
	
	public List<NodeHistoryEntity> findNodeHistoriesbyNodeID(String id) {
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		scanExpression.addFilterCondition("NodeID", new Condition().withComparisonOperator(ComparisonOperator.EQ)
				.withAttributeValueList(new AttributeValue().withS(id)));
		List<NodeHistory> scanResult = mapper.scan(NodeHistory.class, scanExpression);
		
		List<NodeHistoryEntity> list = new ArrayList<>();
		
		for(NodeHistory n : scanResult) {
			NodeHistoryEntity nhe = new NodeHistoryEntity();
				
			nhe.setNID(n.getNodeID());
			nhe.setTime(n.getTime());
			nhe.setSAValues(n.getSAValues());
			
			list.add(nhe);
		}
		
		return list;
	}
	
	public void updateNodeHistoryItem(NodeHistoryEntity ent) {
		updateNodeHistoryItem(ent.getNID(), ent.getTime(), ent.getSAValues());
	}
	
	private void updateNodeHistoryItem(String nid, String time, String values) {
		NodeHistory itemRetrieved;
		
		if(time != null) {
			itemRetrieved = mapper.load(NodeHistory.class, nid, time);
			itemRetrieved.setTime(time);
		}
		else
			itemRetrieved = mapper.load(NodeHistory.class, nid);
		
		itemRetrieved.setSAValues(values);
        System.out.println("Item updated: ");
        System.out.format("SerialNumber=%s, Time=%s, SAValues=%s\n", itemRetrieved.getNodeID(), itemRetrieved.getTime(), itemRetrieved.getSAValues());
	}
	
	/*
	public void findUpdatedNodeHistoryItems() {
		findUpdatedNodeHistoryItems(nHistory.getSerialNumber(), nHistory.getTime());
	}
	
	public void findUpdatedNodeHistoryItems(String nid, String time) {
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
	
	private void deleteNodeHistoryItem(String nid, String time) {
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
	
	// range format example = (7L*24L*60L*60L*1000L) (1 week)
	public List<NodeHistoryEntity> findSpecificNodeHistoryInRangeTime (String nid, Long range) {
		String hashKey = nid;
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MMdd'T'HH:mm:ss.SSS'Z'");
		
		Long currentTimeMilli = (new Date()).getTime();
		Long rangeTimeMilli = (new Date()).getTime() - range;
		dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		String currentTime = dateFormatter.format(currentTimeMilli);
		String rangeTime = dateFormatter.format(rangeTimeMilli);
		
		NodeHistory nhKey = new NodeHistory();
		nhKey.setNodeID(hashKey);
		List<NodeHistory> latestNodeHistory;
		
		
		Condition rangeKeyCondition = new Condition().withComparisonOperator(ComparisonOperator.BETWEEN.toString())
			.withAttributeValueList(new AttributeValue().withS(currentTime), new AttributeValue().withS(rangeTime));
		
		DynamoDBQueryExpression<NodeHistory> queryExpression = new DynamoDBQueryExpression<NodeHistory>().withHashKeyValues(nhKey)
			.withRangeKeyCondition("Time", rangeKeyCondition);
		
		latestNodeHistory = mapper.query(NodeHistory.class, queryExpression);
		
		return this.transform(latestNodeHistory);
	}
	
//	public List<NodeHistoryEntity> findNodeHistoryInRangeTime (Long range) {
//		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MMdd'T'HH:mm:ss.SSS'Z'");
//		
//		Long currentTimeMilli = (new Date()).getTime();
//		Long rangeTimeMilli = (new Date()).getTime() - range;
//		dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
//		String currentTime = dateFormatter.format(currentTimeMilli);
//		String rangeTime = dateFormatter.format(rangeTimeMilli);
//		
//		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
//		scanExpression.addFilterCondition("Time", new Condition().withComparisonOperator(ComparisonOperator.BETWEEN)
//				.withAttributeValueList(new AttributeValue().withS(currentTime), new AttributeValue().withS(rangeTime)));
//		List<NodeHistory> scanResult = mapper.scan(NodeHistory.class, scanExpression);
//		
//		return this.transform(scanResult);
//	}
	
	private List<NodeHistoryEntity> transform(List<NodeHistory> target){
		List<NodeHistoryEntity> list = new ArrayList<>();
		for(NodeHistory item : target){
			NodeHistoryEntity temp = new NodeHistoryEntity();
			temp.setNID(item.getNodeID());
			temp.setSAValues(item.getSAValues());
			temp.setTime(item.getTime());
			
			list.add(temp);
		}
		return list;
	}
    
    @DynamoDBTable(tableName="NodeHistory")
    public static class NodeHistory {
        private String NodeID;
        private String Time;
        private String SAValues;
        
        public NodeHistory() {
        	
        }
        
        public NodeHistory(String nid, String time, String values) {
			// TODO Auto-generated constructor stub
        	this.NodeID = nid;
        	this.Time = time;
        	this.SAValues = values;
		}
                
        @DynamoDBHashKey(attributeName="NodeID")
        public String getNodeID() { return NodeID; }
        public void setNodeID(String NodeID) { this.NodeID = NodeID; }
        
        @DynamoDBRangeKey(attributeName="Time")
        public String getTime() { return Time; }    
        public void setTime(String Time) { this.Time = Time; }
        
        @DynamoDBAttribute(attributeName="SAValues")
        public String getSAValues() { return SAValues; }
        public void setSAValues(String SAValues) { this.SAValues = SAValues; }        
        
    }
}
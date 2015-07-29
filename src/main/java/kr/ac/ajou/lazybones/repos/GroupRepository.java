package kr.ac.ajou.lazybones.repos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

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
import kr.ac.ajou.lazybones.repos.entities.GroupEntity;

public class GroupRepository {
	static Group groupDB;
	static DynamoDBMapper mapper;
	
	public GroupRepository () {
		groupDB = new Group();
		mapper = DynamoDBManager.getMapper();
	}
	
	public GroupRepository (GroupEntity ent) {
		groupDB = new Group(ent.getGroupName(), ent.getUserID());
		mapper = DynamoDBManager.getMapper();
	}

	public void createGroupItems(GroupEntity ent) {
		createGroupItems(groupDB, ent.getGroupName(), ent.getUserID());
	}
	
	private void createGroupItems(Group groupDB, String gName, String uid) {
		groupDB.setGroupName(gName);
		groupDB.setUserID(uid);
				
		mapper.save(groupDB);
		
		String hashKey = gName;
		String rangeKey = uid;
		Group groupKey = new Group();
		groupKey.setGroupName(hashKey);
		Condition rangeKeyCondition = new Condition().withComparisonOperator(ComparisonOperator.EQ.toString())
				.withAttributeValueList(new AttributeValue().withS(rangeKey));
		DynamoDBQueryExpression<Group> queryExpression = new DynamoDBQueryExpression<Group>().withHashKeyValues(groupKey)
				.withRangeKeyCondition("UserID", rangeKeyCondition);
		
		List<Group> latestGroup = mapper.query(Group.class, queryExpression);
        
		System.out.println("Item created: ");
		for(Group group : latestGroup) {
			System.out.format("GroupName=%s, UserID=%s\n", group.getGroupName(), group.getUserID());
		}
	}
	
	public void printGroupItems(GroupEntity ent) {
		printGroupItems(ent.getGroupName(), ent.getUserID());
	}

	private void printGroupItems(String name, String uid) {
		String hashKey = name;
		String rangeKey = uid;
		Group groupKey = new Group();
		groupKey.setGroupName(hashKey);
		List<Group> latestGroup;
		
		if(uid != null) {
			Condition rangeKeyCondition = new Condition().withComparisonOperator(ComparisonOperator.EQ.toString())
					.withAttributeValueList(new AttributeValue().withS(rangeKey));
			DynamoDBQueryExpression<Group> queryExpression = new DynamoDBQueryExpression<Group>().withHashKeyValues(groupKey)
					.withRangeKeyCondition("UserID", rangeKeyCondition);
			latestGroup = mapper.query(Group.class, queryExpression);
		}
		else {
			DynamoDBQueryExpression<Group> queryExpression = new DynamoDBQueryExpression<Group>().withHashKeyValues(groupKey);
			latestGroup = mapper.query(Group.class, queryExpression);
		}
		
		for(Group group : latestGroup) {
			System.out.format("GroupName=%s, UserID=%s\n", group.getGroupName(), group.getUserID());			
		}
	}
	
	public String findGroupName(GroupEntity ent) {
		return findGroupName(ent.getGroupName(), ent.getUserID());
	}
	
	private String findGroupName(String name, String uid) {
		String hashKey = name;
		String rangeKey = uid;
		Group groupKey = new Group();
		groupKey.setGroupName(hashKey);
		List<Group> latestGroup;
		
		if(uid != null) {
			Condition rangeKeyCondition = new Condition().withComparisonOperator(ComparisonOperator.EQ.toString())
					.withAttributeValueList(new AttributeValue().withS(rangeKey));
			DynamoDBQueryExpression<Group> queryExpression = new DynamoDBQueryExpression<Group>().withHashKeyValues(groupKey)
					.withRangeKeyCondition("UserID", rangeKeyCondition);
			latestGroup = mapper.query(Group.class, queryExpression);
		}
		else {
			DynamoDBQueryExpression<Group> queryExpression = new DynamoDBQueryExpression<Group>().withHashKeyValues(groupKey);
			latestGroup = mapper.query(Group.class, queryExpression);
		}
		
		return latestGroup.get(0).getGroupName();
	}
	
	public GroupEntity findGroupbyUserID(String id) {
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		scanExpression.addFilterCondition("UserID", new Condition().withComparisonOperator(ComparisonOperator.EQ)
				.withAttributeValueList(new AttributeValue().withS(id)));
		List<Group> scanResult = mapper.scan(Group.class, scanExpression);
		
		GroupEntity ret = new GroupEntity();
		for(Group g : scanResult) {
			ret.setGroupName(g.getGroupName());
			ret.setUserID(g.getUserID());
		}
		
		return ret;
	}
	
	public GroupEntity findGroup(GroupEntity ent) {
		return findGroup(ent.getGroupName(), ent.getUserID());
	}
	
	private GroupEntity findGroup(String name, String uid) {
		String hashKey = name;
		String rangeKey = uid;
		Group groupKey = new Group();
		groupKey.setGroupName(hashKey);
		List<Group> latestGroup;
		
		if(uid != null) {
			Condition rangeKeyCondition = new Condition().withComparisonOperator(ComparisonOperator.EQ.toString())
					.withAttributeValueList(new AttributeValue().withS(rangeKey));
			DynamoDBQueryExpression<Group> queryExpression = new DynamoDBQueryExpression<Group>().withHashKeyValues(groupKey)
					.withRangeKeyCondition("UserID", rangeKeyCondition);
			latestGroup = mapper.query(Group.class, queryExpression);
		}
		else {
			DynamoDBQueryExpression<Group> queryExpression = new DynamoDBQueryExpression<Group>().withHashKeyValues(groupKey);
			latestGroup = mapper.query(Group.class, queryExpression);
		}
		
		GroupEntity ret = new GroupEntity();
		for(Group g : latestGroup) {
			ret.setGroupName(g.getGroupName());
			ret.setUserID(g.getUserID());
		}
		
		return ret;
	}
	
	public void updateGroupItems(GroupEntity ent) {
		updateGroupItems(ent.getGroupName(), ent.getUserID());
	}
	
	private void updateGroupItems(String name, String uid) {
		Group itemRetrieved;
		
		if(uid != null) {
			itemRetrieved = mapper.load(Group.class, name, uid);
			itemRetrieved.setUserID(uid);
		}
		else
			itemRetrieved = mapper.load(Group.class, name);
		
		itemRetrieved.setUserID(uid);
		mapper.save(itemRetrieved);
        System.out.println("Item updated: ");
        System.out.format("Group Name=%s, User ID=%s\n", itemRetrieved.getGroupName(), itemRetrieved.getUserID());
	}
	
	/*
	public void findUpdatedGroupItems() {
		findUpdatedGroupItems(group.getGroupName(), group.getUserID());
	}
	
	private void findUpdatedGroupItems(String name, String uid) {
		DynamoDBMapperConfig config = new DynamoDBMapperConfig(DynamoDBMapperConfig.ConsistentReads.CONSISTENT);
		Group updatedItem;
		
		if(uid != null)
			updatedItem = mapper.load(Group.class, name, uid, config);
		else
			updatedItem = mapper.load(Group.class, uid);
		
        System.out.println("Retrieved the previously updated item: ");
        System.out.println(updatedItem);
	}
	*/
	
	public void deleteGroupItems(GroupEntity ent) {
		deleteGroupItems(ent.getGroupName(), ent.getUserID());
	}
	
	private void deleteGroupItems(String name, String uid) {
		DynamoDBMapperConfig config = new DynamoDBMapperConfig(DynamoDBMapperConfig.ConsistentReads.CONSISTENT);
		Group updatedItem;
		Group deletedItem;
		
		if(uid != null) {
			updatedItem = mapper.load(Group.class, name, uid, config);
			mapper.delete(updatedItem);
			deletedItem = mapper.load(Group.class, updatedItem.getGroupName(), updatedItem.getUserID(), config);
		}
		else {
			updatedItem = mapper.load(Group.class, name, config);
			mapper.delete(updatedItem);
			deletedItem = mapper.load(Group.class, updatedItem.getGroupName(), config);
		}

        if (deletedItem == null)
            System.out.println("Done - The item is deleted.");
        else
        	System.out.println("Fail - The item is still remained.");
	}
    
    @DynamoDBTable(tableName="Group")
    public static class Group {
        private String GroupName;
        private String UserID;
        
        public Group() {
        	
        }
        
        public Group(String gName, String uid) {
			// TODO Auto-generated constructor stub
        	this.GroupName = gName;
        	this.UserID = uid;
		}
        
        @DynamoDBHashKey(attributeName="GroupName")
        public String getGroupName() { return GroupName; }    
        public void setGroupName(String GroupName) { this.GroupName = GroupName; }
        
        @DynamoDBRangeKey(attributeName="UserID")
        public String getUserID() { return UserID; }    
        public void setUserID(String UserID) { this.UserID = UserID;}
        
    }
}
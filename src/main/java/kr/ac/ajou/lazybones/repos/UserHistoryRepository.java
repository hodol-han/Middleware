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
import kr.ac.ajou.lazybones.repos.entities.UserHistoryEntity;

public class UserHistoryRepository {
	static UserHistory uHistoryDB;
	
	static DynamoDBMapper mapper;

	public UserHistoryRepository() {
		uHistoryDB = new UserHistory();
		mapper = DynamoDBManager.getMapper();
	}

	public UserHistoryRepository(UserHistoryEntity ent) {
		uHistoryDB = new UserHistory(ent.getUID(), ent.getTime(), ent.getNID(), ent.getQuery());
		mapper = DynamoDBManager.getMapper();
	}

	public void createUserHistoryItem(UserHistoryEntity ent) {
		createUserHistoryItem(uHistoryDB, ent.getUID(), ent.getTime(), ent.getNID(), ent.getQuery());
	}

	public void createUserHistoryItem(UserHistory userHistory, String uid, String time, Integer nid, String query) {
		userHistory.setUserID(uid);
		userHistory.setTime(time);
		userHistory.setNodeID(nid);
		userHistory.setQuery(query);

		mapper.save(userHistory);

		String hashKey = uid;
		String rangeKey = time;
		UserHistory uhKey = new UserHistory();
		uhKey.setUserID(hashKey);

		Condition rangeKeyCondition = new Condition().withComparisonOperator(ComparisonOperator.EQ.toString())
				.withAttributeValueList(new AttributeValue().withS(rangeKey));
		DynamoDBQueryExpression<UserHistory> queryExpression = new DynamoDBQueryExpression<UserHistory>()
				.withHashKeyValues(uhKey).withRangeKeyCondition("Time", rangeKeyCondition);

		List<UserHistory> latestUserHistory = mapper.query(UserHistory.class, queryExpression);

		System.out.println("Item created: ");
		for (UserHistory uh : latestUserHistory) {
			System.out.format("UserID=%s, Time=%s, NodeID=%s, Query=%s\n", uh.getUserID(), uh.getTime(), uh.getNodeID(),
					uh.getQuery());
		}
	}

	public void printUserHistory(UserHistoryEntity ent) {
		printUserHistory(ent.getUID(), ent.getTime());
	}

	public void printUserHistory(String uid, String time) {
		String hashKey = uid;
		String rangeKey = time;
		UserHistory uhKey = new UserHistory();
		uhKey.setUserID(hashKey);
		List<UserHistory> latestUserHistory;

		if (time != null) {
			Condition rangeKeyCondition = new Condition().withComparisonOperator(ComparisonOperator.EQ.toString())
					.withAttributeValueList(new AttributeValue().withS(rangeKey));
			DynamoDBQueryExpression<UserHistory> queryExpression = new DynamoDBQueryExpression<UserHistory>()
					.withHashKeyValues(uhKey).withRangeKeyCondition("Time", rangeKeyCondition);
			latestUserHistory = mapper.query(UserHistory.class, queryExpression);
		} else {
			DynamoDBQueryExpression<UserHistory> queryExpression = new DynamoDBQueryExpression<UserHistory>()
					.withHashKeyValues(uhKey);
			latestUserHistory = mapper.query(UserHistory.class, queryExpression);
		}

		for (UserHistory uh : latestUserHistory) {
			System.out.format("UserID=%s, Time=%s, NodeID=%s, Query=%s\n", uh.getUserID(), uh.getTime(), uh.getNodeID(),
					uh.getQuery());
		}
	}

	public List<UserHistoryEntity> findUserHistory(UserHistoryEntity ent) {
		return findUserHistory(ent.getUID(), ent.getTime());
	}

	private List<UserHistoryEntity> findUserHistory(String uid, String time) {
		String hashKey = uid;
		String rangeKey = time;
		UserHistory uhKey = new UserHistory();
		uhKey.setUserID(hashKey);
		List<UserHistory> latestUserHistory;

		if (time != null) {
			Condition rangeKeyCondition = new Condition().withComparisonOperator(ComparisonOperator.EQ.toString())
					.withAttributeValueList(new AttributeValue().withS(rangeKey));
			DynamoDBQueryExpression<UserHistory> queryExpression = new DynamoDBQueryExpression<UserHistory>()
					.withHashKeyValues(uhKey).withRangeKeyCondition("Time", rangeKeyCondition);
			latestUserHistory = mapper.query(UserHistory.class, queryExpression);
		} else {
			DynamoDBQueryExpression<UserHistory> queryExpression = new DynamoDBQueryExpression<UserHistory>()
					.withHashKeyValues(uhKey);
			latestUserHistory = mapper.query(UserHistory.class, queryExpression);
		}

		List<UserHistoryEntity> list = this.transform(latestUserHistory);

		return list;
	}

	public List<UserHistoryEntity> findUserHistoriesbyNodeID(Integer id) {
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		scanExpression.addFilterCondition("NodeID", new Condition().withComparisonOperator(ComparisonOperator.EQ)
				.withAttributeValueList(new AttributeValue().withN(id.toString())));
		List<UserHistory> scanResult = mapper.scan(UserHistory.class, scanExpression);
		List<UserHistoryEntity> list = transform(scanResult);

		return list;
	}

	private List<UserHistoryEntity> transform(List<UserHistory> target) {
		List<UserHistoryEntity> list = new ArrayList<>();

		for (UserHistory u : target) {

			UserHistoryEntity ret = new UserHistoryEntity();

			ret.setUID(u.getUserID());
			ret.setTime(u.getTime());
			ret.setNID(u.getNodeID());
			ret.setQuery(u.getQuery());

			list.add(ret);
		}

		return list;
	}

	public List<UserHistoryEntity> findUserHistoriesbyUserID(String id) {
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		scanExpression.addFilterCondition("UserID", new Condition().withComparisonOperator(ComparisonOperator.EQ)
				.withAttributeValueList(new AttributeValue().withS(id)));
		List<UserHistory> scanResult = mapper.scan(UserHistory.class, scanExpression);

		List<UserHistoryEntity> list = this.transform(scanResult);

		return list;
	}

	public void updateUserHistoryItems(UserHistoryEntity ent) {
		updateUserHistoryItems(ent.getUID(), ent.getTime(), ent.getNID(), ent.getQuery());
	}

	private void updateUserHistoryItems(String uid, String time, Integer nid, String query) {
		UserHistory itemRetrieved;

		if (time != null) {
			itemRetrieved = mapper.load(UserHistory.class, uid, time);
			itemRetrieved.setTime(time);
		} else
			itemRetrieved = mapper.load(UserHistory.class, uid);

		itemRetrieved.setNodeID(nid);
		itemRetrieved.setQuery(query);
		System.out.println("Item updated: ");
		System.out.format("UserID=%s, Time=%s, NodeID=%s, Query=%s\n", itemRetrieved.getUserID(),
				itemRetrieved.getTime(), itemRetrieved.getNodeID(), itemRetrieved.getQuery());
	}

	/*
	 * public void findUpdatedUserHistoryItems() {
	 * findUpdatedUserHistoryItems(uHistory.getUID(), uHistory.getTime()); }
	 * 
	 * public void findUpdatedUserHistoryItems(String uid, String time) {
	 * DynamoDBMapperConfig config = new
	 * DynamoDBMapperConfig(DynamoDBMapperConfig.ConsistentReads.CONSISTENT);
	 * UserHistory updatedItem;
	 * 
	 * if(time != null) updatedItem = mapper.load(UserHistory.class, uid, time,
	 * config); else updatedItem = mapper.load(UserHistory.class, uid, config);
	 * 
	 * System.out.println("Retrieved the previously updated item: ");
	 * System.out.println(updatedItem); }
	 */

	public void deleteUserHistoryItems(UserHistoryEntity ent) {
		deleteUserHistoryItems(ent.getUID(), ent.getTime());
	}

	private void deleteUserHistoryItems(String uid, String time) {
		DynamoDBMapperConfig config = new DynamoDBMapperConfig(DynamoDBMapperConfig.ConsistentReads.CONSISTENT);
		UserHistory updatedItem;
		UserHistory deletedItem;

		if (time != null) {
			updatedItem = mapper.load(UserHistory.class, uid, time, config);
			mapper.delete(updatedItem);
			deletedItem = mapper.load(UserHistory.class, updatedItem.getUserID(), updatedItem.getTime(), config);
		} else {
			updatedItem = mapper.load(UserHistory.class, uid, config);
			mapper.delete(updatedItem);
			deletedItem = mapper.load(UserHistory.class, updatedItem.getUserID(), config);
		}

		if (deletedItem == null)
			System.out.println("Done - The item is deleted.");
		else
			System.out.println("Fail - The item is still remained.");
	}

	@DynamoDBTable(tableName = "UserHistory")
	public static class UserHistory {
		private String UserID;
		private String Time;
		private Integer NodeID;
		private String Query;

		public UserHistory() {

		}

		public UserHistory(String uid, String time, Integer nid, String query) {
			// TODO Auto-generated constructor stub
			this.UserID = uid;
			this.Time = time;
			this.NodeID = nid;
			this.Query = query;
		}

		@DynamoDBHashKey(attributeName = "UserID")
		public String getUserID() {
			return UserID;
		}

		public void setUserID(String UserID) {
			this.UserID = UserID;
		}

		@DynamoDBRangeKey(attributeName = "Time")
		public String getTime() {
			return Time;
		}

		public void setTime(String Time) {
			this.Time = Time;
		}

		@DynamoDBAttribute(attributeName = "NodeID")
		public Integer getNodeID() {
			return NodeID;
		}

		public void setNodeID(Integer NodeID) {
			this.NodeID = NodeID;
		}

		@DynamoDBAttribute(attributeName = "Query")
		public String getQuery() {
			return Query;
		}

		public void setQuery(String Query) {
			this.Query = Query;
		}

	}
}
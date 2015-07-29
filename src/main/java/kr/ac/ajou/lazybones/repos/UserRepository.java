package kr.ac.ajou.lazybones.repos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

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
import kr.ac.ajou.lazybones.repos.entities.UserEntity;

public class UserRepository {
	static User userDB;
	
	static DynamoDBMapper mapper;
	
	public UserRepository () {
		userDB = new User();
		mapper = DynamoDBManager.getMapper();
	}
	
	public UserRepository (UserEntity ent) {
		userDB = new User(ent.getUserKey(), ent.getUserID(), ent.getName(), ent.getPwd());
		mapper = DynamoDBManager.getMapper();
	}

	public void createUserItem(UserEntity ent) {
		createUserItem(userDB, ent.getUserKey(), ent.getUserID(), ent.getName(), ent.getPwd());
	}
	
	private void createUserItem(User userDB, String key, String uid, String name, String pwd) {
		userDB.setUserKey(key);
		userDB.setUserID(uid);
		userDB.setUserName(name);
		userDB.setPassword(pwd);
		
		mapper.save(userDB);
		
		String hashKey = key;
		String rangeKey = uid;
		User userKey = new User();
		userKey.setUserKey(hashKey);
		
		Condition rangeKeyCondition = new Condition().withComparisonOperator(ComparisonOperator.EQ.toString())
				.withAttributeValueList(new AttributeValue().withS(rangeKey));
		DynamoDBQueryExpression<User> queryExpression = new DynamoDBQueryExpression<User>().withHashKeyValues(userKey)
				.withRangeKeyCondition("UserID", rangeKeyCondition);
		
        List<User> latestUser = mapper.query(User.class, queryExpression);
        
		System.out.println("Item created: ");
		for(User u : latestUser) {
			System.out.format("UserKey=%s, UserID=%s, UserName=%s, Password=%s\n", u.getUserKey(), u.getUserID(),
					u.getUserName(), u.getPassword());
		}
	}
	
	public void printUserItems(UserEntity ent) {
		printUserItems(ent.getUserKey(), ent.getUserID());
	}
	
	private void printUserItems(String key, String uid) {       
        String hashKey = key;
		String rangeKey = uid;
		User userKey = new User();
		userKey.setUserKey(hashKey);
		List<User> latestUser;
		
		if(uid != null) {
			Condition rangeKeyCondition = new Condition().withComparisonOperator(ComparisonOperator.EQ.toString())
					.withAttributeValueList(new AttributeValue().withS(rangeKey));
			DynamoDBQueryExpression<User> queryExpression = new DynamoDBQueryExpression<User>().withHashKeyValues(userKey)
					.withRangeKeyCondition("UserID", rangeKeyCondition);
			latestUser = mapper.query(User.class, queryExpression);
		}
		else {
			DynamoDBQueryExpression<User> queryExpression = new DynamoDBQueryExpression<User>().withHashKeyValues(userKey);
			latestUser = mapper.query(User.class, queryExpression);
		}
		
		System.out.println("Item retrieved: ");
		for(User u : latestUser) {
			System.out.format("UserKey=%s, UserID=%s, UserName=%s, Password=%s", u.getUserKey(), u.getUserID(),
					u.getUserName(), u.getPassword());			
		}
	}
	
//	public UserEntity findUserItems(UserEntity ent) {
//		return findUserItems(ent.getUserKey(), ent.getUserID());
//	}
	
//	private UserEntity findUserItems(String key, String uid) {       
//        String hashKey = key;
//		String rangeKey = uid;
//		User userKey = new User();
//		userKey.setUserKey(hashKey);
//		List<User> latestUser;
//		
//		if(uid != null) {
//			Condition rangeKeyCondition = new Condition().withComparisonOperator(ComparisonOperator.EQ.toString())
//					.withAttributeValueList(new AttributeValue().withS(rangeKey));
//			DynamoDBQueryExpression<User> queryExpression = new DynamoDBQueryExpression<User>().withHashKeyValues(userKey)
//					.withRangeKeyCondition("UserID", rangeKeyCondition);
//			latestUser = mapper.query(User.class, queryExpression);
//		}
//		else {
//			DynamoDBQueryExpression<User> queryExpression = new DynamoDBQueryExpression<User>().withHashKeyValues(userKey);
//			latestUser = mapper.query(User.class, queryExpression);
//		}
//		
//		UserEntity ret = new UserEntity();
//		for(User user : latestUser) {
//			ret.setUserKey(user.getUserKey());
//			ret.setUserID(user.getUserID());
//			ret.setName(user.getUserName());
//			ret.setPwd(user.getPassword());
//		}
//		
//		return ret;
//	}
	
//	public String findUserKey(UserEntity ent) {
//		return findUserKey(ent.getUserKey(), ent.getUserID());
//	}
//	
//	private String findUserKey(String key, String uid) {
//		String hashKey = key;
//		String rangeKey = uid;
//		User userKey = new User();
//		userKey.setUserKey(hashKey);
//		List<User> latestUser;
//		
//		if(uid != null) {
//			Condition rangeKeyCondition = new Condition().withComparisonOperator(ComparisonOperator.EQ.toString())
//					.withAttributeValueList(new AttributeValue().withS(rangeKey));
//			DynamoDBQueryExpression<User> queryExpression = new DynamoDBQueryExpression<User>().withHashKeyValues(userKey)
//					.withRangeKeyCondition("UserID", rangeKeyCondition);
//			latestUser = mapper.query(User.class, queryExpression);
//		}
//		else {
//			DynamoDBQueryExpression<User> queryExpression = new DynamoDBQueryExpression<User>().withHashKeyValues(userKey);
//			latestUser = mapper.query(User.class, queryExpression);
//		}
//		
//		return latestUser.get(0).getUserKey();
//	}
	
//	public String findUserID(UserEntity ent) {
//		return findUserID(ent.getUserKey(), ent.getUserID());
//	}
	
//	private String findUserID(String key, String uid) {
//		String hashKey = key;
//		String rangeKey = uid;
//		User userKey = new User();
//		userKey.setUserKey(hashKey);
//		List<User> latestUser;
//		
//		if(uid != null) {
//			Condition rangeKeyCondition = new Condition().withComparisonOperator(ComparisonOperator.EQ.toString())
//					.withAttributeValueList(new AttributeValue().withS(rangeKey));
//			DynamoDBQueryExpression<User> queryExpression = new DynamoDBQueryExpression<User>().withHashKeyValues(userKey)
//					.withRangeKeyCondition("UserID", rangeKeyCondition);
//			latestUser = mapper.query(User.class, queryExpression);
//		}
//		else {
//			DynamoDBQueryExpression<User> queryExpression = new DynamoDBQueryExpression<User>().withHashKeyValues(userKey);
//			latestUser = mapper.query(User.class, queryExpression);
//		}
//		
//		return latestUser.get(0).getUserID();
//	}
	
	public UserEntity findUserByID(String id) {
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		scanExpression.addFilterCondition("UserID", new Condition().withComparisonOperator(ComparisonOperator.EQ)
				.withAttributeValueList(new AttributeValue().withS(id)));
		List<User> scanResult = mapper.scan(User.class, scanExpression);
		
		if(scanResult.isEmpty())
			return null;
		
		UserEntity ret = new UserEntity();
		for(User user : scanResult) {
			ret.setUserKey(user.getUserKey());
			ret.setUserID(user.getUserID());
			ret.setName(user.getUserName());
			ret.setPwd(user.getPassword());
		}
		
		return ret;
	}
	
	public UserEntity findUserByKey(String key) {
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		scanExpression.addFilterCondition("UserKey", new Condition().withComparisonOperator(ComparisonOperator.EQ)
				.withAttributeValueList(new AttributeValue().withS(key)));
		List<User> scanResult = mapper.scan(User.class, scanExpression);
		
		if(scanResult.isEmpty())
			return null;
		
		UserEntity ret = new UserEntity();
		for(User user : scanResult) {
			ret.setUserKey(user.getUserKey());
			ret.setUserID(user.getUserID());
			ret.setName(user.getUserName());
			ret.setPwd(user.getPassword());
		}
		
		return ret;
	}
	
	public void updateUserItem(UserEntity ent) {
		updateUserItems(ent.getUserKey(), ent.getUserID(), ent.getName(), ent.getPwd());
	}
	
	private void updateUserItems(String key, String uid, String name, String pwd) {
		User itemRetrieved;
		
		if(uid != null) {
			itemRetrieved = mapper.load(User.class, key, uid);
			itemRetrieved.setUserID(uid);
		}
		else
			itemRetrieved = mapper.load(User.class, key);
		
		itemRetrieved.setUserName(name);
		itemRetrieved.setPassword(pwd);
		mapper.save(itemRetrieved);
		
        System.out.println("Item updated: ");
        System.out.format("UserKey=%s, UserID=%s, UserName=%s, Passwords=%s", itemRetrieved.getUserKey(), itemRetrieved.getUserID(),
        		itemRetrieved.getUserName(), itemRetrieved.getPassword());
	}
	
	/*
	public void findUpdatedUserItems() {
		findUpdatedUserItems(user.getUserKey(), user.getUserID());
	}
	
	private void findUpdatedUserItems(String key, String uid) {
		DynamoDBMapperConfig config = new DynamoDBMapperConfig(DynamoDBMapperConfig.ConsistentReads.CONSISTENT);
		User updatedItem = mapper.load(User.class, key, uid, config);
        System.out.println("Retrieved the previously updated item: ");
        System.out.println(updatedItem);
	}
	*/
	
	public void deleteUserItem(UserEntity ent) {
		deleteUserItem(ent.getUserKey(), ent.getUserID());
	}
	
	private void deleteUserItem(String key, String uid) {
		DynamoDBMapperConfig config = new DynamoDBMapperConfig(DynamoDBMapperConfig.ConsistentReads.CONSISTENT);   
        User updatedItem;
        User deletedItem;
		
		if(uid != null) {
			updatedItem = mapper.load(User.class, key, uid, config);
			mapper.delete(updatedItem);
			deletedItem = mapper.load(User.class, updatedItem.getUserKey(), updatedItem.getUserID(), config);
		}
		else {
			updatedItem = mapper.load(User.class, key, config);
			mapper.delete(updatedItem);
			deletedItem = mapper.load(User.class, updatedItem.getUserKey(), config);
		}
		
		if (deletedItem == null)
            System.out.println("Done - The item is deleted.");
        else
        	System.out.println("Fail - The item is still remained.");
	}
	
    @DynamoDBTable(tableName="User")
    public static class User {
        private String UserKey;
    	private String UserID;
        private String UserName;
        private String Password;
        
        public User() {
        	
        }
        
        public User(String key, String id, String name, String pwd) {
			// TODO Auto-generated constructor stub
        	this.UserKey = key;
        	this.UserID = id;
        	this.UserName = name;
        	this.Password = pwd;
		}
        
        @DynamoDBHashKey(attributeName="UserKey")
        public String getUserKey() { return UserKey; }
        public void setUserKey(String UserKey) { this.UserKey = UserKey; }
        
		@DynamoDBRangeKey(attributeName="UserID")
        public String getUserID() { return UserID; }
        public void setUserID(String UserID) { this.UserID = UserID; }
        
        @DynamoDBAttribute(attributeName="UserName")
        public String getUserName() { return UserName; }    
        public void setUserName(String UserName) { this.UserName = UserName; }
        
        @DynamoDBAttribute(attributeName="Password")
        public String getPassword() { return Password; }    
        public void setPassword(String Password) { this.Password = Password;}
        
        @Override
        public String toString() {
            return "User [Name=" + UserName + "]";            
        }
    }
}
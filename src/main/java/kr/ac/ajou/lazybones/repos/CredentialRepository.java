package kr.ac.ajou.lazybones.repos;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import kr.ac.ajou.lazybones.managers.DynamoDBManager;
import kr.ac.ajou.lazybones.repos.entities.CredentialEntity;

public class CredentialRepository {
	static CredentialEntity credential = new CredentialEntity();
	static Credential credentialDB;
	static DynamoDBMapper mapper;
	
	public CredentialRepository () {
		mapper = DynamoDBManager.getMapper();
	}
	
	public CredentialRepository (Integer cid, String uid, String issue) {
		credential.setCID(cid);
		credential.setUID(uid);
		credential.setIssuedAt(issue);
		credentialDB = new Credential(credential.getCID(), credential.getUID(), credential.getIssuedAt());
		mapper = DynamoDBManager.getMapper();
	}
	
	public void createCredentialItems() {
		createCredentialItems(credentialDB, credential.getCID(), credential.getUID(), credential.getIssuedAt());
	}

	public void createCredentialItems(Credential credentialDB, Integer cid, String uid, String issuedAt) {
		credentialDB.setCID(cid);
		credentialDB.setUID(uid);
		credentialDB.setIssuedAt(issuedAt);
		
		mapper.save(credentialDB);
		
        System.out.println("Item created: ");
        System.out.println(mapper.load(Credential.class, cid));
	}
	
	public void findCredentialItems() {
		findCredentialItems(credential.getCID());
	}
	
	public void findCredentialItems(Integer cid) {
		Credential itemRetrieved = mapper.load(Credential.class, cid);
        System.out.println("Item retrieved: ");
        System.out.println(itemRetrieved);
	}
	
	public void updateCredentialItems() {
		updateCredentialItems(credential.getCID(), credential.getUID(), credential.getIssuedAt());
	}
	
	public void updateCredentialItems(Integer cid, String uid, String issuedAt) {
		Credential itemRetrieved = mapper.load(Credential.class, cid);
		
		itemRetrieved.setUID(uid);
		itemRetrieved.setIssuedAt(issuedAt);
		mapper.save(itemRetrieved);
        System.out.println("Item updated: ");
        System.out.println(itemRetrieved);
	}
	
	public void findUpdatedCredentialItems() {
		findUpdatedCredentialItems(credential.getCID());
	}
	
	public void findUpdatedCredentialItems(Integer cid) {
		DynamoDBMapperConfig config = new DynamoDBMapperConfig(DynamoDBMapperConfig.ConsistentReads.CONSISTENT);
		Credential updatedItem = mapper.load(Credential.class, cid, config);
        System.out.println("Retrieved the previously updated item: ");
        System.out.println(updatedItem);
	}
	
	public void deleteCredentialItems() {
		deleteCredentialItems(credential.getCID());
	}
	
	public void deleteCredentialItems(Integer cid) {
		DynamoDBMapperConfig config = new DynamoDBMapperConfig(DynamoDBMapperConfig.ConsistentReads.CONSISTENT);
		Credential updatedItem = mapper.load(Credential.class, cid, config);
		mapper.delete(updatedItem);
        
        Credential deletedItem = mapper.load(Credential.class, updatedItem.getCID(), config);
        if (deletedItem == null)
            System.out.println("Done - The item is deleted.");
        else
        	System.out.println("Fail - The item is still remained.");
	}
	
    @DynamoDBTable(tableName="Credential")
    public static class Credential {
        private Integer CID;
        private String UID;
        private String IssuedAt;
        
        public Credential() {
        	
        }
        
        public Credential(Integer cid, String uid, String issue) {
			// TODO Auto-generated constructor stub
        	this.CID = cid;
        	this.UID = uid;
        	this.IssuedAt = issue;
		}
        
        @DynamoDBHashKey(attributeName="CID")
        public Integer getCID() { return CID; }
        public void setCID(Integer CID) { this.CID = CID; }
        
        @DynamoDBAttribute(attributeName="UID")
        public String getUID() { return UID; }    
        public void setUID(String UID) { this.UID = UID; }
        
        @DynamoDBAttribute(attributeName="IssuedAt")
        public String getIssuedAt() { return IssuedAt; }    
        public void setIssuedAt(String IssuedAt) { this.IssuedAt = IssuedAt;}
        
    }
}
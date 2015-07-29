package kr.ac.ajou.lazybones.repos.entities;

public class GroupEntity {
	private String groupName;
	private String userID;
	
	public GroupEntity() {
		
	}
	
	public GroupEntity(String name, String uid) {
		this.groupName = name;
		this.userID = uid;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String name) {
		this.groupName = name;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String uid) {
		this.userID = uid;
	}

}
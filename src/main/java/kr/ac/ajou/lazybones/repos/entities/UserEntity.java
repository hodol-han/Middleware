package kr.ac.ajou.lazybones.repos.entities;

public class UserEntity {
	private String userKey;
	private String userID;
	private String name;
	private String pwd;
	
	public UserEntity() {
		
	}
	
	public UserEntity(String key, String id, String name, String pwd) {
		this.userKey = key;
		this.userID = id;
		this.name = name;
		this.pwd = pwd;
	}
	
	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String key) {
		this.userKey = key;
	}
	
	public String getUserID() {
		return userID;
	}

	public void setUserID(String id) {
		this.userID = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

}
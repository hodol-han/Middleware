package kr.ac.ajou.lazybones.repos.entities;

public class UserHistoryEntity {
	private String uid;
	private String time;
	private Integer nid;
	private String query;
    
	public UserHistoryEntity() {
		
	}
	
	public UserHistoryEntity(String uid, String time, Integer nid, String query) {
		this.uid = uid;
		this.time = time;
		this.nid = nid;
		this.query = query;
	}
	
	public String getUID() {
		return uid;
	}

	public void setUID(String id) {
		this.uid = id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public Integer getNID() {
		return nid;
	}

	public void setNID(Integer id) {
		this.nid = id;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

}
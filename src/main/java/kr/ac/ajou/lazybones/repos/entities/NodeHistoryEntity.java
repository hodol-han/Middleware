package kr.ac.ajou.lazybones.repos.entities;

public class NodeHistoryEntity {
    private String NID;
    private String time;
    private String saValues;
    
	public NodeHistoryEntity() {
		
	}
	
	public NodeHistoryEntity(String nid, String time, String values) {
		this.NID = nid;
		this.time = time;
		this.saValues = values;
	}
	
	public String getNID() {
		return NID;
	}

	public void setNID(String nid) {
		this.NID = nid;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSAValues() {
		return saValues;
	}

	public void setSAValues(String values) {
		this.saValues = values;
	}
}
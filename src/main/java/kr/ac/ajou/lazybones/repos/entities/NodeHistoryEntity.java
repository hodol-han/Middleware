package kr.ac.ajou.lazybones.repos.entities;

public class NodeHistoryEntity {
    private Integer NID;
    private String time;
    private String door;
    private String light;
    private String proximity;
    private String alarm;
    private String temperature;
    private String humidity;
    
	public NodeHistoryEntity() {
		
	}
	
	public NodeHistoryEntity(Integer nid, String time, String door, String light, String prox,
			String alarm, String temper, String hum) {
		this.NID = nid;
		this.time = time;
		this.door = door;
		this.light = light;
		this.proximity = prox;
		this.alarm = alarm;
		this.temperature = temper;
		this.humidity = hum;
	}
	
	public Integer getNID() {
		return NID;
	}

	public void setNID(Integer nid) {
		this.NID = nid;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDoor() {
		return door;
	}

	public void setDoor(String door) {
		this.door = door;
	}
	
	public String getLight() {
		return light;
	}

	public void setLight(String light) {
		this.light = light;
	}

	public String getProximity() {
		return proximity;
	}

	public void setProximity(String prox) {
		this.proximity = prox;
	}
	
	public String getAlarm() {
		return alarm;
	}

	public void setAlarm(String alarm) {
		this.alarm = alarm;
	}
	
	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temper) {
		this.temperature = temper;
	}
	
	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String hum) {
		this.humidity = hum;
	}
}
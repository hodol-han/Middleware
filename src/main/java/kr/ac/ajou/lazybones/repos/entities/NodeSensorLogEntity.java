package kr.ac.ajou.lazybones.repos.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import kr.ac.ajou.lazybones.templates.NodeSensorLogForm;

@Entity
@Table(name="NodeSensorLog")
public class NodeSensorLogEntity {

//	id int not null primary key auto_increment,
//	nid int not null,
//	sensor_data text,
//	logged_at datetime,

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name="nid")
	private NodeEntity node;
	
	@Type(type="text")
	@Column(name="sensor_data")
	private String sensorData;
	
	@DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
	@Column(name="logged_at")
	private Date loggedAt;

	public NodeSensorLogEntity(){
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public NodeEntity getNode() {
		return node;
	}

	public void setNode(NodeEntity node) {
		this.node = node;
	}

	public String getSensorData() {
		return sensorData;
	}

	public void setSensorData(String sensorData) {
		this.sensorData = sensorData;
	}

	public Date getLoggedAt() {
		return loggedAt;
	}

	public void setLoggedAt(Date loggedAt) {
		this.loggedAt = loggedAt;
	}

	public NodeSensorLogForm transform(){
		NodeSensorLogForm form = new NodeSensorLogForm();
		form.setId(this.getId());
		form.setNid(this.getNode().getId());
		form.setSensor_data(this.getSensorData());
		form.setLogged_at(this.getLoggedAt());
		
		return form;
	}
	
	

}

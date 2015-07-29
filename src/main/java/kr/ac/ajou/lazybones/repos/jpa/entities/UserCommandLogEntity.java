package kr.ac.ajou.lazybones.repos.jpa.entities;

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

import kr.ac.ajou.lazybones.templates.UserCommandLogForm;

@Entity
@Table(name="UserCommandLog")
public class UserCommandLogEntity {
	
//	id int not null primary key auto_increment,
//	uid varchar(20) not null,
//	nid int not null,
//	command text,
//	logged_at datetime,
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="uid")
	private UserEntity user;
	
	@ManyToOne
	@JoinColumn(name="nid")
	private NodeEntity node;
	
	@Column
	@Type(type="text")
	private String command;
	
	@DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
	@Column(name="logged_at")
	private Date loggedAt;


	public UserCommandLogEntity() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public NodeEntity getNode() {
		return node;
	}

	public void setNode(NodeEntity node) {
		this.node = node;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public Date getLoggedAt() {
		return loggedAt;
	}

	public void setLoggedAt(Date loggedAt) {
		this.loggedAt = loggedAt;
	}
	
	public UserCommandLogForm transform(){
		UserCommandLogForm form = new UserCommandLogForm();
		form.setId(this.getId());
		form.setCommand(this.getCommand());
		form.setUid(this.getUser().getId());
		form.setLogged_at(this.getLoggedAt());
		
		return form;
	}
	
	

}

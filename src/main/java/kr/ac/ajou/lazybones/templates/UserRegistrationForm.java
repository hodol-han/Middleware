package kr.ac.ajou.lazybones.templates;

import kr.ac.ajou.lazybones.repos.jpa.entities.UserEntity;

public class UserRegistrationForm {
	
	private String id;
	private String name;
	private String pwd;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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

	public UserEntity toUser() {
		return new UserEntity(id, name, pwd);
	}
}
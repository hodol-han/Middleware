package kr.ac.ajou.lazybones.managers;

//import kr.ac.ajou.lazybones.repos.jpa.UserRepository;
//import kr.ac.ajou.lazybones.repos.jpa.entities.UserEntity;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.ac.ajou.lazybones.repos.UserRepository;
import kr.ac.ajou.lazybones.repos.entities.UserEntity;

@Repository
public class UserManager {

//	@Autowired
	private UserRepository repo = new UserRepository();

	public UserEntity insert(String id, String name, String pwd) {

		UserEntity user = new UserEntity();
		user.setUserID(id);
		user.setName(name);
		user.setPwd(pwd);
		user.setUserKey(UUID.randomUUID().toString());

		repo.createUserItem(user);
		
		return user;
	}

	public UserEntity findById(String id) {
		return repo.findUserByID(id);
	}

//	public boolean renewKeyhash(UserEntity user) {
//
//		String keyhash = UUID.randomUUID().toString();
//
//		if (repo.findById(user.getId()) != null) {
//			user.setKeyhash(keyhash);
//			repo.save(user);
//			return true;
//		} else
//			return false;
//	}

	public boolean isValidKeyhash(String keyhash) {
		UserEntity user = repo.findUserByKey(keyhash);
		if (user != null)
			return true;
		else
			return false;

	}

	public boolean isValidLogin(String uid, String password) {
		UserEntity user = repo.findUserByID(uid);
		if (user != null) {
			if (user.getPwd().equals(password))
				return true;
		}
		return false;
	}

	public UserEntity findUserByKeyhash(String keyhash) {
		return repo.findUserByKey(keyhash);
	}

	public int update(UserEntity u) {
		if (repo.findUserByID(u.getUserID()) != null) {
			repo.updateUserItem(u);
			return 1;
		}
		return 0;
	}

	public int delete(UserEntity u) {
		if (repo.findUserByID(u.getUserID()) != null) {
			repo.deleteUserItem(u);
			return 1;
		} else {
			return 0;
		}
	}
}
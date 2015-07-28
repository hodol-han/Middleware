package kr.ac.ajou.lazybones.managers;

import kr.ac.ajou.lazybones.repos.UserRepository;
import kr.ac.ajou.lazybones.repos.entities.UserEntity;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public class UserManager {

	@Autowired
	private UserRepository repo;

	public UserEntity insert(String id, String name, String pwd) {

		UserEntity user = new UserEntity();
		user.setId(id);
		user.setName(name);
		user.setPwd(pwd);
		user.setKeyhash(UUID.randomUUID().toString());

		return repo.save(user);
	}

	public UserEntity findById(String id) {
		return repo.findById(id);
	}

	public boolean renewKeyhash(UserEntity user) {

		String keyhash = UUID.randomUUID().toString();

		if (repo.findById(user.getId()) != null) {
			user.setKeyhash(keyhash);
			repo.save(user);
			return true;
		} else
			return false;
	}

	public boolean isValidKeyhash(String keyhash) {
		UserEntity user = repo.findByKeyhash(keyhash);
		if (user != null)
			return true;
		else
			return false;

	}

	public boolean isValidLogin(String uid, String password) {
		UserEntity user = repo.findById(uid);
		if (user != null) {
			if (user.getPwd().equals(password))
				return true;
		}
		return false;
	}

	public UserEntity findUserByKeyhash(String keyhash) {
		return repo.findByKeyhash(keyhash);

	}

	public int update(UserEntity u) {
		if (repo.findById(u.getId()) != null) {
			repo.save(u);
			return 1;
		}
		return 0;
	}

	public int delete(UserEntity u) {
		if (repo.findById(u.getId()) != null) {
			repo.delete(u);
			return 1;
		} else {
			return 0;
		}
	}
}
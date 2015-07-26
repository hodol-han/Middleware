package kr.ac.ajou.lazybones.managers;

import kr.ac.ajou.lazybones.repos.UserRepository;
import kr.ac.ajou.lazybones.repos.entities.UserEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public class UserManager {

	@Autowired
	private UserRepository repo;

	public UserEntity insert(String id, String name, String pwd) {
		UserEntity user = repo.save(new UserEntity(id, name, pwd));
		return user;
	}

	public UserEntity findById(String id) {
		return repo.findById(id);
	}

	public int update(UserEntity u) {
		if(repo.findById(u.getId()) != null){
			repo.save(u);
			return 1;
		} 
		return 0;
	}

	public int delete(UserEntity u) {
		if(repo.findById(u.getId()) != null){
			repo.delete(u);
			return 1;
		} else {
			return 0;
		}
	}
}
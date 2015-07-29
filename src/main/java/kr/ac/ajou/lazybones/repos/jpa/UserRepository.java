package kr.ac.ajou.lazybones.repos.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.ac.ajou.lazybones.repos.jpa.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	<U extends UserEntity> U save(UserEntity user);

	UserEntity findById(String id);
	
	UserEntity findByKeyhash(String keyhash);

	void delete(UserEntity u);
}
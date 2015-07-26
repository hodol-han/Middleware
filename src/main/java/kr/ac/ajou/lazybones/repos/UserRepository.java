package kr.ac.ajou.lazybones.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.ac.ajou.lazybones.repos.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	<U extends UserEntity> U save(UserEntity user);

	UserEntity findById(String id);

	void delete(UserEntity u);
}
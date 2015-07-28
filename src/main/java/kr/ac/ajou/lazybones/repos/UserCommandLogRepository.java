package kr.ac.ajou.lazybones.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.ac.ajou.lazybones.repos.entities.NodeEntity;
import kr.ac.ajou.lazybones.repos.entities.UserCommandLogEntity;
import kr.ac.ajou.lazybones.repos.entities.UserEntity;;

public interface UserCommandLogRepository extends JpaRepository <UserCommandLogEntity, Long> {
	
	public List<UserCommandLogEntity> findUserCommandLogEntitiesByUser(UserEntity user);
	public List<UserCommandLogEntity> findUserCommandLogEntitiesByNode(NodeEntity node);
	
	<U extends UserCommandLogEntity> U save(UserCommandLogEntity log);
	

}

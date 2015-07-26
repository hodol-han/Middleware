package kr.ac.ajou.lazybones.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.ac.ajou.lazybones.repos.entities.NodeEntity;

public interface NodeRepository extends JpaRepository<NodeEntity, Long> {

}

package kr.ac.ajou.lazybones.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.ac.ajou.lazybones.repos.entities.NodeEntity;
import kr.ac.ajou.lazybones.repos.entities.NodeSensorLogEntity;

public interface NodeSensorLogRepository extends JpaRepository<NodeSensorLogEntity, Long> {

	public <U extends NodeSensorLogEntity> U save(NodeSensorLogEntity log);

	public List<NodeSensorLogEntity> findNodeSensorLogEntitiesByNode(NodeEntity node);

}

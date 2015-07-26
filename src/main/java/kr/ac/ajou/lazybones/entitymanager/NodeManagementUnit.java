package kr.ac.ajou.lazybones.entitymanager;

import org.springframework.beans.factory.annotation.Autowired;

import kr.ac.ajou.lazybones.repos.NodeRepository;
import kr.ac.ajou.lazybones.repos.entities.NodeEntity;

public class NodeManagementUnit {
	@Autowired
	private NodeRepository repo;

	public NodeEntity insert() {
		return null;
	}

	public NodeEntity findById(String id) {
		return null;
	}

	public int update(NodeEntity u) {
		return 0;
	}

	public int delete(NodeEntity u) {
		return 0;
	}

}

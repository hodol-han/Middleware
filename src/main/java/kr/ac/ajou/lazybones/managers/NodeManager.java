package kr.ac.ajou.lazybones.managers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.ac.ajou.lazybones.repos.NodeRepository;
import kr.ac.ajou.lazybones.repos.entities.NodeEntity;
import kr.ac.ajou.lazybones.repos.entities.UserEntity;

@Repository
public class NodeManager {


	@Autowired
	private NodeRepository repo;
	
	@Autowired
	ReceiverManager receiverManager;
	
	@Autowired
	RequesterManager requesterManager;
	


	@Transactional
	public void registerNode(UserEntity owner, String serialNumber, String productName, String nodeName) {
		if (repo.findBySerial(serialNumber) == null) {
			
			NodeEntity entity = new NodeEntity(owner, serialNumber, productName, nodeName);

			repo.save(entity);
		}
	}

	@Transactional
	public void unregisterNode(Long nid) {
		NodeEntity node = repo.findById(nid);
		if (node != null) {
			repo.delete(node);

			// Erase current requester and receiver of the node if exists
			receiverManager.detachReceiver(nid);
			requesterManager.detachRequester(nid);
		}
	}

	@Transactional
	public List<NodeEntity> findNodesByOwner(UserEntity owner) {
		
		return repo.findNodeEntitiesByOwner(owner);
	}

	public NodeEntity findById(Long id) {
		return null;
	}

	public NodeEntity findBySerial(String serial) {
		return repo.findBySerial(serial);
	}

	public int update(NodeEntity u) {
		return 0;
	}

	public int delete(NodeEntity u) {
		return 0;
	}

}

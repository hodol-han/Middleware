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

	private NodeRepository repo = new NodeRepository();

	@Autowired
	ReceiverManager receiverManager;

	@Autowired
	RequesterManager requesterManager;

	@Transactional
	public void registerNode(UserEntity owner, String serialNumber, String productName, String nodeName) {
		if (repo.findNodeBySerialNumber(serialNumber) == null) {

			NodeEntity entity = new NodeEntity();

			entity.setOwner(owner.getUserID());
			entity.setSerialNumber(serialNumber);
			entity.setProductName(productName);
			entity.setNodeName(nodeName);

			repo.createNodeItems(entity);
		}
	}

	@Transactional
	public void unregisterNode(String nid) {
		NodeEntity node = repo.findNodebyNodeID(nid);
		if (node != null) {
			repo.deleteNodeItem(node);

			// Erase current requester and receiver of the node if exists
			receiverManager.detachReceiver(nid);
			requesterManager.detachRequester(nid);
		}
	}

	@Transactional
	public List<NodeEntity> findNodesByOwner(UserEntity owner) {

		return repo.findNodesByOwner(owner.getUserID());
	}

	public NodeEntity findNodeById(String id) {
		return repo.findNodebyNodeID(id);
	}

	public NodeEntity findNodeBySerial(String serial) {
		return repo.findNodeBySerialNumber(serial);
	}

	public boolean update(NodeEntity u) {
		if (repo.findNodebyNodeID(u.getNID()) != null) {
			repo.updateNodeItem(u);
			return true;
		}
		return false;
	}

	public boolean delete(NodeEntity u) {
		if (repo.findNodebyNodeID(u.getNID()) != null) {
			repo.deleteNodeItem(u);
			return true;
		}
		return false;
	}

}

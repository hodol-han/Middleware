package kr.ac.ajou.lazybones.managers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.ac.ajou.lazybones.components.Node;
import kr.ac.ajou.lazybones.components.Receiver;
import kr.ac.ajou.lazybones.components.Requester;
import kr.ac.ajou.lazybones.repos.NodeRepository;
import kr.ac.ajou.lazybones.repos.entities.NodeEntity;
import kr.ac.ajou.lazybones.repos.entities.UserEntity;

@Repository
public class NodeManager {

	@Autowired
	private NodeRepository repo;
	
	@Autowired
	ReceiverThreadPool rp;

	@PersistenceContext
	private EntityManager em;

	private Map<Long, Requester> requesters = new HashMap<>();

	@Transactional
	public boolean addLiveNode(String serial, Requester requester, Receiver receiver) {

		// Find SN and make a node then add it into liveNodes
		NodeEntity entity = this.findBySerial(serial);

		if (entity != null) {
			Long nid = entity.getId();

			this.requesters.put(nid, requester);
			this.rp.attachReceiver(nid, receiver);
			
			return true;
		} else
			return false;
	}
	
	@Transactional
	public boolean removeLiveNode(Long nid){
		// Find SN and make a node then add it into liveNodes
		this.requesters.remove(nid);
		this.rp.detachReceiver(nid);
		
		return true;
		
	}

	@Transactional
	public void registerNode(String ownerId, String serialNumber, String productName, String nodeName) {
		if (repo.findBySerial(serialNumber) == null) {
			// TODO
			UserEntity owner = em.getReference(UserEntity.class, ownerId);
			NodeEntity entity = new NodeEntity(owner, serialNumber, productName, nodeName);

			repo.save(entity);
		}
	}
	
	@Transactional
	public void unregisterNode(Long nid){
		NodeEntity node = repo.findById(nid);
		if(node != null){
			repo.delete(node);
			
			//Erase current requester and receiver of the node if exists
			this.requesters.remove(nid);
			this.rp.detachReceiver(nid);
		}
	}

	@Transactional
	public List<NodeEntity> findNodesByOwner(String ownerId) {
		UserEntity owner = em.getReference(UserEntity.class, ownerId);

		return repo.findNodesByOwner(owner);
	}

	public NodeEntity findById(String id) {
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

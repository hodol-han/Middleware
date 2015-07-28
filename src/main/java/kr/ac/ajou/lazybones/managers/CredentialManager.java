package kr.ac.ajou.lazybones.managers;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.ac.ajou.lazybones.repos.CredentialRepository;
import kr.ac.ajou.lazybones.repos.entities.UserEntity;

//@Repository
public class CredentialManager {
	
//	@Autowired
//	private CredentialRepository repo;
//	
//	@PersistenceContext
//	private EntityManager em;
//
//	//How to validate pwd?
//	public String issueNewKeyhash(String uid){
//		
//		UserEntity user = em.getReference(UserEntity.class, uid);
//		if(user != null)
//		{
//			
//			repo.findCredentialEntitiesByUser(user);
//		}
//		
//		return null;
//	}
//	
//	public boolean isValid(String keyhash){
//		
//		if(repo.findUserEntityByKeyhash(keyhash) != null)
//			return true;
//		else
//			return false;
//		
//	}
//	
	


}

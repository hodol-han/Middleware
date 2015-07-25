package kr.ac.ajou.lazybones.components;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Actual management component for Washers.
 * 
 * @author AJOU
 *
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class NodeManager {
	
	private Map<String, Node> liveNodes = new HashMap<>();
	
	public void addLiveNode(Node node){
		//TODO
	}
	
	public void registerNode(){
		//TODO
	}
	

}

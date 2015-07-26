
package kr.ac.ajou.lazybones.components;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class MessageBroker {
		
	Map<String, Node> nodes;
	
	public MessageBroker(){
		this.nodes = new HashMap<>();
	}
	
	public MessageBroker(Map<String, Node> nodes){
		this.nodes = nodes;
	}
	
	public void command(String to, String command){
		String result;
		
		Node node = this.nodes.get(to);
		if(node != null)
			result = node.command(command);
		else
			result = "failed";
	}
	
	
	


}

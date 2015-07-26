package kr.ac.ajou.lazybones.components;

public class Node {
	
	
	private Requester connection;
	
	
	public Node(Requester connection){
		this.connection = connection;
	}
	
	public String query(String query){
		return connection.query(query);
	}	

}

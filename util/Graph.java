package util;

import java.util.List;
import java.util.ArrayList;
import music.*;

/*
 *This class represents the entire graph of chord progressions
 *
 */

public class Graph<T> {

	List<Node<T>> _nodes;
	Node<T> _startingNode;

	public Graph(){

		_nodes = new ArrayList<Node<T>>();
		
	}
	
	public void setStartingNode(Node<T> node) {
		_startingNode = node;
	}

	//Adds a Node to the Graph
	public void addToStartingNode(Node<T> node, int weight) {

		if(_startingNode == null) {
			
			_startingNode = new Node<T>(node.getValue());
		}
//		if(edgeExists(_startingNode, node)) {// If the starting Node already connects to a node with the same value as the given Node
//			
//			for(Edge<T> edge : _startingNode.getFollowing()) {
//				
//				Node<T> levelOneNode = edge.getBack();
//				if(levelOneNode.getValue().equals(node.getValue())) { // levelOneNode has the same value as the given node 
//					
//					// add the node's following edges to levelOneNode, ignoring nodes that lead to the same value
//					for(Edge<T> tempEdge : node.getFollowing()) {
//						
//						if(!edgeExists(levelOneNode, tempEdge.getBack()))
//							levelOneNode.addFollowingEdge(new Edge(levelOneNode, tempEdge.getBack(), weight));;
//					}
//					
//					node = levelOneNode;
//					break;
//				}
//			}
//		}
//		else {
//			
//			addEdge(_startingNode, node, weight);
//		}
		addEdge(_startingNode, node, weight);
	}

	//makes a connection between node1 and node2 with the specified weight.
	//An Edge is created for each node and added to the other node's adjacency list.
	//The shorter the weight, the more likely that edge (chord progression) will be used
	public void addEdge(Node<T> node1, Node<T> node2, int weight){


		if(!_nodes.contains(node1))
			_nodes.add(node1);

		if(!_nodes.contains(node2))
			_nodes.add(node2);

		if(!edgeExists(node1, node2)) { //does add an edge if the current node already links to a node with the same value 
			Edge<T> edge = new Edge<T>(node1, node2, weight);
			node1.addFollowingEdge(edge);
			node2.addPrecedingEdge(edge);
		}
	}

	//removes an edge within the graph, and removes that edge from the adjacency lists of the nodes it connects
	public void removeEdge(Edge<T> edge) {

		Node<T> front = edge.getFront();
		front.removeFollowingEdge(edge);

		Node<T> back = edge.getBack();
		back.removePrecedingEdge(edge);
	}

	//removes the edge that links two given nodes, and removes that edge from the adjacency lists of those nodes
	public void removeEdge(Node<T> front, Node<T> back) {

		for(Node<T> node : _nodes) {

			//search all the nodes for the node front
			if(node == front) {
				//look for the wanted edge
				for(Edge<T> edge : node.getFollowing()) {

					if(edge._back == back) {

						removeEdge(edge);
					}

				}
			}
		}
	}
	
	public void removeNode(Node<T> node) {
		List<Edge<T>> followEdges = node.getFollowing();
		for (int i = 0; i < followEdges.size(); i++) {
			this.removeEdge(followEdges.get(i));
		}
		List<Edge<T>> precEdges = node.getFollowing();
		for (int i = 0; i < precEdges.size(); i++) {
			this.removeEdge(precEdges.get(i));
		}
		
		_nodes.remove(node);
	}

	//returns true if there is already an edge that links the Node front with the same value of back, else return false
	public boolean edgeExists(Node<T> front, Node<T> back) {
		
		boolean exists = false;
		
		for(Edge<T> edge : front.getFollowing()) {
			
			T val2 = edge.getBack().getValue();
			if(val2.equals(back.getValue())) {
				
				exists = true;
			}
		}
		
		return exists;
	}
	//returns true is node1 is adjacent to node2, else return false
	public boolean isAdjacent(Node<T> node1, Node<T> node2){

		if(node1.canLeadTo(node2) || node1.canComeFrom(node2))
			return true;
		else
			return false;
	}

	public List<Node<T>> getNodes(){

		return _nodes;
	}
	
	public Node<T> getStartingNode() {
		
		return _startingNode;
	}

	/*
	 * Finds a Node object by a ChordSymbol
	 *
	 */
	public Node<T> findNode(T chordsym){

		for(Node<T> node : _nodes) {

			if(node.getValue().equals(chordsym)) {

				return node;
			}
		}
		
		return null;
	}

//	//returns true if node1 connects to node2 in a certain number of steps, using bidirectional search
//	public boolean isConnectedBidirectional(Node node1, Node node2, int steps){
//
//		for(int i = 0; i <= steps; i++){
//
//
//		}
//	}
//
//	//returns true if node1 can lead to node2 in a certain number of steps
//	public boolean leadsTo(Node node1, Node node2, int steps){
//
//		if(steps < 0) //invalid input
//			return false;
//		else if(steps == 0){ //check if node1 directly leads to node2
//
//			if(node1.canLeadTo(node2))
//				return true;
//			else
//				return false;
//		}
//		else if(steps == 1){//check if node1 leads to a chord than can lead to chord 2
//
//			for(Node temp : node1.getFollowingChords()){
//				if(temp.canLeadTo(node2))
//					return true;
//			}
//			return false;
//		}
//		else{ //check if node1 leads to node2 in a certain number of steps
//
//			if(!(List followingchords = node1.getFollowingChords()).isEmpty()){
//
//				boolean connectionFound = false;
//				Node temp = followingchords.get(0);
//
//				while(!connectionFound && temp != followingchords.get(followingchords.size())){
//				}
//				return leadsTo(node1, node2)
//
//			}
//		}
//	}

}
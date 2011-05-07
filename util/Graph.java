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

	public Graph(){

		_nodes = new ArrayList<Node<T>>();
	}

	//Adds a Node to the Graph
	public void addNode(Node node) {

		_nodes.add(node);
	}

	//makes a connection between node1 and node2 with the specified weight.
	//An Edge is created for each node and added to the other node's adjacency list.
	//The shorter the weight, the more likely that edge (chord progression) will be used
	public void addEdge(Node<T> node1, Node<T> node2, int weight){


		if(!_nodes.contains(node1))
			_nodes.add(node1);

		if(!_nodes.contains(node2))
			_nodes.add(node2);

		Edge<T> edge = new Edge<T>(node1, node2, weight);
		node1.addFollowingEdge(edge);
		node2.addPrecedingEdge(edge);
	}

	//removes an edge within the graph, and removes that edge from the adjacency lists of the nodes it connects
	public void removeEdge(Edge<T> edge) {

		Node front = edge._front;
		front.removeFollowingEdge(edge);

		Node back = edge._back;
		back.removePrecedingEdge(edge);
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

	/*
	 * Finds a Node object by a ChordSymbol
	 *
	 */
	public Node findNode(ChordSymbol chordsym){

		Node returnNode = null;
		for(Node node : _nodes) {

			if(((ChordSymbol) node.getValue()).equals(chordsym)) {

				returnNode = node;
			}
		}

		return returnNode;
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
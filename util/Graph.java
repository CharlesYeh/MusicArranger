package util;

import java.util.*;
import music.*;

/*
 *This class represents the entire graph of chord progressions
 *
 */

public class Graph {

	List<Node> _chordGraph;

	public Graph(){

		_chordGraph = new ArrayList();
	}

	//makes a connection between node1 and node2 with the specified weight. An Edge is created for each node and added to the other node's adjacency list.
	public void addEdge(Node node1, Node node2, int weight){


		if(!_chordGraph.contains(node1))
			_chordGraph.add(node1);

		if(!_chordGraph.contains(node2))
			_chordGraph.add(node2);

		Edge edge = new Edge(node1, node2, weight);
		node1.addFollowingEdge(edge);
		node2.addPrecedingEdge(edge);
	}

	//returns true is node1 is adjacent to node2, else return false
	public boolean isAdjacent(Node node1, Node node2){

		if(node1.canLeadTo(node2) || node1.canComeFrom(node2))
			return true;
		else
			return false;
	}

//	//returns true if node1 connects to node2 in a certain number of steps, using bidirectional search
//	public boolean isConnectedBidirectional(Node node1, Node node2, int steps){
//
//		for(int i = 0; i <= steps; i++){
//
//
//		}
//	}

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
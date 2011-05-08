package util;

/*
 *This class is a node object for the chord progression graph. Each node will represent a specifc type of chord with
 *a specific inversion, and has a list of chords that can preceed it, as well as a list of chords that it can move into
 *
 */

import music.*;
import java.util.List;
import java.util.ArrayList;

public class Node<T>{

	T _value;
	List<Edge> _preceding;//a list of edges of the chords that can precede the current one
	List<Edge> _following;//a list of edges of the chords that the current one can lead to

	public Node(T v){

		_value = v;
		_preceding = new ArrayList();
		_following = new ArrayList();
	}

	public List<Edge> getPreceding(){
		return _preceding;
	}

	public T getValue(){

		return _value;
	}

	public List<Edge> getFollowing(){

		return _following;
	}

	//adds an Edge to the list _followingChords
	public void addFollowingEdge(Edge edge){

		if(!_following.contains(edge))
			_following.add(edge);
		else {
			System.out.println("edge to be added is already present in list _followingEdge");
		}
	}

	//adds an Edge to the list _precedingChords
	public void addPrecedingEdge(Edge edge){

		if(!_preceding.contains(edge))
		_preceding.add(edge);
		else {
			System.out.println("edge to be added is already present in list _precedingEdge");
		}
	}

	//returns true if current node leads to the given node, else return false
	public boolean canLeadTo(Node node){

		if(_following.contains(node))
			return true;
		else
			return false;
	}

	//returns true if current node can be preceded by the given node, else return false
	public boolean canComeFrom(Node node){

		if(_preceding.contains(node))
			return true;
		else
			return false;
	}

	//adds an Edge to the list _followingChords
	public void removeFollowingEdge(Edge edge){

		if(_following.contains(edge))
			_following.remove(edge);
	}

	//removes an Edge from the list _precedingChords
	public void removePrecedingEdge(Edge edge) {

		if(_preceding.contains(edge))
			_preceding.remove(edge);
	}
}
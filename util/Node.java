package util;

/*
 *This class is a node object for the chord progression graph. Each node will represent a specifc type of chord with
 *a specific inversion, and has a list of chords that can preceed it, as well as a list of chords that it can move into
 *
 */

import music.*;
import java.util.List;
import java.util.ArrayList;

public class Node{

	ChordSymbol _chordSymbol;
	List<Edge> _precedingChords;//a list of edges of the chords that can precede the current one
	List<Edge> _followingChords;//a list of edges of the chords that the current one can lead to

	public Node(ChordSymbol chordsymb){

		_chordSymbol = chordsymb;
		_precedingChords = new ArrayList();
		_followingChords = new ArrayList();
	}

	public List<Edge> getPrecedingChords(){

		return _precedingChords;
	}

	public List<Edge> getFollowigChords(){

		return _followingChords;
	}

	//adds an Edge to the list _followingChords
	public void addFollowingEdge(Edge edge){

		if(!_followingChords.contains(edge))
			_followingChords.add(edge);
		else {
			System.out.println("edge to be added is already present in list _followingEdge");
		}
	}

	//adds an Edge to the list _precedingChords
	public void addPrecedingEdge(Edge edge){

		if(!_precedingChords.contains(edge))
		_precedingChords.add(edge);
		else {
			System.out.println("edge to be added is already present in list _precedingEdge");
		}
	}

	//returns true if current node leads to the given node, else return false
	public boolean canLeadTo(Node node){

		if(_followingChords.contains(node))
			return true;
		else
			return false;
	}

	//returns true if current node can be preceded by the given node, else return false
	public boolean canComeFrom(Node node){

		if(_precedingChords.contains(node))
			return true;
		else
			return false;
	}
}
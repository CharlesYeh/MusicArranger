package util;

/*
 *This class is a weighted edge for a node. It contains a node, and a interger weight value that
 *arbitrarily represents the probability that a node connects to this one.
 *
 */

 public class Edge{

 	Node _adjacentChord;
 	int _weight;

 	public Edge(Node chord, int weight){

 		_adjacentChord = chord;
 		_weight = weight;
 	}
 }
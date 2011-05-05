package util;

/*
 *This class is a weighted edge for a node. It contains a node, and a interger weight value that
 *arbitrarily represents the probability that a node connects to this one.
 *
 */

 public class Edge{

 	Node _chord1;
 	Node _chord2;
 	int _weight;

 	public Edge(Node chord1, Node chord2, int weight){

 		_chord1 = chord1;
 		_chord2 = chord2;
 		_weight = weight;
 	}
 }
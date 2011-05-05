package util;

/*
 *This class is a weighted edge for a node. It contains a node, and a interger weight value that
 *arbitrarily represents the probability that a node connects to this one.
 *
 */

public class Edge<T>{

	Node<T> _front;
	Node<T> _back;
	int _weight;
	
	public Edge(Node<T> f, Node<T> b, int weight){
		_front	= f;
		_back	= b;
		_weight	= weight;
	}
}
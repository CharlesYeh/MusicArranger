package logic;

import java.lang.Thread;
import music.ChordSymbol;
import music.ChordType;
import music.Piece;
import util.*;

public class Analyzer extends Thread{

	Graph<ChordSymbol> _chordgraph;

	public Analyzer(){
		initMajorKeyGraph();
	}

	public void addChordSymbols(Piece p) {
		// go through piece matching notes to chords

	}

	private void initMajorKeyGraph() {
		_chordgraph = new Graph<ChordSymbol>();

		// create chords
		Node<ChordSymbol> chordI		= new Node<ChordSymbol>(new ChordSymbol(1, ChordType.MAJOR));
		Node<ChordSymbol> chordii		= new Node<ChordSymbol>(new ChordSymbol(2, ChordType.MINOR));
		Node<ChordSymbol> chordiii	= new Node<ChordSymbol>(new ChordSymbol(3, ChordType.MINOR));
		Node<ChordSymbol> chordIV		= new Node<ChordSymbol>(new ChordSymbol(4, ChordType.MAJOR));
		Node<ChordSymbol> chordV		= new Node<ChordSymbol>(new ChordSymbol(5, ChordType.MAJOR));
		Node<ChordSymbol> chordvi		= new Node<ChordSymbol>(new ChordSymbol(6, ChordType.MINOR));
		Node<ChordSymbol> chordviio	= new Node<ChordSymbol>(new ChordSymbol(7, ChordType.DIMIN));

		// V seven chords
		Node<ChordSymbol> chordV7		= new Node<ChordSymbol>(new ChordSymbol(5, ChordType.MAJOR7, 0));
		Node<ChordSymbol> chordV65	= new Node<ChordSymbol>(new ChordSymbol(5, ChordType.MAJOR7, 1));
		Node<ChordSymbol> chordV43	= new Node<ChordSymbol>(new ChordSymbol(5, ChordType.MAJOR7, 2));
		Node<ChordSymbol> chordV42	= new Node<ChordSymbol>(new ChordSymbol(5, ChordType.MAJOR7, 3));

		Node<ChordSymbol> chordN		= new Node<ChordSymbol>(new ChordSymbol(0, ChordType.NEAPOLITAN));
		Node<ChordSymbol> chordIt6	= new Node<ChordSymbol>(new ChordSymbol(0, ChordType.ITAUG6));
		Node<ChordSymbol> chordFr6	= new Node<ChordSymbol>(new ChordSymbol(0, ChordType.FRAUG6));
		Node<ChordSymbol> chordGer6	= new Node<ChordSymbol>(new ChordSymbol(0, ChordType.GERAUG6));

		// I -> I ii iii IV V vi viio N It6 Fr6 Ger6
		// ii -> V viio N It6 Fr6 Ger6
		// iii -> vi IV
		// iv -> I V viio ii N It6 Fr6 Ger6
		// V -> I vi
		// vi -> ii IV
		// vii -> I V ii N It6 Fr6 Ger6
		
		///Adding edges for chordI
		_chordgraph.addEdge(chordI, chordii, 	1);
		_chordgraph.addEdge(chordI, chordiii, 	1);
		_chordgraph.addEdge(chordI, chordIV, 	1);
		_chordgraph.addEdge(chordI, chordV, 	1);
		_chordgraph.addEdge(chordI, chordvi, 	1);
		_chordgraph.addEdge(chordI, chordviio, 	1);
		_chordgraph.addEdge(chordI, chordN, 	2);
		_chordgraph.addEdge(chordI, chordIt6, 	2);
		_chordgraph.addEdge(chordI, chordFr6, 	2);
		_chordgraph.addEdge(chordI, chordGer6, 	2);

		//Adding edges for chordii
		_chordgraph.addEdge(chordii, chordV, 	1);
		_chordgraph.addEdge(chordii, chordviio,	2);
		_chordgraph.addEdge(chordii, chordN, 	3);
		_chordgraph.addEdge(chordii, chordIt6, 	3);
		_chordgraph.addEdge(chordii, chordFr6, 	3);
		_chordgraph.addEdge(chordii, chordGer6,	3);

		//Adding edges for chordiii
		_chordgraph.addEdge(chordiii, chordvi, 	1);
		_chordgraph.addEdge(chordiii, chordIV, 	1);
		
		//Adding edges for chordIV
		_chordgraph.addEdge(chordIV, chordV, 	1);
		_chordgraph.addEdge(chordIV, chordii, 	1);
		_chordgraph.addEdge(chordIV, chordviio, 	1);
		_chordgraph.addEdge(chordIV, chordI, 	2); //plagal cadence
		_chordgraph.addEdge(chordIV, chordN, 	2);
		_chordgraph.addEdge(chordIV, chordIt6, 	2);
		_chordgraph.addEdge(chordIV, chordFr6, 	2);
		_chordgraph.addEdge(chordIV, chordGer6,	2);

		//Adding edges for chordV
		_chordgraph.addEdge(chordV, chordI, 	1); //Authentic cadence
		_chordgraph.addEdge(chordV, chordvi, 	2); //Deceptive cadence

		//Adding edges for chordvi
		_chordgraph.addEdge(chordvi, chordii, 	1);
		_chordgraph.addEdge(chordvi, chordIV, 	1);

		//Adding edges for chordviio
		_chordgraph.addEdge(chordviio, chordV, 	1);
		_chordgraph.addEdge(chordviio, chordI, 	1); //Authentic cadence (I don't think this is an AC? - Charles)
		_chordgraph.addEdge(chordviio, chordii,	1);
		_chordgraph.addEdge(chordviio, chordN, 	2);
		
	}

	private void initMinorKeyGraph() {
		_chordgraph = new Graph<ChordSymbol>();
		
		// create chords
		Node<ChordSymbol> chordi		= new Node<ChordSymbol>(new ChordSymbol(1, ChordType.MINOR));
		Node<ChordSymbol> chordiio	= new Node<ChordSymbol>(new ChordSymbol(2, ChordType.DIMIN));
		Node<ChordSymbol> chordIII	= new Node<ChordSymbol>(new ChordSymbol(3, ChordType.MAJOR));
		Node<ChordSymbol> chordiv		= new Node<ChordSymbol>(new ChordSymbol(4, ChordType.MINOR));
		Node<ChordSymbol> chordV		= new Node<ChordSymbol>(new ChordSymbol(5, ChordType.MAJOR));
		Node<ChordSymbol> chordVI		= new Node<ChordSymbol>(new ChordSymbol(6, ChordType.MAJOR));
		Node<ChordSymbol> chordviio	= new Node<ChordSymbol>(new ChordSymbol(7, ChordType.DIMIN));
		
		// V seven chords
		Node<ChordSymbol> chordV7		= new Node<ChordSymbol>(new ChordSymbol(5, ChordType.MAJOR7, 0));
		Node<ChordSymbol> chordV65	= new Node<ChordSymbol>(new ChordSymbol(5, ChordType.MAJOR7, 1));
		Node<ChordSymbol> chordV43	= new Node<ChordSymbol>(new ChordSymbol(5, ChordType.MAJOR7, 2));
		Node<ChordSymbol> chordV42	= new Node<ChordSymbol>(new ChordSymbol(5, ChordType.MAJOR7, 3));

		Node<ChordSymbol> chordN		= new Node<ChordSymbol>(new ChordSymbol(0, ChordType.NEAPOLITAN));
		Node<ChordSymbol> chordIt6	= new Node<ChordSymbol>(new ChordSymbol(0, ChordType.ITAUG6));
		Node<ChordSymbol> chordFr6	= new Node<ChordSymbol>(new ChordSymbol(0, ChordType.FRAUG6));
		Node<ChordSymbol> chordGer6	= new Node<ChordSymbol>(new ChordSymbol(0, ChordType.GERAUG6));
		
		///Adding edges for chordI
		_chordgraph.addEdge(chordi, chordiio, 	1);
		_chordgraph.addEdge(chordi, chordIII, 	1);
		_chordgraph.addEdge(chordi, chordiv, 	1);
		_chordgraph.addEdge(chordi, chordV, 	1);
		_chordgraph.addEdge(chordi, chordVI, 	1);
		_chordgraph.addEdge(chordi, chordviio, 	1);
		_chordgraph.addEdge(chordi, chordN, 	2);
		_chordgraph.addEdge(chordi, chordIt6, 	2);
		_chordgraph.addEdge(chordi, chordFr6, 	2);
		_chordgraph.addEdge(chordi, chordGer6, 	2);
		
		//Adding edges for chordii
		_chordgraph.addEdge(chordiio, chordV, 	1);
		_chordgraph.addEdge(chordiio, chordviio,	2);
		_chordgraph.addEdge(chordiio, chordN, 	3);
		_chordgraph.addEdge(chordiio, chordIt6, 	3);
		_chordgraph.addEdge(chordiio, chordFr6, 	3);
		_chordgraph.addEdge(chordiio, chordGer6,	3);
		
		//Adding edges for chordiii
		_chordgraph.addEdge(chordIII, chordVI, 	1);
		_chordgraph.addEdge(chordIII, chordiv, 	1);

		//Adding edges for chordIV
		_chordgraph.addEdge(chordiv, chordV, 	1);
		_chordgraph.addEdge(chordiv, chordiio, 	1);
		_chordgraph.addEdge(chordiv, chordviio,	1);
		_chordgraph.addEdge(chordiv, chordi, 	2); //plagal cadence
		_chordgraph.addEdge(chordiv, chordN, 	2);
		_chordgraph.addEdge(chordiv, chordIt6, 	2);
		_chordgraph.addEdge(chordiv, chordFr6, 	2);
		_chordgraph.addEdge(chordiv, chordGer6,	2);

		//Adding edges for chordV
		_chordgraph.addEdge(chordV, chordi, 	1); //Authentic cadence
		_chordgraph.addEdge(chordV, chordVI, 	2); //Deceptive cadence

		//Adding edges for chordvi
		_chordgraph.addEdge(chordVI, chordiio, 	1);
		_chordgraph.addEdge(chordVI, chordi, 	1);

		//Adding edges for chordviio
		_chordgraph.addEdge(chordviio, chordV, 	1);
		_chordgraph.addEdge(chordviio, chordi, 	1); //Authentic cadence (I don't think this is an AC? - Charles)
		_chordgraph.addEdge(chordviio, chordiio,1);
		_chordgraph.addEdge(chordviio, chordN, 	2);
		
		// I -> I ii iii IV V vi vii

		// ii -> IV V
		// iii -> vi
		// iv -> I V vi
		// V -> I
		// vi -> I V
		// vii -> I iv V
	}
	
	public void run() {
		// start analysis process here
		
	}
}
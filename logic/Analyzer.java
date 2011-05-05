package logic;

import music.ChordSymbol;
import music.ChordType;
import music.Piece;
import util.*;

public class Analyzer{

	//Graph<ChordSymbol> _chordPossibilities;

	public Analyzer(){
		initMajorKeyGraph();
	}

	public void addChordSymbols(Piece p) {
		// go through piece matching notes to chords

	}

	private void initMajorKeyGraph() {
		//_chordPossibilities = new Graph<ChordSymbol>();

		// create chords
		Node chordI	= new Node(new ChordSymbol(1, ChordType.MAJOR));
		Node chordii	= new Node(new ChordSymbol(2, ChordType.MINOR));
		Node chordiii	= new Node(new ChordSymbol(3, ChordType.MINOR));
		Node chordIV	= new Node(new ChordSymbol(4, ChordType.MAJOR));
		Node chordV	= new Node(new ChordSymbol(5, ChordType.MAJOR));
		Node chordvi	= new Node(new ChordSymbol(6, ChordType.MINOR));
		Node chordviio	= new Node(new ChordSymbol(7, ChordType.DIMIN));

		// V seven chords
		Node chordV7	= new Node(new ChordSymbol(5, ChordType.MAJOR7, 0));
		Node chordV65	= new Node(new ChordSymbol(5, ChordType.MAJOR7, 1));
		Node chordV43	= new Node(new ChordSymbol(5, ChordType.MAJOR7, 2));
		Node chordV42	= new Node(new ChordSymbol(5, ChordType.MAJOR7, 3));

		Node chordN	= new Node(new ChordSymbol(0, ChordType.NEAPOLITAN));
		Node chordIt6	= new Node(new ChordSymbol(0, ChordType.ITAUG6));
		Node chordFr6	= new Node(new ChordSymbol(0, ChordType.FRAUG6));
		Node chordGer6	= new Node(new ChordSymbol(0, ChordType.GERAUG6));

		// I -> I ii iii IV V vi viio N It6 Fr6 Ger6
		// ii -> V viio N It6 Fr6 Ger6
		// iii -> vi IV
		// iv -> I V viio ii N It6 Fr6 Ger6
		// V -> I vi
		// vi -> ii IV
		// vii -> I V ii N It6 Fr6 Ger6

		Graph chordgraph = new Graph();
		///Adding edges for chordI
		chordgraph.addEdge(chordI, chordii, 1);
		chordgraph.addEdge(chordI, chordiii, 1);
		chordgraph.addEdge(chordI, chordIV, 1);
		chordgraph.addEdge(chordI, chordV, 1);
		chordgraph.addEdge(chordI, chordvi, 1);
		chordgraph.addEdge(chordI, chordviio, 1);
		chordgraph.addEdge(chordI, chordN, 2);
		chordgraph.addEdge(chordI, chordIt6, 2);
		chordgraph.addEdge(chordI, chordFr6, 2);
		chordgraph.addEdge(chordI, chordGer6, 2);

		//Adding edges for chordii
		chordgraph.addEdge(chordii, chordV, 1);
		chordgraph.addEdge(chordii, chordviio, 2);
		chordgraph.addEdge(chordii, chordN, 3);
		chordgraph.addEdge(chordii, chordIt6, 3);
		chordgraph.addEdge(chordii, chordFr6, 3);
		chordgraph.addEdge(chordii, chordGer6, 3);

		//Adding edges for chordiii
		chordgraph.addEdge(chordiii, chordvi, 1);
		chordgraph.addEdge(chordiii, chordIV, 1);

		//Adding edges for chordIV
		chordgraph.addEdge(chordIV, chordV, 1);
		chordgraph.addEdge(chordIV, chordii, 1);
		chordgraph.addEdge(chordIV, chordviio, 1);
		chordgraph.addEdge(chordIV, chordI, 2); //plagal cadence
		chordgraph.addEdge(chordIV, chordN, 2);
		chordgraph.addEdge(chordIV, chordIt6, 2);
		chordgraph.addEdge(chordIV, chordFr6, 2);
		chordgraph.addEdge(chordIV, chordGer6, 2);

		//Adding edges for chordV
		chordgraph.addEdge(chordV, chordI, 1); //Authentic cadence
		chordgraph.addEdge(chordV, chordvi, 2); //Deceptive cadence

		//Adding edges for chordvi
		chordgraph.addEdge(chordvi, chordii, 1);
		chordgraph.addEdge(chordvi, chordIV, 1);

		//Adding edges for chordviio
		chordgraph.addEdge(chordviio, chordV, 1);
		chordgraph.addEdge(chordviio, chordI, 1); //Authentic cadence
		chordgraph.addEdge(chordviio, chordii, 1);
		chordgraph.addEdge(chordIV, chordN, 2);
		chordgraph.addEdge(chordIV, chordIt6, 2);
		chordgraph.addEdge(chordIV, chordFr6, 2);
		chordgraph.addEdge(chordIV, chordGer6, 2);

	}

	private void initMinorKeyGraph() {
		//_chordPossibilities = new Graph<ChordSymbol>();

		// create chords
		ChordSymbol chordi	= new ChordSymbol(0, ChordType.MINOR);
		ChordSymbol chordiio	= new ChordSymbol(0, ChordType.DIMIN);
		ChordSymbol chordIII	= new ChordSymbol(0, ChordType.MAJOR);
		ChordSymbol chordiv	= new ChordSymbol(0, ChordType.MINOR);
		ChordSymbol chordV	= new ChordSymbol(0, ChordType.MAJOR);
		ChordSymbol chordVI	= new ChordSymbol(0, ChordType.MAJOR);
		ChordSymbol chordviio	= new ChordSymbol(0, ChordType.DIMIN);

		// V seven chords
		ChordSymbol chordV7	= new ChordSymbol(0, ChordType.MAJOR7);
		ChordSymbol chordV65	= new ChordSymbol(0, ChordType.MAJOR7);
		ChordSymbol chordV43	= new ChordSymbol(0, ChordType.MAJOR7);
		ChordSymbol chordV42	= new ChordSymbol(0, ChordType.MAJOR7);

		ChordSymbol chordN	= new ChordSymbol(0, ChordType.NEAPOLITAN);
		ChordSymbol chordIt6	= new ChordSymbol(0, ChordType.ITAUG6);
		ChordSymbol chordFr6	= new ChordSymbol(0, ChordType.FRAUG6);
		ChordSymbol chordGer6	= new ChordSymbol(0, ChordType.GERAUG6);

		// I -> I ii iii IV V vi vii

		// ii -> IV V
		// iii -> vi
		// iv -> I V vi
		// V -> I
		// vi -> I V
		// vii -> I iv V
	}
}
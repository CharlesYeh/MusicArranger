package logic;

import util.*;
import music.*;
import instructions.GenerateInstructionType;
import instructions.InstructionIndex;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Collections;
import java.lang.Thread;

public class Analyzer extends Thread {
	
	Graph<ChordSymbol> _chordPreferencesGraph;
	GenerateInstructionType _generateType;
	Piece _piece;
	InstructionIndex _start;
	InstructionIndex _end;
	Rational _spacing;
	
	List<Pitch> _prevPitches = null;
	
	public Analyzer() {
		initMajorKeyGraph();
	}
	
	public void setPrevPitches(List<Pitch> prevPitches) {
		_prevPitches = prevPitches;
	}
	
	public void resetPrevPitches() {
		_prevPitches = null;
	}

	public void addChordSymbols(Piece p) {
		// go through piece matching notes to chords
		
	}
	
	public Graph<ChordSymbol> calculateAnalysisGraph(List<List<Pitch>> melody,
			KeySignature keySig) {
		return createPossibleProgressionsGraph(analyzeMelody(melody, keySig));
	}
	
	public List<List<Node<ChordSymbol>>> calculateAnalysisIndices(List<List<Pitch>> melody,
			KeySignature keySig) {
		Graph<ChordSymbol> analysisGraph = calculateAnalysisGraph(melody, keySig);
		List<List<Node<ChordSymbol>>> output = new ArrayList<List<Node<ChordSymbol>>>();
		
//		tests.unitTest.printGraph(analysisGraph);
		System.out.println("Performing analysis");
		for (int i = 0; i < melody.size(); i++) {
			output.add(getChordsAtIndex(analysisGraph, i));
		}
		
		return output;
	}
	
	public Graph<ChordSymbol> getChordPreferencesGraph() {
		
		return _chordPreferencesGraph;
	}

	public void initMajorKeyGraph() {
		_chordPreferencesGraph = new Graph<ChordSymbol>();

		// create chords
		Node<ChordSymbol> chordI		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(1, Accidental.NATURAL), ChordType.MAJOR));
		Node<ChordSymbol> chordii		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(2, Accidental.NATURAL), ChordType.MINOR));
		Node<ChordSymbol> chordiii		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(3, Accidental.NATURAL), ChordType.MINOR));
		Node<ChordSymbol> chordIV		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(4, Accidental.NATURAL), ChordType.MAJOR));
		Node<ChordSymbol> chordV		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(5, Accidental.NATURAL), ChordType.MAJOR));
		Node<ChordSymbol> chordvi		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(6, Accidental.NATURAL), ChordType.MINOR));
		Node<ChordSymbol> chordviio		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(7, Accidental.NATURAL), ChordType.DIMIN));

		// seventh chords
		Node<ChordSymbol> chordii7		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(2, Accidental.NATURAL), ChordType.MINOR7, 0));
		Node<ChordSymbol> chordV7		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(5, Accidental.NATURAL), ChordType.MAJORMINOR7, 0));
		Node<ChordSymbol> chordviiho7		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(7, Accidental.NATURAL), ChordType.HDIMIN7, 0));
//		Node<ChordSymbol> chordV65		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(5, Accidental.NATURAL), ChordType.MAJORMINOR7, 1));
//		Node<ChordSymbol> chordV43		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(5, Accidental.NATURAL), ChordType.MAJORMINOR7, 2));
//		Node<ChordSymbol> chordV42		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(5, Accidental.NATURAL), ChordType.MAJORMINOR7, 3));

		Node<ChordSymbol> chordN		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(2, Accidental.FLAT), ChordType.NEAPOLITAN));
		Node<ChordSymbol> chordIt6		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(6, Accidental.FLAT), ChordType.ITAUG6));
		Node<ChordSymbol> chordFr6		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(6, Accidental.FLAT), ChordType.FRAUG6));
		Node<ChordSymbol> chordGer6		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(6, Accidental.FLAT), ChordType.GERAUG6));

		// I -> I ii III iv V VI VIio N It6 Fr6 Ger6
		// ii -> V VIio N It6 Fr6 Ger6
		// III -> VI iv
		// iv -> I V VIio ii N It6 Fr6 Ger6
		// V -> I VI
		// VI -> ii iv
		// VIi -> I V ii N It6 Fr6 Ger6
		
		/*=====NOTE: to improve efficiency of finding optimal paths, 
		 * the path finder is build in a way that requires more optimal edges to be added before less optimal ones=====*/
		///Adding edges for chordI
		_chordPreferencesGraph.addEdge(chordI, chordI, 		1);
		_chordPreferencesGraph.addEdge(chordI, chordii, 	1);
		_chordPreferencesGraph.addEdge(chordI, chordiii, 	1);
		_chordPreferencesGraph.addEdge(chordI, chordIV, 	1);
		_chordPreferencesGraph.addEdge(chordI, chordV, 		1);
		_chordPreferencesGraph.addEdge(chordI, chordvi, 	1);
		_chordPreferencesGraph.addEdge(chordI, chordviio, 	1);
		_chordPreferencesGraph.addEdge(chordI, chordii7, 	1);
		_chordPreferencesGraph.addEdge(chordI, chordV7, 	1);
		_chordPreferencesGraph.addEdge(chordI, chordviiho7, 1);
		_chordPreferencesGraph.addEdge(chordI, chordN, 		2);
		_chordPreferencesGraph.addEdge(chordI, chordIt6, 	2);
		_chordPreferencesGraph.addEdge(chordI, chordFr6, 	2);
		_chordPreferencesGraph.addEdge(chordI, chordGer6, 	2);

		//Adding edges for chordii
		_chordPreferencesGraph.addEdge(chordii, chordii, 	1);
		_chordPreferencesGraph.addEdge(chordii, chordii7, 	1);
		_chordPreferencesGraph.addEdge(chordii, chordV, 	1);
		_chordPreferencesGraph.addEdge(chordii, chordV7, 	1);
		_chordPreferencesGraph.addEdge(chordii, chordviio,	2);
		_chordPreferencesGraph.addEdge(chordii, chordviiho7, 2);
		_chordPreferencesGraph.addEdge(chordii, chordN, 	3);
		_chordPreferencesGraph.addEdge(chordii, chordIt6, 	3);
		_chordPreferencesGraph.addEdge(chordii, chordFr6, 	3);
		_chordPreferencesGraph.addEdge(chordii, chordGer6,	3);
		
		//Adding edges for chordii7
		_chordPreferencesGraph.addEdge(chordii7, chordii7, 	1);
		_chordPreferencesGraph.addEdge(chordii7, chordii, 	1);
		_chordPreferencesGraph.addEdge(chordii7, chordV, 	1);
		_chordPreferencesGraph.addEdge(chordii7, chordV7, 	1);
		_chordPreferencesGraph.addEdge(chordii7, chordviio,	2);
		_chordPreferencesGraph.addEdge(chordii7, chordviiho7, 2);
		_chordPreferencesGraph.addEdge(chordii7, chordN, 	3);
		_chordPreferencesGraph.addEdge(chordii7, chordIt6, 	3);
		_chordPreferencesGraph.addEdge(chordii7, chordFr6, 	3);
		_chordPreferencesGraph.addEdge(chordii7, chordGer6,	3);

		//Adding edges for chordIII
		_chordPreferencesGraph.addEdge(chordiii, chordiii, 	1);
		_chordPreferencesGraph.addEdge(chordiii, chordvi, 	1);
		_chordPreferencesGraph.addEdge(chordiii, chordIV, 	1);

		//Adding edges for chordIV
		_chordPreferencesGraph.addEdge(chordIV, chordIV, 	1);
		_chordPreferencesGraph.addEdge(chordIV, chordV, 	1);
		_chordPreferencesGraph.addEdge(chordIV, chordV7, 	1);
		_chordPreferencesGraph.addEdge(chordIV, chordii, 	1);
		_chordPreferencesGraph.addEdge(chordIV, chordii7, 	1);
		_chordPreferencesGraph.addEdge(chordIV, chordviio, 	1);
		_chordPreferencesGraph.addEdge(chordIV, chordviiho7,1);
		_chordPreferencesGraph.addEdge(chordIV, chordI, 	2); //plagal cadence
		_chordPreferencesGraph.addEdge(chordIV, chordN, 	2);
		_chordPreferencesGraph.addEdge(chordIV, chordIt6, 	2);
		_chordPreferencesGraph.addEdge(chordIV, chordFr6, 	2);
		_chordPreferencesGraph.addEdge(chordIV, chordGer6,	2);

		//Adding edges for chordV
		_chordPreferencesGraph.addEdge(chordV, chordV, 		1);
		_chordPreferencesGraph.addEdge(chordV, chordV7, 		1);
		_chordPreferencesGraph.addEdge(chordV, chordI, 		1); //Authentic cadence
		_chordPreferencesGraph.addEdge(chordV, chordvi, 	2); //Deceptive cadence
		
		//Adding edges for chordV7
		_chordPreferencesGraph.addEdge(chordV7, chordV7, 	1);
		_chordPreferencesGraph.addEdge(chordV7, chordV, 	1);
		_chordPreferencesGraph.addEdge(chordV7, chordI, 	1); //Authentic cadence
		_chordPreferencesGraph.addEdge(chordV7, chordvi, 	2); //Deceptive cadence

		//Adding edges for chordvi
		_chordPreferencesGraph.addEdge(chordvi, chordvi, 	1);
		_chordPreferencesGraph.addEdge(chordvi, chordii, 	1);
		_chordPreferencesGraph.addEdge(chordvi, chordii7, 	1);
		_chordPreferencesGraph.addEdge(chordvi, chordiii, 	1);
		_chordPreferencesGraph.addEdge(chordvi, chordIV, 	1);

		//Adding edges for chordviio
		_chordPreferencesGraph.addEdge(chordviio, chordviio,1);
		_chordPreferencesGraph.addEdge(chordviio, chordviiho7,1);
		_chordPreferencesGraph.addEdge(chordviio, chordV, 	1);
		_chordPreferencesGraph.addEdge(chordviio, chordV7, 	1);
		_chordPreferencesGraph.addEdge(chordviio, chordI, 	1); //Authentic cadence
		_chordPreferencesGraph.addEdge(chordviio, chordiii,	1);
		_chordPreferencesGraph.addEdge(chordviio, chordN, 	2);
		_chordPreferencesGraph.addEdge(chordviio, chordIt6, 2);
		_chordPreferencesGraph.addEdge(chordviio, chordFr6, 2);
		_chordPreferencesGraph.addEdge(chordviio, chordGer6,2);
		
		//Adding edges for chordviiho7
		_chordPreferencesGraph.addEdge(chordviiho7, chordviiho7,1);
		_chordPreferencesGraph.addEdge(chordviiho7, chordviio,1);
		_chordPreferencesGraph.addEdge(chordviiho7, chordV, 	1);
		_chordPreferencesGraph.addEdge(chordviiho7, chordV7, 	1);
		_chordPreferencesGraph.addEdge(chordviiho7, chordI, 	1); //Authentic cadence
		_chordPreferencesGraph.addEdge(chordviiho7, chordiii,	1);
		_chordPreferencesGraph.addEdge(chordviiho7, chordN, 	2);
		_chordPreferencesGraph.addEdge(chordviiho7, chordIt6, 	2);
		_chordPreferencesGraph.addEdge(chordviiho7, chordFr6, 	2);
		_chordPreferencesGraph.addEdge(chordviiho7, chordGer6,	2);
		
		//Adding edges for chordN
		_chordPreferencesGraph.addEdge(chordN, chordN, 		1);
		_chordPreferencesGraph.addEdge(chordN, chordV, 		1);
		_chordPreferencesGraph.addEdge(chordN, chordV7, 	1);
		
		//Adding edges for chordIt6
		_chordPreferencesGraph.addEdge(chordIt6, chordIt6, 	1);
		_chordPreferencesGraph.addEdge(chordIt6, chordV, 	1);
		_chordPreferencesGraph.addEdge(chordIt6, chordV7, 	1);
		
		//Adding edges for chordFr6
		_chordPreferencesGraph.addEdge(chordFr6, chordFr6, 	1);
		_chordPreferencesGraph.addEdge(chordFr6, chordV, 	1);
		_chordPreferencesGraph.addEdge(chordFr6, chordV7, 	1);
		
		//Adding edges for chordGer6
		_chordPreferencesGraph.addEdge(chordGer6, chordGer6,1);
		_chordPreferencesGraph.addEdge(chordGer6, chordV, 	1);
		_chordPreferencesGraph.addEdge(chordGer6, chordV7, 	1);

	}

	public void initMinorKeyGraph() {
		_chordPreferencesGraph = new Graph<ChordSymbol>();

		// create chords
		Node<ChordSymbol> chordi		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(1, Accidental.NATURAL), ChordType.MINOR));
		Node<ChordSymbol> chordiio		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(2, Accidental.NATURAL), ChordType.DIMIN));
		Node<ChordSymbol> chordIII		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(3, Accidental.NATURAL), ChordType.MAJOR));
		Node<ChordSymbol> chordiv		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(4, Accidental.NATURAL), ChordType.MINOR));
		Node<ChordSymbol> chordV		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(5, Accidental.NATURAL), ChordType.MAJOR));
		Node<ChordSymbol> chordVI		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(6, Accidental.NATURAL), ChordType.MAJOR));
		Node<ChordSymbol> chordviio		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(7, Accidental.NATURAL), ChordType.DIMIN));

		// seventh chords
		Node<ChordSymbol> chordiiho7		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(2, Accidental.NATURAL), ChordType.HDIMIN7, 0));
		Node<ChordSymbol> chordV7		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(5, Accidental.NATURAL), ChordType.MAJORMINOR7, 0));
		Node<ChordSymbol> chordviio7		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(7, Accidental.NATURAL), ChordType.DIMIN7, 0));
//		Node<ChordSymbol> chordV65		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(5, Accidental.NATURAL), ChordType.MAJORMINOR7, 1));
//		Node<ChordSymbol> chordV43		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(5, Accidental.NATURAL), ChordType.MAJORMINOR7, 2));
//		Node<ChordSymbol> chordV42		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(5, Accidental.NATURAL), ChordType.MAJORMINOR7, 3));

		Node<ChordSymbol> chordN		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(2, Accidental.FLAT), ChordType.NEAPOLITAN));
		Node<ChordSymbol> chordIt6		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(6, Accidental.NATURAL), ChordType.ITAUG6));
		Node<ChordSymbol> chordFr6		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(6, Accidental.NATURAL), ChordType.FRAUG6));
		Node<ChordSymbol> chordGer6		= new Node<ChordSymbol>(new ChordSymbol(new ScaleDegree(6, Accidental.NATURAL), ChordType.GERAUG6));

		// i -> iio III iv V VI viio N It6 Fr6 Ger6
		// iio -> V viio N It6 Fr6 Ger6
		// III -> VI iv
		// iv -> I V viio iio N It6 Fr6 Ger6
		// V -> I VI
		// VI -> iio iv
		// VIi -> I V iio N It6 Fr6 Ger6
		
		/*=====NOTE: to improve efficiency of finding optimal paths, 
		 * the path finder is build in a way that requires more optimal edges to be added before less optimal ones=====*/
		//Adding edges for chordi
		_chordPreferencesGraph.addEdge(chordi, chordi, 		1);
		_chordPreferencesGraph.addEdge(chordi, chordiio, 	1);
		_chordPreferencesGraph.addEdge(chordi, chordiiho7, 	1);
		_chordPreferencesGraph.addEdge(chordi, chordIII, 	1);
		_chordPreferencesGraph.addEdge(chordi, chordiv, 	1);
		_chordPreferencesGraph.addEdge(chordi, chordV, 		1);
		_chordPreferencesGraph.addEdge(chordi, chordV7, 	1);
		_chordPreferencesGraph.addEdge(chordi, chordVI, 	1);
		_chordPreferencesGraph.addEdge(chordi, chordviio, 	1);
		_chordPreferencesGraph.addEdge(chordi, chordviio7, 	1);
		_chordPreferencesGraph.addEdge(chordi, chordN, 		2);
		_chordPreferencesGraph.addEdge(chordi, chordIt6, 	2);
		_chordPreferencesGraph.addEdge(chordi, chordFr6, 	2);
		_chordPreferencesGraph.addEdge(chordi, chordGer6, 	2);

		//Adding edges for chordiio
		_chordPreferencesGraph.addEdge(chordiio, chordiio, 	1);
		_chordPreferencesGraph.addEdge(chordiio, chordiiho7,1);
		_chordPreferencesGraph.addEdge(chordiio, chordV, 	1);
		_chordPreferencesGraph.addEdge(chordiio, chordV7, 	1);
		_chordPreferencesGraph.addEdge(chordiio, chordviio,	2);
		_chordPreferencesGraph.addEdge(chordiio, chordviio7,2);
		_chordPreferencesGraph.addEdge(chordiio, chordN, 	3);
		_chordPreferencesGraph.addEdge(chordiio, chordIt6, 	3);
		_chordPreferencesGraph.addEdge(chordiio, chordFr6, 	3);
		_chordPreferencesGraph.addEdge(chordiio, chordGer6,	3);
		
		//Adding edges for chordiio7
		_chordPreferencesGraph.addEdge(chordiiho7, chordiiho7, 	1);
		_chordPreferencesGraph.addEdge(chordiiho7, chordiio, 	1);
		_chordPreferencesGraph.addEdge(chordiiho7, chordV, 		1);
		_chordPreferencesGraph.addEdge(chordiiho7, chordV7, 	1);
		_chordPreferencesGraph.addEdge(chordiiho7, chordviio,	2);
		_chordPreferencesGraph.addEdge(chordiiho7, chordviio7,	2);
		_chordPreferencesGraph.addEdge(chordiiho7, chordN, 		3);
		_chordPreferencesGraph.addEdge(chordiiho7, chordIt6, 	3);
		_chordPreferencesGraph.addEdge(chordiiho7, chordFr6, 	3);
		_chordPreferencesGraph.addEdge(chordiiho7, chordGer6,	3);

		//Adding edges for chordIII
		_chordPreferencesGraph.addEdge(chordIII, chordIII, 	1);
		_chordPreferencesGraph.addEdge(chordIII, chordVI, 	1);
		_chordPreferencesGraph.addEdge(chordIII, chordiv, 	1);

		//Adding edges for chordiv
		_chordPreferencesGraph.addEdge(chordiv, chordiv, 	1);
		_chordPreferencesGraph.addEdge(chordiv, chordV, 	1);
		_chordPreferencesGraph.addEdge(chordiv, chordV7, 	1);
		_chordPreferencesGraph.addEdge(chordiv, chordiio, 	1);
		_chordPreferencesGraph.addEdge(chordiv, chordiiho7, 1);
		_chordPreferencesGraph.addEdge(chordiv, chordviio,	1);
		_chordPreferencesGraph.addEdge(chordiv, chordviio7,	1);
		_chordPreferencesGraph.addEdge(chordiv, chordi, 	2); //plagal cadence
		_chordPreferencesGraph.addEdge(chordiv, chordN, 	2);
		_chordPreferencesGraph.addEdge(chordiv, chordIt6, 	2);
		_chordPreferencesGraph.addEdge(chordiv, chordFr6, 	2);
		_chordPreferencesGraph.addEdge(chordiv, chordGer6,	2);

		//Adding edges for chordV
		_chordPreferencesGraph.addEdge(chordV, chordV, 		1);
		_chordPreferencesGraph.addEdge(chordV, chordV7, 	1);
		_chordPreferencesGraph.addEdge(chordV, chordi, 		1); //Authentic cadence
		_chordPreferencesGraph.addEdge(chordV, chordVI, 	2); //Deceptive cadence
		
		//Adding edges for chordV7
		_chordPreferencesGraph.addEdge(chordV7, chordV7, 	1);
		_chordPreferencesGraph.addEdge(chordV7, chordV, 	1);
		_chordPreferencesGraph.addEdge(chordV7, chordi, 	1); //Authentic cadence
		_chordPreferencesGraph.addEdge(chordV7, chordVI, 	2); //Deceptive cadence

		//Adding edges for chordVI
		_chordPreferencesGraph.addEdge(chordVI, chordVI, 	1);
		_chordPreferencesGraph.addEdge(chordVI, chordiio, 	1);
		_chordPreferencesGraph.addEdge(chordVI, chordi, 	1);

		//Adding edges for chordviio
		_chordPreferencesGraph.addEdge(chordviio, chordviio,1);
		_chordPreferencesGraph.addEdge(chordviio, chordviio7,1);
		_chordPreferencesGraph.addEdge(chordviio, chordV, 	1);
		_chordPreferencesGraph.addEdge(chordviio, chordV7, 	1);
		_chordPreferencesGraph.addEdge(chordviio, chordi, 	1); //Authentic cadence
		_chordPreferencesGraph.addEdge(chordviio, chordN, 	2);
		_chordPreferencesGraph.addEdge(chordviio, chordIt6, 2);
		_chordPreferencesGraph.addEdge(chordviio, chordFr6, 2);
		_chordPreferencesGraph.addEdge(chordviio, chordGer6,2);
		
		//Adding edges for chordviio7
		_chordPreferencesGraph.addEdge(chordviio7, chordviio7, 	1);
		_chordPreferencesGraph.addEdge(chordviio7, chordviio, 	1);
		_chordPreferencesGraph.addEdge(chordviio7, chordV, 		1);
		_chordPreferencesGraph.addEdge(chordviio7, chordV7, 	1);
		_chordPreferencesGraph.addEdge(chordviio7, chordi, 		1); //Authentic cadence
		_chordPreferencesGraph.addEdge(chordviio7, chordN, 		2); 
		_chordPreferencesGraph.addEdge(chordviio7, chordIt6, 	2);
		_chordPreferencesGraph.addEdge(chordviio7, chordFr6, 	2);
		_chordPreferencesGraph.addEdge(chordviio7, chordGer6,	2);
		
		//Adding edges for chordN
		_chordPreferencesGraph.addEdge(chordN, chordN, 	1);
		_chordPreferencesGraph.addEdge(chordN, chordV, 	1);
		_chordPreferencesGraph.addEdge(chordN, chordV7, 	1);
		
		//Adding edges for chordIt6
		_chordPreferencesGraph.addEdge(chordIt6, chordIt6, 	1);
		_chordPreferencesGraph.addEdge(chordIt6, chordV, 	1);
		_chordPreferencesGraph.addEdge(chordIt6, chordV7, 	1);
		
		//Adding edges for chordFr6
		_chordPreferencesGraph.addEdge(chordFr6, chordFr6, 	1);
		_chordPreferencesGraph.addEdge(chordFr6, chordV, 	1);
		_chordPreferencesGraph.addEdge(chordFr6, chordV7, 	1);
		
		//Adding edges for chordGer6
		_chordPreferencesGraph.addEdge(chordGer6, chordGer6, 	1);
		_chordPreferencesGraph.addEdge(chordGer6, chordV, 	1);
		_chordPreferencesGraph.addEdge(chordGer6, chordV7, 	1);

	}

	//Takes in a List of ChordSymbol objects, a Pitch object, and a KeySignature object,
	//and removes all ChordSymbol objects in the list that represent chords that do not contain the given pitch
	public void filterChords(List<ChordSymbol> chords, Pitch pitch,
			KeySignature keySig) {
		int numChords = chords.size();
		ListIterator<ChordSymbol> iter = chords.listIterator();
		
		for (int i = 0; i < numChords; i++) {
			ChordSymbol nextChord = iter.next();
			List<Pitch> assocPitches = getChordPitches(nextChord, keySig);
			boolean containsPitch = false;
			
			for (Pitch assocPitch : assocPitches) {
				containsPitch = containsPitch || pitch.equalsName(assocPitch);
			}
			
			if (!containsPitch) {
				iter.remove();
			}
		}
	}
	
	// Each list of pitches is turned into a List<ChordSymbol>, which represent the possible
	// chords it could belong to.
	public List<List<ChordSymbol>> analyzeMelody(List<List<Pitch>> melodyLine,
			KeySignature keySig) {
		
		if (keySig.getIsMajor()) {
			initMajorKeyGraph();
		}
		else {
			initMinorKeyGraph();
		}
		
		ArrayList<List<ChordSymbol>> output = new ArrayList<List<ChordSymbol>>();
		for(List<Pitch> melodyInstance : melodyLine) { //for each instance in the melody
			
			List<ChordSymbol> possibleChords = findMatchingChords(melodyInstance, keySig);
			
			//add this List of ChordSymbol objects to the output list
			output.add(possibleChords);
		}
		
		return output;

	}
	
	//Takes in a list of pitches, and returns a list of chordSymbols that represent Chords that contain those pitches
	public List<ChordSymbol> findMatchingChords(List<Pitch> pitches, KeySignature keySig) {
		
		//get all the nodes that are possible within the _chordPreferencesGraph
		List<Node<ChordSymbol>> chordNodes = _chordPreferencesGraph.getNodes();
		List<ChordSymbol> possibleChords = new LinkedList<ChordSymbol>();
		for (Node<ChordSymbol> chordNode : chordNodes) {
			possibleChords.add(chordNode.getValue());
		}
		
		//filter out the chords by going through each of the pitches in that melody instance 
		for(Pitch pitch : pitches) {
			
			filterChords(possibleChords, pitch, keySig);
		}
		
		return possibleChords;
	}
	
	// Given a pitch and the key it belongs to, returns the scale degree of that pitch.
	public ScaleDegree calcScaleDegree(Pitch pitch, Pitch key, boolean isMajor) {
		int letterDif = letterDifference(pitch, key);
		int pitchDif = pitchDifference(pitch, key);
		
		int scaleDegreeNumber = letterDif;
		
		int dfaultPitchDif;
		// pitch difference if the letter difference were to have no accidental
		
		if (isMajor) {
			dfaultPitchDif = NoteLetter.getNoteLetter(letterDif).pitchValue();
		}
		else { // if minor
			letterDif  = (letterDif - 2) % 7;
			if (letterDif < 0) letterDif += 7;
			dfaultPitchDif = (NoteLetter.getNoteLetter(letterDif).pitchValue() -
				NoteLetter.A.pitchValue()) % 12;
			if (dfaultPitchDif < 0) dfaultPitchDif += 12;
		}
		int accidentalValue = pitchDif - dfaultPitchDif;
		Accidental accidental = Accidental.getAccidental(accidentalValue);

		return new ScaleDegree(scaleDegreeNumber, accidental);
	}
	
	// Returns the letter difference between two pitches
	// For example, letterDifference between F# and Bb is 4 (four letters to get from F to B)
	public int letterDifference(Pitch pitch1, Pitch pitch2) {
		int pitch1NoteValue = pitch1.getNoteLetter().intValue();
		int pitch2NoteValue = pitch2.getNoteLetter().intValue();

		int letterDif = (pitch1NoteValue - pitch2NoteValue) % 7;
		if (letterDif < 0) letterDif += 7;
		
		return letterDif;
	}
	
	// Returns the pitch difference between two pitches (in half steps), DISREGARDING OCTAVES
	// For example, pitchDifference between F# and Bb is 8
	public int pitchDifference(Pitch pitch1, Pitch pitch2) {
		int pitch1PitchValue = pitch1.getNoteLetter().pitchValue() + pitch1.getAccidental().intValue();
		int pitch2PitchValue = pitch2.getNoteLetter().pitchValue() + pitch2.getAccidental().intValue();
		
		int pitchDif = (pitch1PitchValue - pitch2PitchValue) % 12;
		if (pitchDif < 0) pitchDif += 12;
		
		return pitchDif;
	}
	
	public List<Pitch> getChordPitches(ChordSymbol chordSymbol, KeySignature keySig){
		boolean isMajor = keySig.getIsMajor();
		List<Pitch> chordPitches = new ArrayList<Pitch>();
		
		ChordType chordType = chordSymbol.getChordType();
		Pitch keySigPitch = keySig.getKeySigPitch();
		Pitch rootNote = keySigPitch.addInterval(chordSymbol.getScaleDegree().getIntervalFromRoot(isMajor));
		
		
		switch (chordType) {
			case MAJOR:
				chordPitches.add(rootNote);
				chordPitches.add(rootNote.addInterval(new Interval(IntervalType.MAJOR, 3)));
				chordPitches.add(rootNote.addInterval(new Interval(IntervalType.PERFECT, 5)));
				break;

			case MINOR:
				chordPitches.add(rootNote);
				chordPitches.add(rootNote.addInterval(new Interval(IntervalType.MINOR, 3)));
				chordPitches.add(rootNote.addInterval(new Interval(IntervalType.PERFECT, 5)));
				break;

			case DIMIN:
				chordPitches.add(rootNote);
				chordPitches.add(rootNote.addInterval(new Interval(IntervalType.MINOR, 3)));
				chordPitches.add(rootNote.addInterval(new Interval(IntervalType.DIMINISHED, 5)));
				break;

			case MAJOR7:
				chordPitches.add(rootNote);
				chordPitches.add(rootNote.addInterval(new Interval(IntervalType.MAJOR, 3)));
				chordPitches.add(rootNote.addInterval(new Interval(IntervalType.PERFECT, 5)));
				chordPitches.add(rootNote.addInterval(new Interval(IntervalType.MAJOR, 7)));
				break;

			case MAJORMINOR7:
				chordPitches.add(rootNote);
				chordPitches.add(rootNote.addInterval(new Interval(IntervalType.MAJOR, 3)));
				chordPitches.add(rootNote.addInterval(new Interval(IntervalType.PERFECT, 5)));
				chordPitches.add(rootNote.addInterval(new Interval(IntervalType.MINOR, 7)));
				break;

			case MINOR7:
				chordPitches.add(rootNote);
				chordPitches.add(rootNote.addInterval(new Interval(IntervalType.MINOR, 3)));
				chordPitches.add(rootNote.addInterval(new Interval(IntervalType.PERFECT, 5)));
				chordPitches.add(rootNote.addInterval(new Interval(IntervalType.MINOR, 7)));
				break;

			case DIMIN7:
				chordPitches.add(rootNote);
				chordPitches.add(rootNote.addInterval(new Interval(IntervalType.DIMINISHED, 3)));
				chordPitches.add(rootNote.addInterval(new Interval(IntervalType.DIMINISHED, 5)));
				chordPitches.add(rootNote.addInterval(new Interval(IntervalType.DIMINISHED, 7)));
				break;
				
			case NEAPOLITAN:
				chordPitches.add(rootNote);
				chordPitches.add(rootNote.addInterval(new Interval(IntervalType.MAJOR, 3)));
				chordPitches.add(rootNote.addInterval(new Interval(IntervalType.PERFECT, 5)));
				break;

			case HDIMIN7:
				chordPitches.add(rootNote);
				chordPitches.add(rootNote.addInterval(new Interval(IntervalType.DIMINISHED, 3)));
				chordPitches.add(rootNote.addInterval(new Interval(IntervalType.DIMINISHED, 5)));
				chordPitches.add(rootNote.addInterval(new Interval(IntervalType.MINOR, 7)));
				break;

			case ITAUG6:
				chordPitches.add(rootNote);
				chordPitches.add(rootNote.addInterval(new Interval(IntervalType.MAJOR, 3)));
				chordPitches.add(rootNote.addInterval(new Interval(IntervalType.AUGMENTED, 6)));
				break;

			case FRAUG6:
				chordPitches.add(rootNote);
				chordPitches.add(rootNote.addInterval(new Interval(IntervalType.MAJOR, 3)));
				chordPitches.add(rootNote.addInterval(new Interval(IntervalType.AUGMENTED, 4)));
				chordPitches.add(rootNote.addInterval(new Interval(IntervalType.AUGMENTED, 6)));
				break;

			case GERAUG6:
				chordPitches.add(rootNote);
				chordPitches.add(rootNote.addInterval(new Interval(IntervalType.MAJOR, 3)));
				chordPitches.add(rootNote.addInterval(new Interval(IntervalType.PERFECT, 5)));
				chordPitches.add(rootNote.addInterval(new Interval(IntervalType.AUGMENTED, 6)));
				break;

		}

		return chordPitches;
	}

	public Graph<ChordSymbol> createPossibleProgressionsGraph(List<List<ChordSymbol>> chordListsList){

		Graph<ChordSymbol> output = new Graph<ChordSymbol>();

		//convert matchingChordList to a list of equal dimension with each ChordSymbol encased in a Node structure
		List<List<Node<ChordSymbol>>> chordNodeListList = new ArrayList<List<Node<ChordSymbol>>>();
		for(List<ChordSymbol> matchingChords : chordListsList) {

			List<Node<ChordSymbol>> matchingNodes = new ArrayList<Node<ChordSymbol>>();
			for(ChordSymbol chordsym : matchingChords) {

				Node<ChordSymbol> node = new Node<ChordSymbol>(chordsym);
				matchingNodes.add(node);
			}
			chordNodeListList.add(matchingNodes);
		}


		//use the new list of possible chord nodes to create the the matchingProgressions graph

		//first add the nodes of the list of chords that match the first note into the Graph.
		//These are the beggining chords for all of the chord progressions that can be potentially generated.
		
		ChordSymbol blank = new ChordSymbol(new ScaleDegree(0), ChordType.BLANK);
		Node<ChordSymbol> blankNode = new Node<ChordSymbol>(blank);
		
		output.setStartingNode(blankNode);

		List<Node<ChordSymbol>> firstChordNodes = chordNodeListList.get(0);
		for(Node<ChordSymbol> node : firstChordNodes) {
			output.addToStartingNode(node, 1);
		}
		
		joinChordNodeLists(output, 0, 1, chordNodeListList);

		return output;
	}
	
	public void joinChordNodeLists(Graph<ChordSymbol> graph,
			int prevIndex, int nextIndex, List<List<Node<ChordSymbol>>> chordNodeListList) {
		List<Node<ChordSymbol>> prevNodes = chordNodeListList.get(prevIndex);
		List<Node<ChordSymbol>> nextNodes = chordNodeListList.get(nextIndex);
		
		for (Node<ChordSymbol> prevNode : prevNodes) {
			Node<ChordSymbol> preferenceGraphNode = _chordPreferencesGraph.findNode(prevNode.getValue());
			
			List<Edge<ChordSymbol>> prefEdges = preferenceGraphNode.getFollowing();
			List<ChordSymbol> possibleChords = new ArrayList<ChordSymbol>();
			for (Edge<ChordSymbol> edge : prefEdges) {
				possibleChords.add(edge.getBack().getValue());
			}
			boolean contains;
			for(Node<ChordSymbol> nextNode : nextNodes) {
				contains = false;
				for(ChordSymbol possibleChord : possibleChords) {
					if (possibleChord.equals(nextNode.getValue())) {
						contains = true;
						break;
					}
				}
				if(contains) { 
					graph.addEdge(prevNode, nextNode, 1); //valid progression, add edge to the return graph
				}
			}
			if (prevNode.getFollowing().size() == 0 || prevNode.getPreceding().size() == 0) {
				deleteBranches(graph, prevNode);
			}
		}
		
		if (nextIndex < chordNodeListList.size() - 1) {
			joinChordNodeLists(graph, prevIndex + 1, nextIndex + 1, chordNodeListList);
		}		
	}
	
	public void deleteBranches(Graph<ChordSymbol> graph, Node<ChordSymbol> node) {

//		List<Edge<ChordSymbol>> followEdges = node.getFollowing();
//		List<Edge<ChordSymbol>> precEdges = node.getPreceding();
//		List<Node<ChordSymbol>> adjacencies = new ArrayList<Node<ChordSymbol>>();
//		for (Edge<ChordSymbol> edge : followEdges) {
//			adjacencies.add(edge.getBack());
//		}
//		for (Edge<ChordSymbol> edge : precEdges) {
//			adjacencies.add(edge.getFront());
//		}
//		graph.removeNode(node);
//		
//		for (Node<ChordSymbol> adjacency : adjacencies) {
//			if (adjacency.getFollowing().size() == 0 || adjacency.getPreceding().size() == 0) {
//				deleteBranches(graph, adjacency);
//			}
//		}
	}

	//Takes a Graph and an Index, and return all the Nodes at an index number of levels away from the starting Node. 
	public List<Node<ChordSymbol>> getChordsAtIndex(Graph<ChordSymbol> chordProgressionGraph, int index) {
		
		Node<ChordSymbol> startingNode = chordProgressionGraph.getStartingNode();
		List<Node<ChordSymbol>> returnNodes = null;
		List<Node<ChordSymbol>> firstNodes = new ArrayList<Node<ChordSymbol>>();
		
		if(index < 0 || startingNode == null) { //invalid input
			
			return null;
		}
			
		// gets the first level of nodes
		List<Edge<ChordSymbol>> startingEdges = startingNode.getFollowing();
		for(Edge<ChordSymbol> edge : startingEdges) {
			
			firstNodes.add(edge.getBack());
		}
		if(index == 0) { 
			
			// return the first level of nodes
			returnNodes = firstNodes;
		}
		else if(index > 0) { //must iterate through the graph to find the wanted Node objects
			
			returnNodes = getChordsAtIndexHelper(firstNodes, 1, index);
		}
		
		return returnNodes;
	}
	
	
	/*
	 *  Helper function for getChordsAtIndex, takes a List of Node objects, the current index, 
	 *	and the query index, and returns a List of Nodes that are at the query Index number
	 *  of levels away from the root Node of the Graph
	 */
	private List<Node<ChordSymbol>> getChordsAtIndexHelper(List<Node<ChordSymbol>> currentNodes, int currentIndex, int queryIndex) {
		
//		boolean hasNext = false; //boolean to check if the currentNode leads to another level in the Graph
		List<Node<ChordSymbol>> nextNodes = new ArrayList<Node<ChordSymbol>>();
		
		for(Node<ChordSymbol> currentNode : currentNodes) {
			
			// gets the next level of nodes
			List<Edge<ChordSymbol>> startingEdges = currentNode.getFollowing();
			for(Edge<ChordSymbol> edge : startingEdges) {
				
				Node<ChordSymbol> nextNode = edge.getBack();
				if(!nextNodes.contains(nextNode)) {
					
					// only add to the List nextNodes when nextNodes does not already contain the Node
					nextNodes.add(nextNode);
				}
			}
		}
		
		if(currentIndex < queryIndex) {
			
			nextNodes = getChordsAtIndexHelper(nextNodes, currentIndex + 1, queryIndex);
		}
		
		return nextNodes;
	}

	/* 
	 * Takes a Graph and truncates it such that only the specified ChordSymbol is represented at the index number of levels from the starting Node
	 * 
	 */
	public Graph<ChordSymbol> setChordsAtIndex(ChordSymbol targetChord, Graph<ChordSymbol> progressionsGraph, int index) {
		
		Graph<ChordSymbol> returnGraph = new Graph<ChordSymbol>();
		// get the chords that are present at the specific index in the Graph
		List<Node<ChordSymbol>> chordsAtIndex = getChordsAtIndex(progressionsGraph, index);
		
		if(!chordsAtIndex.isEmpty()) {
			
			// boolean to determine whether the chordsAtIndex contain the wanted chordSymbol
			boolean hasChordSymbol = false;
			Node<ChordSymbol> determinedNode = null;
			for(Node<ChordSymbol> node : chordsAtIndex) {
				
				if(node.getValue().equals(targetChord)) {
					
					hasChordSymbol = true;
					determinedNode = node;
					break;
				}
			}
			
			if(hasChordSymbol) {
				
				// returns a Graph that has all the edges that link to determinedNode
				returnGraph = buildGraphFromNode(determinedNode);
			}
		}
		
		// returns empty Graph if the progressionsGraph does not contain any nodes at the specified index
		return returnGraph;
	}
	
	/*
	 * Takes a Node and returns a Graph that contains all the edges that link to Node
	 * 
	 */
	public Graph<ChordSymbol> buildGraphFromNode(Node<ChordSymbol> determinedNode) {
		
		Graph<ChordSymbol> newGraph = new Graph<ChordSymbol>();
		Node<ChordSymbol> newCurrentNode = new Node<ChordSymbol>(determinedNode.getValue());
		addFollowingEdges(newGraph, newCurrentNode, determinedNode.getFollowing());
		addPrecedingEdges(newGraph, newCurrentNode, determinedNode.getPreceding());
		return newGraph;
	}
	
	/*
	 * Adds the Edges that follow the given Node to the given Graph
	 */
	private void addFollowingEdges(Graph<ChordSymbol> newGraph, Node<ChordSymbol> currentNode, List<Edge<ChordSymbol>> followingEdges) {
		
		for(Edge<ChordSymbol> edge : followingEdges) {
			
			Node<ChordSymbol> nextNode = edge.getBack();
			
			// create new Nodes for the new Graph
			Node<ChordSymbol> newNextNode = new Node<ChordSymbol>(nextNode.getValue());
			
			newGraph.addEdge(currentNode, newNextNode, edge.getWeight());
			// continue adding the edges following the next Nodes
			addFollowingEdges(newGraph, newNextNode, nextNode.getFollowing());
		}
	}
	
	/*
	 * Adds the Edges that precede the given Node to the given Graph, and links the frontMost Node to the startingNode of the Graph
	 * 
	 */
	private void addPrecedingEdges(Graph<ChordSymbol> newGraph, Node<ChordSymbol> currentNode, List<Edge<ChordSymbol>> precedingEdges) {
		
		for(int i = 0; i < precedingEdges.size(); i++) {
			
			Edge<ChordSymbol> edge = precedingEdges.get(i);
			Node<ChordSymbol> previousNode = edge.getFront();
			
			// create new Nodes for the new Graph
			Node<ChordSymbol> newPrevNode = new Node<ChordSymbol>(previousNode.getValue());
			if(previousNode.getPreceding().isEmpty()) {// This node should be a starting Node
				
				// add this node to the starting Nodes of the Graph
				newGraph.addToStartingNode(currentNode, edge.getWeight());
				
			}
			else { // This node is not a starting Node
				
				// check if there is already a level one node that links to currentNode's value
				boolean startingNodeLinksToCurrent = false;
				
				Node<ChordSymbol> twoStepsPrecedingNode = previousNode.getPreceding().get(0).getFront();
				if(twoStepsPrecedingNode.getPreceding().isEmpty()) { // check if the previous chord is a level one node
					
					Node<ChordSymbol> startingNode = newGraph.getStartingNode();
					if(startingNode != null) {
						for(Edge<ChordSymbol> levelOneEdge : startingNode.getFollowing()) { // find a level one node that 
							
							Node<ChordSymbol> levelOneNode = levelOneEdge.getBack();
							if(levelOneNode.getValue().equals(previousNode.getValue())) {
								
								newGraph.addEdge(levelOneNode, currentNode, edge.getWeight());
								startingNodeLinksToCurrent = true;
//								addPrecedingEdges(newGraph, levelOneNode, previousNode.getPreceding());
//								break;
							}
						}
					}
				}
				if(!startingNodeLinksToCurrent) {
					newGraph.addEdge(newPrevNode, currentNode, edge.getWeight());
					// continue adding the edges preceding the previous Nodes
					addPrecedingEdges(newGraph, newPrevNode, previousNode.getPreceding());
				}
				
			}
		}
	}
	
//	/*
//	 * Returns a clone of a Node
//	 * 
//	 */
//	private Node<ChordSymbol> cloneNode(Node<ChordSymbol> toClone) {
//		
//		Node<ChordSymbol> newNode = new Node<ChordSymbol>(toClone.getValue());
//		ArrayList<Edge<ChordSymbol>> preceding = (ArrayList<Edge<ChordSymbol>>) (toClone.getPreceding()).clone();
//		ArrayList<Edge<ChordSymbol>> following = (ArrayList<Edge<ChordSymbol>>) toClone.getFollowing();
//		
//	}
	
	//removes the Node toRemove from the Graph and removes the relevant Edges,
	//if the node that is removed is the only node that one of its previous nodes lead to, then that previous node is removed as well
	private void removeFromProgression(Node<ChordSymbol> toRemove, List<List<Node<ChordSymbol>>> matchingNodesList,
															int matchingNodesListIdx, Graph<ChordSymbol> progressionsGraph) {

		//get the list of Nodes from which to remove the Node toRemove
		List<Node<ChordSymbol>> currentNodesList = matchingNodesList.get(matchingNodesListIdx);
		currentNodesList.remove(toRemove);

		if(matchingNodesListIdx > 0) {


			List<Edge<ChordSymbol>> previousEdges = toRemove.getPreceding();

			//check to see if the any previous Nodes only leads to the current Node that was just deleted
			for(Edge<ChordSymbol> previousEdge : previousEdges) {

				Node<ChordSymbol> previousNode = previousEdge.getFront();
				if(previousNode.getFollowing().isEmpty()) {//if the previous node only leads to the current node, it will be deleted as well
					
					progressionsGraph.removeEdge(previousNode, toRemove);
					removeFromProgression(previousNode, matchingNodesList, matchingNodesListIdx - 1, progressionsGraph);
				}
			}
		}
		else if(matchingNodesListIdx == 0) {//if toRemove belongs to the first set of Nodes in matchingNodesList

			//remove from the starting node
			progressionsGraph.removeEdge(progressionsGraph.getStartingNode(), toRemove);
		}


	}


	/*
	 * Takes a integer which indicates the scale degree of a note (1-7), and converts that to the pitch degree (0-11, one half step for each pitch degree).
	 */
	public int scaleDegreeToPitchDegree(int scaleDegree) {

		if(scaleDegree < 1 || scaleDegree > 7){
			//	System.out.println("erroroneous scaleDegree input");
			return 0;
		}
		else if(scaleDegree < 4)
			return ((scaleDegree - 1) * 2);
		else
			return (5 + (scaleDegree - 4) *2);
	}
	
	public void generate(GenerateInstructionType type, Piece piece, InstructionIndex start, InstructionIndex end,
			Rational spacing) {
		_generateType = type;
		_piece 	= piece;
		_start 	= start;
		_end 		= end;
		_spacing = spacing;
		
		start();
	}
	
	public void run() {
		// start analysis process here
		switch (_generateType) {
		case CHORDS:
			generateChords();
			break;
		case VOICES:
			generateVoices();
			break;
		}
	}
	
	public void generateChords() {
		
	}
	

	/*
	 * Takes a list of Pitches, a ChordSymbol, and a keysignature, and returns a list of Pitches where harmonized melodies should be placed
	 */
	public List<Pitch> harmonizeMelodyInstance(List<Pitch> melodyInstance, ChordSymbol chordsym, KeySignature keySig) {
		
		List<Pitch> chordTones = getChordPitches(chordsym, keySig);
		int numberOfChordTones = chordTones.size();
		int numberOfCurrentPitchesPresent = melodyInstance.size(); 
		int[] chordToneCheckList = new int[numberOfChordTones]; // Each element represents a chord tone. 0 means that chord tone is not present, 1 means it is.
		int highestGivenNoteOctave = 12; //lowest Octave of the notes that already exist
		Pitch highestGivenPitch = null;
		Pitch lowestGivenPitch = null;
		Pitch insertedRoot = null;
		Pitch insertedThird = null;
		List<Pitch> returnList = new ArrayList<Pitch>();
		
		// find out the lowest given pitch
		if(!melodyInstance.isEmpty()) {
			Collections.sort(melodyInstance);
			Collections.reverse(melodyInstance);
			highestGivenPitch = melodyInstance.get(0);
			highestGivenNoteOctave = highestGivenPitch.getOctave();
			lowestGivenPitch = melodyInstance.get(melodyInstance.size() - 1);
			
			returnList.addAll(melodyInstance);
//			return returnList;
		} else {
			highestGivenPitch = chordTones.get(chordTones.size() - 1);
			highestGivenPitch.setOctave(4);
			highestGivenNoteOctave = highestGivenPitch.getOctave();
		}
		
		Pitch bass = lowestGivenPitch;
		
		
		// Find out which chord tones are already present within the Melody
		for(int i = 0; i < chordTones.size(); i++) {
			
			Pitch chordPitch = chordTones.get(i);
			for(Pitch melodyPitch : melodyInstance) {
				
				if(melodyPitch.getNoteLetter() == chordPitch.getNoteLetter() && melodyPitch.getAccidental() == chordPitch.getAccidental()) {
					// The melody contains this chord pitch, but maybe in a different Octave
					
					chordToneCheckList[i] ++;
					break;
				}
			}
		}
		
//		for(Pitch melodyPitch : melodyInstance) { // find lowest octave and 
//			if(melodyPitch.getOctave() < highestGivenNoteOctave) {
//				
//				highestGivenNoteOctave = melodyPitch.getOctave();
//			}
//			if(melodyPitch.compareTo(highestGivenPitch) < 0) {
//				
//				highestGivenPitch = melodyPitch;
//			}
//			returnList.add(melodyPitch);
//		}
		
//		int absoluteLowestOctave = lowestExistingNoteOctave;
		int remainingPitchesToEnter = 4 - numberOfCurrentPitchesPresent;
		
		while(remainingPitchesToEnter > 0) {
			
			Pitch toAdd = null;
			if(chordToneCheckList[0] == 0) { //root of chord is not present yet, so add root
				
				Pitch root = chordTones.get(0);
				// set octave of root to be the lowest melody note
				root.setOctave(highestGivenNoteOctave); 
				while(root.compareTo(highestGivenPitch) > 0) {
					root.setOctave(root.getOctave() -1 );
				}
				chordToneCheckList[0] ++;
				toAdd = root;
				insertedRoot = root;
			}
			else if(chordToneCheckList[1] == 0) { //3rd of chord is missing, add 3rd
				
				Pitch third = chordTones.get(1);
				// set octave of root to be 1 below the lowest melody note
				third.setOctave(highestGivenNoteOctave);  
				while(third.compareTo(highestGivenPitch) > 0) {
					third.setOctave(third.getOctave() -1 );
				}
				chordToneCheckList[1] ++;
				toAdd = third;
				insertedThird = third;
			}
			else if(chordToneCheckList.length > 3 && chordToneCheckList[3] == 0) { //7th of chord is missing, add 7th
				
				Pitch seventh = chordTones.get(3);
				// set octave of root to be 1 below the lowest melody note
				seventh.setOctave(highestGivenNoteOctave); 
				while(seventh.compareTo(highestGivenPitch) > 0) {
					seventh.setOctave(seventh.getOctave() -1 );
				}
				chordToneCheckList[3] ++;
				toAdd = seventh;
			}
			else if(chordToneCheckList[2] == 0) { // enter 5th
				
				Pitch fifth = chordTones.get(2);
				// set octave of root to be 1 below the lowest melody note
				fifth.setOctave(highestGivenNoteOctave); 
				while(fifth.compareTo(highestGivenPitch) > 0) {
					fifth.setOctave(fifth.getOctave() - 1);
				}
				chordToneCheckList[2] ++;
				toAdd = fifth;
			}
			else { // all chord tones already exist, repeat root or third
				
				Pitch root = chordTones.get(0).copy();
				Pitch third = chordTones.get(1).copy();
				// set octave of root to be 1 below the lowest melody note
//				if(presentRoot != null) {
//					root.setOctave(lowestExistingNoteOctave - 1); 
//				}
//				else {
				
				third.setOctave(highestGivenNoteOctave-1);
				root.setOctave(highestGivenNoteOctave-1);
				 
				while(root.compareTo(highestGivenPitch) > 0) {
					root.setOctave(root.getOctave() - 1);
				}
				while(third.compareTo(highestGivenPitch) > 0) {
					third.setOctave(third.getOctave() - 1);
				}
				if(third.compareTo(bass) > 0 && !returnList.contains(third)) { // If the third is higher than the bass, and a the existing third is not the same as the one to be entered
					
					chordToneCheckList[1] ++;
					toAdd = third;
					
				} else {
					// If the bass note is the root, insert a third as the root
					if(bass.equalsName(root)) {
						
						while(third.compareTo(bass) > 0) {
							third.setOctave(third.getOctave() - 1);
						}
						chordToneCheckList[1] ++;
						toAdd = third;
					}
					else { // otherwise make the bass the root
						
						while(root.compareTo(bass) > 0) {
							root.setOctave(third.getOctave() - 1);
						}
						chordToneCheckList[0] ++;
						toAdd = root;
					}
				}
			}
			
			returnList.add(toAdd);
			if(toAdd.compareTo(bass) < 0) { // if added note is lower than the bass, update the bass note
				bass = toAdd;
			}
			
			remainingPitchesToEnter--;
		}
		
		Collections.sort(returnList);
		Collections.reverse(returnList);
//		for(int i = 0; i < returnList.size(); i++) {
//			for(int j = 0; j < returnList.size() - 1; j++){
//				
//				if(returnList.get(j).compareTo(returnList.get(j + 1)) < 0) {
//					Pitch temp = returnList.get(j);
//					returnList.remove(j);
//					returnList.add(j + 1, temp);
//				}
//			}
//		}
		
		_prevPitches = returnList;
		
		return returnList;
	}

	/* 
	 * Takes in a list of pitches that already exist in the melody instance to be harmonized, and a chordsymbol as a target chord to harmonize with,
	 * and returns a list of Pitches that are to be added, considering some voice leading pricipals
	 * 
	 */
	public List<Pitch> harmonizeWithVoiceLeading(List<Pitch> currentPitches, ChordSymbol chordsym, KeySignature keySig) {
		
		List<Pitch> precedingPitches = _prevPitches;
		
		if(precedingPitches == null || precedingPitches.isEmpty()) {
			
			// generate voices without considering voice leading
			return harmonizeMelodyInstance(currentPitches, chordsym, keySig);
		}
		else {
			ArrayList<Pitch> previousPitchesNotAccountedFor = new ArrayList<Pitch>();
			previousPitchesNotAccountedFor.addAll(precedingPitches);
			
			List<Pitch> chordTones = getChordPitches(chordsym, keySig);
			Pitch precedingSoprano = null;
			Pitch precedingBass = null;
			Pitch precedingTenor = null;
			Pitch precedingAlto = null;
			Pitch currentSoprano = null;
			Pitch currentBass = null;
			int numberOfChordTones = chordTones.size();
			int numberOfCurrentPitchesPresent = currentPitches.size(); 
			int numVoices = precedingPitches.size();
			int numVoicesToAdd = numVoices - numberOfCurrentPitchesPresent;
			
			int[] voiceLeadingCheckList = new int[numVoices]; // an array of integers, each one representing the state of a Pitch in the previous voice
			int[] chordToneCheckList = new int[numberOfChordTones]; // Each element represents a chord tone. 0 means that chord tone is not present, 1 means it is.
			
			List<Pitch> returnList = new ArrayList<Pitch>();
			returnList.addAll(currentPitches);
			
			// Find out which chord tones are already present within the Melody
			for(int i = 0; i < chordTones.size(); i++) {
				
				Pitch chordPitch = chordTones.get(i);
				for(Pitch melodyPitch : currentPitches) {
					
					if(melodyPitch.getNoteLetter() == chordPitch.getNoteLetter() && melodyPitch.getAccidental() == chordPitch.getAccidental()) {
						// The melody contains this chord pitch, but maybe in a different Octave
						
						chordToneCheckList[i] ++;
						break;
					}
				}
			}
			
			precedingBass = precedingPitches.get(numVoices - 1);
			precedingTenor = precedingPitches.get(numVoices - 2);
			precedingAlto = precedingPitches.get(1);
			precedingSoprano = precedingPitches.get(0);
			
			Collections.sort(currentPitches);
			Collections.reverse(currentPitches);
		
			// designate each of the current Pitches to be in the same voice as one of the preceding Pitches
			for(Pitch currentPitch : currentPitches) { // starting from the highest note
				
				
				if(previousPitchesNotAccountedFor.contains(precedingSoprano) &&
						(currentPitch.compareTo(precedingSoprano) >= 0
						|| (currentPitch.compareTo(precedingAlto) >= 0 
								&& currentPitch.calcIntervalTo(precedingSoprano).getSize() < 5))) { // this note is the soprano for the current melody instance
					
//					voiceLeadingCheckList[numVoices - 1] = 1;
					previousPitchesNotAccountedFor.remove(precedingSoprano);
					currentSoprano = currentPitch;
				}
				else if((currentPitch.compareTo(precedingBass) <= 0
						|| (currentPitch.compareTo(precedingTenor) <= 0 && precedingBass.calcIntervalTo(currentPitch).getSize() < 5))
						&& previousPitchesNotAccountedFor.contains(precedingBass)) { // this note is the bass for the current melody instance
					
//					voiceLeadingCheckList[0] = 1;
					previousPitchesNotAccountedFor.remove(precedingBass);
					currentBass = currentPitch;
				}
				else { // find the preceding pitch with the closest interval to the current one
//					int closestPreviousPitchIndex = -1;
					Pitch closestPreviousPitch = null;
					int closestInterval = 100;
					
					// compare current pitch with all the preceding ones to find the closest one
					for(Pitch precedingPitch : previousPitchesNotAccountedFor) {
						
//						if(voiceLeadingCheckList[j] == 0) {
							
//							Pitch precedingPitch = precedingPitches.get(j);
//							if(closestPreviousPitchIndex == -1) {
						if(closestPreviousPitch == null) {
//								closestPreviousPitchIndex = j;
							closestPreviousPitch = precedingPitch;
							closestInterval = Math.abs(currentPitch.computeMidiPitch() - precedingPitch.computeMidiPitch());
						}
						else {
							int newInterval = Math.abs(currentPitch.computeMidiPitch() - precedingPitch.computeMidiPitch());
							if(newInterval < closestInterval) { // this previous Pitch is closer to the current Pitch
								
//									closestPreviousPitchIndex = j;
								closestPreviousPitch = precedingPitch;
								closestInterval = newInterval;
							}
						}
//						}
					}
					
//					if(closestPreviousPitchIndex != -1) {
					if(closestPreviousPitch != null) {
						// 
//						voiceLeadingCheckList[closestPreviousPitchIndex] = 1;	
						previousPitchesNotAccountedFor.remove(closestPreviousPitch);
					}
				}
				
			}
			
			// add a Pitch for each of the preceding voices that are not yet present in the current melody instance
			while(numVoicesToAdd > 0) {
				
				Pitch chordToneToInsert = null;
//				int chordToneIndex = 0;
				
				if(chordToneCheckList[0] == 0) { //root of chord is not present yet, so add root
					
					chordToneToInsert = chordTones.get(0).copy();
					chordToneCheckList[0] ++;
				}
				else if(chordToneCheckList[1] == 0) { //3rd of chord is missing, add 3rd
					
					chordToneToInsert = chordTones.get(1).copy();
					chordToneCheckList[1] ++;
				}
				else if(chordToneCheckList.length > 3 && chordToneCheckList[3] == 0) { //7th of chord is missing, add 7th
					
					chordToneToInsert = chordTones.get(3).copy();
					chordToneCheckList[3] ++;
				}
				else if(chordToneCheckList[2] == 0) { // enter 5th
					
					chordToneToInsert = chordTones.get(2).copy();
					chordToneCheckList[2] ++;
				}
				else { // all chord tones already exist, repeat root or third, depending on which one is closer to the preceding Pitch that is not accounted for
					
					Pitch root = chordTones.get(0).copy();
					Pitch third = chordTones.get(1).copy();
					Pitch precedingPitch = previousPitchesNotAccountedFor.get(0);
					
					int rootIntervalFromPrec = Math.abs(root.computeMidiPitch() - precedingPitch.computeMidiPitch()) % 12 ;
					if(rootIntervalFromPrec > 6)
						rootIntervalFromPrec -= 12;
					int thirdIntervalFromPrec = Math.abs(third.computeMidiPitch() - precedingPitch.computeMidiPitch()) % 12 ;
					if(thirdIntervalFromPrec > 6)
						thirdIntervalFromPrec -= 12;
					
					if(rootIntervalFromPrec < thirdIntervalFromPrec) {
						chordToneToInsert =  root;
						chordToneCheckList[0] ++;
					}
					else {
						chordToneToInsert =  third;
						chordToneCheckList[1] ++;
					}
				}
				

				int maxPitch = currentSoprano != null ? currentSoprano.computeMidiPitch() : 127;
				int minPitch = currentBass != null ? currentBass.computeMidiPitch() : 0;
				findBestVoice(previousPitchesNotAccountedFor, chordToneToInsert, maxPitch, minPitch);
//				if(currentSoprano == null) {
//					currentSoprano = chordToneToInsert;
//				}
				
				returnList.add(chordToneToInsert);
				numVoicesToAdd --;
			}
			Collections.sort(returnList);
			Collections.reverse(returnList);
			
			_prevPitches = returnList;
			
			System.out.println("Previous Pitches:");
            for(Pitch pitch : precedingPitches) {
              	System.out.println(pitch);
             }
            System.out.println("harmonizing:");
            for(Pitch pitch : currentPitches) {
            	System.out.println(pitch);
            }
            System.out.println("with chord after voice leading: " + chordsym.getSymbolText() + chordsym.getTopInversionText() + " in key: " + keySig.getKeySigPitch());
            for(Pitch pitch : returnList) {
            	
            	System.out.println(pitch);
            }
            
			return returnList;
		}
	}
	
	/* 
	 * Takes a list of previous pitches, a current pitch, and highest and lowest possible pitch values, finds the pitch among the previous pitches that the current Pitch is closest to (ignoring octave numbers)
 	 * removes that closest previous pitch from the notAccountedFor list, and modifies the current Pitch to the octave that makes it closest to the selected previous note
 	 * 
 	 */
	public void findBestVoice(List<Pitch> previousPitchesNotAccountedFor, Pitch chordToneToInsert, int maxPitch, int minPitch) {
		System.out.println("maxPitch is:" + maxPitch + "   minPitch is:" + minPitch);
		Pitch closestPreviousPitch = previousPitchesNotAccountedFor.get(0);
		int smallestInterval = 100;
		
		// go through the preceding Pitches and find one that is closest to the chordToneToInsert
		for(Pitch precedingPitch : previousPitchesNotAccountedFor) {
			
			// calculate the interval between chordToneToInsert and precedingPitch, and find the minimum one between 
			int posNegInterval = (chordToneToInsert.computeMidiPitch() - precedingPitch.computeMidiPitch()) % 12;
			int newIntervalAbove = posNegInterval >= 0 ? posNegInterval : posNegInterval + 12;
			int newIntervalBelow = posNegInterval <= 0 ? -posNegInterval : posNegInterval - 12;
			int newInterval;
			
			if(newIntervalAbove < newIntervalBelow
					&& (precedingPitch.computeMidiPitch() + newIntervalAbove < maxPitch)) { // check to see if this interval would make the pitch exceed the maximum pitch value
					
				newInterval = newIntervalAbove;
			}
			else {
				
				newInterval = newIntervalBelow;
			}
			if(newInterval < smallestInterval) { // this previous Pitch is closer to the current Pitch
				
//					closestPreviousPitchIndex = j;
				closestPreviousPitch = precedingPitch;
				smallestInterval = newInterval;
			}
		}
		
		previousPitchesNotAccountedFor.remove(closestPreviousPitch);
		// set chordToneToInsert to the octave that makes it closest to the chosen Pitch
		int pitchInterval = chordToneToInsert.computeMidiPitch() - closestPreviousPitch.computeMidiPitch();
		while(pitchInterval >= 12) {
			
			chordToneToInsert.setOctave(chordToneToInsert.getOctave() - 1);
			pitchInterval -= 12;
		}
		while(pitchInterval <= -12) {
			
			chordToneToInsert.setOctave(chordToneToInsert.getOctave() + 1);
			pitchInterval += 12;
		}
		
		if((pitchInterval > 6 && (chordToneToInsert.computeMidiPitch() - 12) > minPitch) || chordToneToInsert.computeMidiPitch() >= maxPitch) {
			chordToneToInsert.setOctave(chordToneToInsert.getOctave() - 1);
		}
		else if((pitchInterval < -6 && (chordToneToInsert.computeMidiPitch() + 12) < maxPitch) || chordToneToInsert.computeMidiPitch() <= minPitch) {
			chordToneToInsert.setOctave(chordToneToInsert.getOctave() + 1);
		}
		
//		return chordToneToInsert;
	}
	
	public void generateVoices() {
		
	}
	
	
}
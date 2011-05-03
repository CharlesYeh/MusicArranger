package logic;

import music.*;

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
		ChordSymbol chordI	= new ChordSymbol(0, ChordType.MAJOR);
		ChordSymbol chordii	= new ChordSymbol(0, ChordType.MINOR);
		ChordSymbol chordiii	= new ChordSymbol(0, ChordType.MINOR);
		ChordSymbol chordIV	= new ChordSymbol(0, ChordType.MAJOR);
		ChordSymbol chordV	= new ChordSymbol(0, ChordType.MAJOR);
		ChordSymbol chordvi	= new ChordSymbol(0, ChordType.MINOR);
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
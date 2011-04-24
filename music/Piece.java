package music;

import java.util.LinkedList;
import java.util.ArrayList;

public class Piece {
	ArrayList<Staff> _staffs;						// staffs in the piece
	LinkedList<KeySignature> _keySigs;			// key signatures
	LinkedList<TimeSignature> _timeSigs;		// time signatures
	LinkedList<ChordSymbol> _chordSymbols;		// chord symbols
	
	public Piece () {
	
	}
	
	public ArrayList<Staff> getStaffs() {
		return _staffs;
	}
	
	public LinkedList<KeySignature> getKeySignatures() {
		return _keySigs;
	}
	
	public LinkedList<TimeSignature> getTimeSignatures() {
		return _timeSigs;
	}
	
	public LinkedList<ChordSymbol> getChordSymbols() {
		return _chordSymbols;
	}
	
}
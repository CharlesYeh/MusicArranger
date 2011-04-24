package music;

import java.util.LinkedList;
import java.util.ArrayList;

public class Piece {
	ArrayList<Staff> _staffs;					// staffs in the piece
	LinkedList<KeySignature> _keySigs;			// key signatures
	LinkedList<TimeSignature> _timeSigs;		// time signatures
	LinkedList<ChordSymbol> _chordSymbols;		// chord symbols
	
	public Piece () {
		this(new ArrayList<Staff>(),
				new LinkedList<KeySignature>(),
				new LinkedList<TimeSignature>(),
				new LinkedList<ChordSymbol>());
	}
	
	public Piece (ArrayList<Staff> staffs,
						LinkedList<KeySignature> keySigs, 
						LinkedList<TimeSignature> timeSigs,
						LinkedList<ChordSymbol> chordSymbols) {
		_staffs 		= staffs;
		_keySigs 	= keySigs;
		_timeSigs 	= timeSigs;
		_chordSymbols = chordSymbols;
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
package music;

import java.util.LinkedList;
import java.util.ArrayList;

public class Piece {
	ArrayList<Staff> _staffs;				// staffs in the piece
	LinkedList<KeySig> _keySigs;			// key signatures
	LinkedList<TimeSig> _timeSigs;			// time signatures
	LinkedList<ChordSymbol> _chordSymbols;	// chord symbols
	
	public Piece () {
	
	}
	
	public ArrayList<Staff> getStaffs() {
		return _staffs;
	}
	
	public LinkedList<KeySig> getKeySigs() {
		return _keySigs;
	}
	
	public LinkedList<TimeSig> getTimeSigs() {
		return _timeSigs;
	}
	
	public LinkedList<ChordSymbol> getChordSymbol() {
		return _chordSymbols;
	}
}
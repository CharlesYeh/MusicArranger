package music;

import java.util.LinkedList;
import java.util.ArrayList;

public class Measure {
	LinkedList<KeySignature> _keySigs;
	LinkedList<TimeSignature> _timeSigs;
	LinkedList<Clef> _clefs;
	LinkedList<ChordSymbol> _chordSymbols;
	ArrayList<Voice> _voices;
	
	public Measure() {
		_keySigs = new LinkedList<KeySignature>();
		_timeSigs = new LinkedList<TimeSignature>();
		_clefs = new LinkedList<Clef>();
		_chordSymbols = new LinkedList<ChordSymbol>();
		_voices = new ArrayList<Voice>();
	}
	
	public LinkedList<KeySignature> getKeySignatures() {
		return _keySigs;
	}
	
	public LinkedList<TimeSignature> getTimeSignatures() {
		return _timeSigs;
	}
	
	public LinkedList<Clef> getClefs() {
		return _clefs;
	}
	
	public LinkedList<ChordSymbol> getChordSymbols() {
		return _chordSymbols;
	}
	
	public ArrayList<Voice> getVoices() {
		return _voices;
	}
}

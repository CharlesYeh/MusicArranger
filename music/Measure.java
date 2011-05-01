package music;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

public class Measure {
	List<KeySignature> _keySigs;
	List<TimeSignature> _timeSigs;
	List<Clef> _clefs;
	List<ChordSymbol> _chordSymbols;
	List<Voice> _voices;
	
	public Measure() {
		_keySigs 	= new LinkedList<KeySignature>();
		_timeSigs 	= new LinkedList<TimeSignature>();
		_clefs 		= new LinkedList<Clef>();
		_chordSymbols 	= new LinkedList<ChordSymbol>();
		_voices 	= new ArrayList<Voice>();
	}
	
	public List<KeySignature> getKeySignatures() {
		return _keySigs;
	}
	
	public List<TimeSignature> getTimeSignatures() {
		return _timeSigs;
	}
	
	public List<Clef> getClefs() {
		return _clefs;
	}
	
	public List<ChordSymbol> getChordSymbols() {
		return _chordSymbols;
	}
	
	public List<Voice> getVoices() {
		return _voices;
	}
}

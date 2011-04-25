package logic;

import java.util.*;
import music.*;

/* Editor is the class in the logic package that directly interacts with the music package and
 * can make modifications to the data structure.
 */
public class Editor{
	Piece _piece;				// piece being edited
	
	public Editor(){
		
	}
	
	public Editor clearScore() {
		_piece = new Piece();
	}
	
	// USED FOR REMOVING A GENERAL ELEMENT FROM ANY GIVEN LIST
	public <T> Editor removeElem(ListIterator<T> iter) {
		if (iter.hasNext()) {
			iter.next();
			iter.remove();
		} 
		else {
			throw new RuntimeException("Tried to remove from end of list");
		}
		return this;
	}
	
	// MULTINOTE EDITING
	public Editor insertPitch(ListIterator<Pitch> iter, NoteLetter ltr, int octv, Accidental accd, boolean istied) {
		Pitch pitch = new Pitch(ltr, octv, accd, istied);
		iter.add(pitch);
		return this;
	}
	
	public Editor replacePitch(ListIterator<Pitch> iter, NoteLetter ltr, int octv, Accidental accd, boolean istied) {
		this.removeElem(iter).insertPitch(iter, ltr, octv, accd, istied);
		return this;
	}
	
	// VOICE EDITING
	public Editor insertMultiNote(ListIterator<MultiNote> iter, Rational dur) {
		MultiNote multi = new MultiNote(dur);
		iter.add(multi);
		return this;
	}
	public Editor replaceMultiNote(ListIterator<MultiNote> iter, Rational dur) {
		this.removeElem(iter).insertMultiNote(iter, dur);
		return this;
	}
	
	// STAFF EDITING
	// edits the clef list
	public Editor insertClef(ListIterator<Clef> iter, Rational dur, ClefName clefnm, int linenum) {
		Clef clef = new Clef(dur, clefnm, linenum);
		iter.add(clef);
		return this;
	}
	public Editor replaceClef(ListIterator<Clef> iter, Rational dur, ClefName clefnm, int linenum) {
		this.removeElem(iter).insertClef(iter, dur, clefnm, linenum);
		return this;
	}
	
	// PIECE EDITING
	// edits the key signature list
	public Editor insertKeySig(ListIterator<KeySignature> iter, Rational dur, int accds, boolean ismjr) {
		KeySignature keysig = new KeySignature(dur, accds, ismjr);
		iter.add(keysig);
		return this;
	}
	public Editor replaceKeySig(ListIterator<KeySignature> iter, Rational dur, int accds, boolean ismjr) {
		this.removeElem(iter).insertKeySig(iter, dur, accds, ismjr);
		return this;
	}
	
	// edits the time signature list
	public Editor insertTimeSig(ListIterator<TimeSignature> iter, Rational dur, int numer, int denom) {
		TimeSignature timesig = new TimeSignature(dur, numer, denom);
		iter.add(timesig);
		return this;
	}
	public Editor replaceTimeSig(ListIterator<TimeSignature> iter, Rational dur, int numer, int denom) {
		this.removeElem(iter).insertTimeSig(iter, dur, numer, denom);
		return this;
	}
	
	// edits the chord symbol list
	public Editor insertChordSymbol(ListIterator<ChordSymbol> iter, Rational dur, int scldgr, ChordType qual) {
		ChordSymbol chordsym = new ChordSymbol(dur, scldgr, qual);
		iter.add(chordsym);
		return this;
	}
	public Editor replaceChordSymbol(ListIterator<ChordSymbol> iter, Rational dur, int scldgr, ChordType qual) {
		this.removeElem(iter).insertChordSymbol(iter, dur, scldgr, qual);
		return this;
	}
}
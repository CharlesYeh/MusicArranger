package logic;

import java.util.*;
import music.*;

/* Editor is the class in the logic package that directly interacts with the music package and
 * can make modifications to the data structure.
 */
public class Editor{
	Piece _piece;				// piece being edited

	// these iterators are used for sequential addition to the list
	ListIterator<KeySignature> _keySigIter;
	ListIterator<TimeSignature> _timeSigIter;
	ListIterator<ChordSymbol> _chordSymIter;
	ListIterator<Staff> _staffIter;
	ListIterator<Clef> _clefIter;
	ListIterator<Voice> _voiceIter;
	ListIterator<MultiNote> _multiNoteIter;
	ListIterator<Pitch> _pitchIter;
	
	public Editor() {
	}
	
	public Editor clearScore() {
		setPiece(new Piece());
		return this;
	}
	
	public Editor setPiece(Piece piece) {
		_piece = piece;
		_keySigIter = _piece.getKeySignatures().listIterator();
		_timeSigIter = _piece.getTimeSignatures().listIterator();
		_chordSymIter = _piece.getChordSymbols().listIterator();
		_staffIter = _piece.getStaffs().listIterator();
		return this;
	}
	
	public Piece getPiece() {
		return _piece;
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
	// PIECE EDITING
	// edits the key signature list
	
	// insert at current iterator location, if no iterator is specified
	public Editor insertKeySig(Rational dur, int accds, boolean ismjr) {
		insertKeySig(_keySigIter, dur, accds, ismjr);
		return this;
	}

	// insert at specified iterator location
	public Editor insertKeySig(ListIterator<KeySignature> iter, Rational dur, int accds, boolean ismjr) {
		KeySignature keysig = new KeySignature(dur, accds, ismjr);
		iter.add(keysig);
		_keySigIter = iter;
		return this;
	}
	public Editor replaceKeySig(ListIterator<KeySignature> iter, Rational dur, int accds, boolean ismjr) {
		this.removeElem(iter).insertKeySig(iter, dur, accds, ismjr);
		iter.previous();
		return this;
	}
	
	// edits the time signature list
	
	// insert at current iterator location, if no iterator is specified
	public Editor insertTimeSig(Rational dur, int numer, int denom) {
		insertTimeSig(_timeSigIter, dur, numer, denom);
		return this;
	}
	// insert at specified iterator location
	public Editor insertTimeSig(ListIterator<TimeSignature> iter, Rational dur, int numer, int denom) {
		TimeSignature timesig = new TimeSignature(dur, numer, denom);
		iter.add(timesig);
		_timeSigIter = iter;
		return this;
	}
	public Editor replaceTimeSig(ListIterator<TimeSignature> iter, Rational dur, int numer, int denom) {
		this.removeElem(iter).insertTimeSig(iter, dur, numer, denom);
		iter.previous();
		return this;
	}
	
	// edits the staff list
	
	// insert at current iterator location, if no iterator is specified
	public Editor insertStaff() {
		insertStaff(_staffIter);
		return this;
	}
	// insert at specified iterator location
	public Editor insertStaff(ListIterator<Staff> iter) {
		Staff staff = new Staff();
		iter.add(staff);
		_voiceIter = staff.getVoices().listIterator();
		_clefIter = staff.getClefs().listIterator();
		_staffIter = iter;
		return this;
	}
	public Editor replaceTimeSig(ListIterator<Staff> iter) {
		this.removeElem(iter).insertStaff(iter);
		iter.previous();
		return this;
	}
	
	// edits the chord symbol list
	
	// insert at current iterator location, if no iterator is specified
	public Editor insertChordSymbol(Rational dur, int scldgr, ChordType qual) {
		insertChordSymbol(_chordSymIter, dur, scldgr, qual);
		return this;
	}
	// insert at specified iterator location
	public Editor insertChordSymbol(ListIterator<ChordSymbol> iter, Rational dur, int scldgr, ChordType qual) {
		ChordSymbol chordsym = new ChordSymbol(dur, scldgr, qual);
		iter.add(chordsym);
		_chordSymIter = iter;
		return this;
	}
	public Editor replaceChordSymbol(ListIterator<ChordSymbol> iter, Rational dur, int scldgr, ChordType qual) {
		this.removeElem(iter).insertChordSymbol(iter, dur, scldgr, qual);
		iter.previous();
		return this;
	}
	
	// STAFF EDITING
	// edits the clef list
	
	// insert at current iterator location, if no iterator is specified
	public Editor insertClef(Rational dur, ClefName clefnm, int linenum) {
		insertClef(_clefIter, dur, clefnm, linenum);
		return this;
	}
	// insert at specified iterator location
	public Editor insertClef(ListIterator<Clef> iter, Rational dur, ClefName clefnm, int linenum) {
		Clef clef = new Clef(dur, clefnm, linenum);
		iter.add(clef);
		_clefIter = iter;
		return this;
	}
	public Editor replaceClef(ListIterator<Clef> iter, Rational dur, ClefName clefnm, int linenum) {
		this.removeElem(iter).insertClef(iter, dur, clefnm, linenum);
		iter.previous();
		return this;
	}
	
	//edits the voice list
	
	// insert at current iterator location, if no iterator is specified
	public Editor insertVoice() {
		insertVoice(_voiceIter);
		return this;
	}
	// insert at specified iterator location
	public Editor insertVoice(ListIterator<Voice> iter) {
		Voice voice = new Voice();
		iter.add(voice);
		_voiceIter = iter;
		_multiNoteIter = voice.getMultiNotes().listIterator();
		return this;
	}
	
	// VOICE EDITING
	
	// insert at current iterator location, if no iterator is specified
	public Editor insertMultiNote(Rational dur) {
		insertMultiNote(_multiNoteIter, dur);
		return this;
	}
	// insert at specified iterator location
	public Editor insertMultiNote(ListIterator<MultiNote> iter, Rational dur) {
		MultiNote multi = new MultiNote(dur);
		iter.add(multi);
		_multiNoteIter = iter;
		_pitchIter = multi.getPitches().listIterator();
		return this;
	}
	public Editor replaceMultiNote(ListIterator<MultiNote> iter, Rational dur) {
		this.removeElem(iter).insertMultiNote(iter, dur);
		iter.previous();
		return this;
	}
	
	// MULTINOTE EDITING
	
	// insert at current iterator location, if no iterator is specified
	public Editor insertPitch(NoteLetter ltr, int octv, Accidental accd, boolean istied) {
		insertPitch(_pitchIter, ltr, octv, accd, istied);
		return this;
	}
	// insert at specified iterator location
	public Editor insertPitch(ListIterator<Pitch> iter, NoteLetter ltr, int octv, Accidental accd, boolean istied) {
		Pitch pitch = new Pitch(ltr, octv, accd, istied);
		iter.add(pitch);
		_pitchIter = iter;
		return this;
	}
	public Editor replacePitch(ListIterator<Pitch> iter, NoteLetter ltr, int octv, Accidental accd, boolean istied) {
		this.removeElem(iter).insertPitch(iter, ltr, octv, accd, istied);
		iter.previous();
		return this;
	}
}
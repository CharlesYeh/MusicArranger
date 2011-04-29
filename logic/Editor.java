package logic;

import java.util.*;
import music.*;

/* Editor is the class in the logic package that directly interacts with the music package and
 * can make modifications to the data structure.
 */
public class Editor{
	Piece _piece;				// piece being edited

// these iterators are used for sequential addition to the list
	ListIterator<Staff> _staffIter;
	ListIterator<Measure> _measureIter;
	ListIterator<KeySignature> _keySigIter;
	ListIterator<TimeSignature> _timeSigIter;
	ListIterator<ChordSymbol> _chordSymIter;
	ListIterator<Clef> _clefIter;
	ListIterator<Voice> _voiceIter;
	ListIterator<MultiNote> _multiNoteIter;
	ListIterator<Pitch> _pitchIter;

	public Editor() {
	}
<<<<<<< HEAD
	
	public Editor(Piece piece) {
		setPiece(piece);
	}
	
=======

>>>>>>> 7df1b9966fa6c1dbdcb4c3db763db3de5d3715a9
	public Editor clearScore() {
		setPiece(new Piece());
		return this;
	}

	public Editor setPiece(Piece piece) {
		_piece = piece;
		_staffIter = _piece.getStaffs().listIterator();
		return this;
	}

	public Piece getPiece() {
		return _piece;
	}
<<<<<<< HEAD
	
// STAFFS
	public Editor setStaffIter(ListIterator<Staff> iter) {
		_staffIter = iter;
		return this;
	}
	public Editor insertStaff(Staff staff) {
		_staffIter.add(staff);
		_measureIter = staff.getMeasures().listIterator();
		return this;
	}
	public Editor removeStaff() {
		if (_staffIter.hasNext()) {
			_staffIter.next();
			_staffIter.remove();
		} 
=======

	// USED FOR REMOVING A GENERAL ELEMENT FROM ANY GIVEN LIST
	public <T> Editor removeElem(ListIterator<T> iter) {
		if (iter.hasNext()) {
			iter.next();
			iter.remove();
		}
>>>>>>> 7df1b9966fa6c1dbdcb4c3db763db3de5d3715a9
		else {
			throw new RuntimeException("Tried to remove from end of list");
		}
		return this;
	}
<<<<<<< HEAD
	public Editor replaceStaff(Staff staff) {
		this.removeStaff().insertStaff(staff);
		_staffIter.previous();
		return this;
	}
	
// MEASURES
	public Editor setMeasureIter(ListIterator<Measure> iter) {
		_measureIter = iter;
=======
	// PIECE EDITING
	// edits the key signature list

	// insert at current iterator location, if no iterator is specified
	public Editor insertKeySig(Rational dur, int accds, boolean ismjr) {
		insertKeySig(_keySigIter, dur, accds, ismjr);
>>>>>>> 7df1b9966fa6c1dbdcb4c3db763db3de5d3715a9
		return this;
	}
	public Editor insertMeasure(Measure measure) {
		_measureIter.add(measure);
		_keySigIter = measure.getKeySignatures().listIterator();
		_timeSigIter = measure.getTimeSignatures().listIterator();
		_chordSymIter = measure.getChordSymbols().listIterator();
		_clefIter = measure.getClefs().listIterator();
		_voiceIter = measure.getVoices().listIterator();
		return this;
	}
	public Editor removeMeasure() {
		if (_measureIter.hasNext()) {
			_measureIter.next();
			_measureIter.remove();
		} 
		else {
			throw new RuntimeException("Tried to remove from end of list");
		}
		return this;
	}
	public Editor replaceMeasure(Measure measure) {
		this.removeMeasure().insertMeasure(measure);
		_measureIter.previous();
		return this;
	}
<<<<<<< HEAD
	
// KEY SIGS
	public Editor setKeySignatureIter(ListIterator<KeySignature> iter) {
		_keySigIter = iter;
=======

	// edits the time signature list

	// insert at current iterator location, if no iterator is specified
	public Editor insertTimeSig(Rational dur, int numer, int denom) {
		insertTimeSig(_timeSigIter, dur, numer, denom);
>>>>>>> 7df1b9966fa6c1dbdcb4c3db763db3de5d3715a9
		return this;
	}
	public Editor insertKeySig(KeySignature keySig) {
		_keySigIter.add(keySig);
		return this;
	}
	public Editor removeKeySig() {
		if (_keySigIter.hasNext()) {
			_keySigIter.next();
			_keySigIter.remove();
		} 
		else {
			throw new RuntimeException("Tried to remove from end of list");
		}
		return this;
	}
	public Editor replaceKeySig(KeySignature keySig) {
		this.removeKeySig().insertKeySig(keySig);
		_keySigIter.previous();
		return this;
	}
<<<<<<< HEAD
	
// TIME SIGS
	public Editor setTimeSignatureIter(ListIterator<TimeSignature> iter) {
		_timeSigIter = iter;
=======

	// edits the staff list

	// insert at current iterator location, if no iterator is specified
	public Editor insertStaff() {
		insertStaff(_staffIter);
>>>>>>> 7df1b9966fa6c1dbdcb4c3db763db3de5d3715a9
		return this;
	}
	public Editor insertTimeSig(TimeSignature timeSig) {
		_timeSigIter.add(timeSig);
		return this;
	}
	public Editor removeTimeSig() {
		if (_timeSigIter.hasNext()) {
			_timeSigIter.next();
			_timeSigIter.remove();
		} 
		else {
			throw new RuntimeException("Tried to remove from end of list");
		}
		return this;
	}
<<<<<<< HEAD
	public Editor replaceTimeSig(TimeSignature timeSig) {
		this.removeTimeSig().insertTimeSig(timeSig);
		_timeSigIter.previous();
=======

	// edits the chord symbol list

	// insert at current iterator location, if no iterator is specified
	public Editor insertChordSymbol(Rational dur, int scldgr, ChordType qual) {
		insertChordSymbol(_chordSymIter, dur, scldgr, qual);
>>>>>>> 7df1b9966fa6c1dbdcb4c3db763db3de5d3715a9
		return this;
	}
	
// CHORD SYMBOLS
	public Editor setChordSymbolIter(ListIterator<ChordSymbol> iter) {
		_chordSymIter = iter;
		return this;
	}
	public Editor insertChordSymbol(ChordSymbol chordSymbol) {
		_chordSymIter.add(chordSymbol);
		return this;
	}
<<<<<<< HEAD
	public Editor removeChordSymbol() {
		if (_chordSymIter.hasNext()) {
			_chordSymIter.next();
			_chordSymIter.remove();
		} 
		else {
			throw new RuntimeException("Tried to remove from end of list");
		}
		return this;
	}
	public Editor replaceChordSymbol(ChordSymbol chordSymbol) {
		this.removeChordSymbol().insertChordSymbol(chordSymbol);
		_chordSymIter.previous();
=======

	// STAFF EDITING
	// edits the clef list

	// insert at current iterator location, if no iterator is specified
	public Editor insertClef(Rational dur, ClefName clefnm, int linenum) {
		insertClef(_clefIter, dur, clefnm, linenum);
>>>>>>> 7df1b9966fa6c1dbdcb4c3db763db3de5d3715a9
		return this;
	}
	
// CLEFS
	public Editor setClefIter(ListIterator<Clef> iter) {
		_clefIter = iter;
		return this;
	}
	public Editor insertClef(Clef clef) {
		_clefIter.add(clef);
		return this;
	}
<<<<<<< HEAD
	public Editor removeClef() {
		if (_clefIter.hasNext()) {
			_clefIter.next();
			_clefIter.remove();
		} 
		else {
			throw new RuntimeException("Tried to remove from end of list");
		}
=======

	//edits the voice list

	// insert at current iterator location, if no iterator is specified
	public Editor insertVoice() {
		insertVoice(_voiceIter);
>>>>>>> 7df1b9966fa6c1dbdcb4c3db763db3de5d3715a9
		return this;
	}
	public Editor replaceClef(Clef clef) {
		this.removeClef().insertClef(clef);
		_clefIter.previous();
		return this;
	}
	
// VOICES
	public Editor setVoiceIter(ListIterator<Voice> iter) {
		_voiceIter = iter;
<<<<<<< HEAD
		return this;
	}
	public Editor insertVoice(Voice voice) {
		_voiceIter.add(voice);
		_multiNoteIter = voice.getMultiNotes().listIterator();
		return this;
	}
	public Editor removeVoice() {
		if (_voiceIter.hasNext()) {
			_voiceIter.next();
			_voiceIter.remove();
		} 
		else {
			throw new RuntimeException("Tried to remove from end of list");
		}
		return this;
	}
	public Editor replaceVoice(Voice voice) {
		this.removeVoice().insertVoice(voice);
		_voiceIter.previous();
=======
		_multiNoteIter = voice.getMultinotes().listIterator();
		return this;
	}

	// VOICE EDITING

	// insert at current iterator location, if no iterator is specified
	public Editor insertMultiNote(Rational dur) {
		insertMultiNote(_multiNoteIter, dur);
>>>>>>> 7df1b9966fa6c1dbdcb4c3db763db3de5d3715a9
		return this;
	}
	
// MULTINOTES
	public Editor setMultiNoteIter(ListIterator<MultiNote> iter) {
		_multiNoteIter = iter;
		return this;
	}
	public Editor insertMultiNote(MultiNote multiNote) {
		_multiNoteIter.add(multiNote);
		_pitchIter = multiNote.getPitches().listIterator();
		return this;
	}
<<<<<<< HEAD
	public Editor removeMultiNote() {
		if (_multiNoteIter.hasNext()) {
			_multiNoteIter.next();
			_multiNoteIter.remove();
		} 
		else {
			throw new RuntimeException("Tried to remove from end of list");
		}
=======

	// MULTINOTE EDITING

	// insert at current iterator location, if no iterator is specified
	public Editor insertPitch(NoteLetter ltr, int octv, Accidental accd, boolean istied) {
		insertPitch(_pitchIter, ltr, octv, accd, istied);
>>>>>>> 7df1b9966fa6c1dbdcb4c3db763db3de5d3715a9
		return this;
	}
	public Editor replaceMultiNote(MultiNote multiNote) {
		this.removeMultiNote().insertMultiNote(multiNote);
		_multiNoteIter.previous();
		return this;
	}
	
// PITCHES
	public Editor setPitchIter(ListIterator<Pitch> iter) {
		_pitchIter = iter;
		return this;
	}
	public Editor insertPitch(Pitch pitch) {
		_pitchIter.add(pitch);
		return this;
	}
	public Editor removePitch() {
		if (_pitchIter.hasNext()) {
			_pitchIter.next();
			_pitchIter.remove();
		} 
		else {
			throw new RuntimeException("Tried to remove from end of list");
		}
		return this;
	}
	public Editor replacePitch(Pitch pitch) {
		this.removePitch().insertPitch(pitch);
		_pitchIter.previous();
		return this;
	}
}